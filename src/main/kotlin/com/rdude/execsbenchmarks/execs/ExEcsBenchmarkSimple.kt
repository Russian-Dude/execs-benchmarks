package com.rdude.execsbenchmarks.execs

import com.rdude.exECS.world.World
import com.rdude.execsbenchmarks.config.*
import kotlinx.benchmark.Measurement
import kotlinx.benchmark.Setup
import kotlinx.benchmark.State
import kotlinx.benchmark.Warmup
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Scope

@State(Scope.Benchmark)
@Warmup(iterations = WARMUP_ITERATIONS)
@Measurement(iterations = BENCHMARK_ITERATIONS)
class ExEcsBenchmarkSimple {

    private lateinit var world: World

    @Setup(Level.Iteration)
    fun setup() {
        world = World()
        world.registerSystem(MovingSystem())
        world.createEntities(
            amount = ENTITIES_AMOUNT,
            { ExEcsPositionComponent(ComponentsInitValues.positionComponentXs[it], ComponentsInitValues.positionComponentYs[it]) },
            { ExEcsSpeedComponent(ComponentsInitValues.speedComponentXs[it], ComponentsInitValues.speedComponentYs[it]) }
        )
    }

    @Benchmark
    fun benchmark() {
        repeat(WORLD_ITERATIONS) {
            world.act()
        }
    }
}