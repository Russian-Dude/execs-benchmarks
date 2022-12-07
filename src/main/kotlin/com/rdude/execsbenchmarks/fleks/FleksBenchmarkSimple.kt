package com.rdude.execsbenchmarks.fleks
import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.world
import com.rdude.execsbenchmarks.config.*
import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Level


@State(Scope.Benchmark)
@Warmup(iterations = WARMUP_ITERATIONS)
@Measurement(iterations = BENCHMARK_ITERATIONS)
class FleksBenchmarkSimple {

    private lateinit var world: World

    @Setup(Level.Iteration)
    fun setup() {
        world = world {
            systems {
                add(FleksMovingSystem())
            }
        }
        repeat(ENTITIES_AMOUNT) { index ->
            world.entity {
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