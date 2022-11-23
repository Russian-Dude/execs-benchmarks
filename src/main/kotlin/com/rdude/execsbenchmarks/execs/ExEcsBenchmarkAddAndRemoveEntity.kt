package com.rdude.execsbenchmarks.execs

import com.rdude.exECS.world.World
import com.rdude.execsbenchmarks.config.BENCHMARK_ITERATIONS
import com.rdude.execsbenchmarks.config.ENTITIES_AMOUNT
import com.rdude.execsbenchmarks.config.WARMUP_ITERATIONS
import com.rdude.execsbenchmarks.config.WORLD_ITERATIONS
import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Level

@State(Scope.Benchmark)
@Warmup(iterations = WARMUP_ITERATIONS)
@Measurement(iterations = BENCHMARK_ITERATIONS)
class ExEcsBenchmarkAddAndRemoveEntity {

    private lateinit var world: World

    @Setup(Level.Iteration)
    fun setup() {
        world = World()
        world.registerSystem(ExEcsRemoveEntitySystem())
        world.registerSystem(ExEcsAddEntitySystem())
        world.createEntities(
            amount = ENTITIES_AMOUNT,
            { ExEcsRemoveMyEntityComponent }
        )
    }

    @Benchmark
    fun benchmark() {
        repeat(WORLD_ITERATIONS) {
            world.act()
        }
    }
}