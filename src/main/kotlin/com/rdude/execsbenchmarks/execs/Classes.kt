package com.rdude.execsbenchmarks.execs

import com.rdude.exECS.component.Component
import com.rdude.exECS.entity.Entity
import com.rdude.exECS.pool.Poolable
import com.rdude.exECS.system.IterableActingSystem

class ExEcsPositionComponent(var x: Int, var y: Int) : Component, Poolable

class ExEcsSpeedComponent(var x: Int = 0, var y: Int = 0) : Component, Poolable


class MovingSystem : IterableActingSystem(allOf = ExEcsPositionComponent::class and ExEcsSpeedComponent::class) {

    override fun act(entity: Entity) {
        val position = entity<ExEcsPositionComponent>()!!
        val speed = entity<ExEcsSpeedComponent>()!!

        position.x += speed.x
        position.y += speed.y
    }
}