package com.rdude.execsbenchmarks.fleks
import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.world
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
class FleksBenchmarkAddAndRemoveEntity {

    private lateinit var world: World

    @Setup(Level.Iteration)
    fun setup() {
        world = world {
            systems {
                add(FleksRemoveEntitySystem())
                add(FleksAddEntitySystem())
            }
        }
        repeat(ENTITIES_AMOUNT) {
            world.entity {
                it += FleksRemoveMyEntityComponent
            }
        }
    }

    @Benchmark
    fun benchmark() {
        repeat(WORLD_ITERATIONS) {
            world.update(0f)
        }
    }

}