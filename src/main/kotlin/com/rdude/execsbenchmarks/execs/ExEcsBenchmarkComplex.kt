package com.rdude.execsbenchmarks.execs
import com.rdude.exECS.pool.fromPool
import com.rdude.exECS.world.World
import com.rdude.execsbenchmarks.config.*
import kotlinx.benchmark.*
import org.openjdk.jmh.annotations.Level

@State(Scope.Benchmark)
@Warmup(iterations = WARMUP_ITERATIONS)
@Measurement(iterations = BENCHMARK_ITERATIONS)
class ExEcsBenchmarkComplex {

    private lateinit var world: World

    @Setup(Level.Iteration)
    fun setup() {
        world = World()
        world.registerSystem(ExEcsRemoveEntitySystem())
        world.registerSystem(ExEcsAddEntitySystem())
        world.registerSystem(ExEcsMovingSystem())
        world.registerSystem(ExEcsRemoveComponentSystem())
        world.registerSystem(ExEcsAddRemovedComponentSystem())
        world.createEntities(
            amount = ENTITIES_AMOUNT,
            { ExEcsRemoveMyEntityComponent },
            { ExEcsRemoveMeComponent },
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