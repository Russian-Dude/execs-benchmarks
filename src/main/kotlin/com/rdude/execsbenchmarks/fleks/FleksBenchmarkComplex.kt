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
class FleksBenchmarkComplex {

    private lateinit var world: World

    @Setup(Level.Iteration)
    fun setup() {
        world = world {
            val family = World.family { all(FleksRemoveMeComponent) }
            families {
                onRemove(family) {
                    entity {
                        it += FleksRemoveMeComponent
                    }
                }
            }
            systems {
                add(FleksRemoveEntitySystem())
                add(FleksAddEntitySystem())
                add(FleksMovingSystem())
                add(FleksRemoveComponentSystem(family))
            }
        }
        repeat(ENTITIES_AMOUNT) { index ->
            world.entity {
                it += FleksRemoveMyEntityComponent
                it += FleksRemoveMeComponent
                it += FleksPositionComponent(
                    ComponentsInitValues.positionComponentXs[index],
                    ComponentsInitValues.positionComponentYs[index]
                )
                it += FleksSpeedComponent(
                    ComponentsInitValues.speedComponentXs[index],
                    ComponentsInitValues.speedComponentYs[index]
                )
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