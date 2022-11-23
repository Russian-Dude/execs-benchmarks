package com.rdude.execsbenchmarks.execs

import com.rdude.exECS.pool.fromPool
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
        world.registerSystem(ExEcsMovingSystem())
        world.createEntities(
            amount = ENTITIES_AMOUNT,
            {
                fromPool<ExEcsPositionComponent> {
                    x = ComponentsInitValues.positionComponentXs[it]
                    y = ComponentsInitValues.positionComponentYs[it]
                }
            },
            {
                fromPool<ExEcsSpeedComponent> {
                    x = ComponentsInitValues.speedComponentXs[it]
                    y = ComponentsInitValues.speedComponentYs[it]
                }
            }
        )
    }

    @Benchmark
    fun benchmark() {
        repeat(WORLD_ITERATIONS) {
            world.act()
        }
    }
}