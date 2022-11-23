package com.rdude.execsbenchmarks.execs

import com.rdude.exECS.component.Component
import com.rdude.exECS.component.ImmutableComponent
import com.rdude.exECS.entity.Entity
import com.rdude.exECS.event.ComponentRemovedEvent
import com.rdude.exECS.pool.Poolable
import com.rdude.exECS.system.ActingSystem
import com.rdude.exECS.system.EventSystem
import com.rdude.exECS.system.IterableActingSystem

class ExEcsPositionComponent(var x: Int, var y: Int) : Component, Poolable

class ExEcsSpeedComponent(var x: Int = 0, var y: Int = 0) : Component, Poolable

object ExEcsRemoveMeComponent : ImmutableComponent

object ExEcsRemoveMyEntityComponent : ImmutableComponent



class ExEcsMovingSystem : IterableActingSystem(allOf = ExEcsPositionComponent::class and ExEcsSpeedComponent::class) {

    override fun act(entity: Entity) {
        val position = entity<ExEcsPositionComponent>()!!
        val speed = entity<ExEcsSpeedComponent>()!!

        position.x += speed.x
        position.y += speed.y
    }
}


class ExEcsRemoveComponentSystem : IterableActingSystem(only = ExEcsRemoveMeComponent) {

    override fun act(entity: Entity) {
        entity.removeComponent<ExEcsRemoveMeComponent>()
    }
}


class ExEcsAddRemovedComponentSystem : EventSystem<ComponentRemovedEvent<ExEcsRemoveMeComponent>>() {

    override fun eventFired(event: ComponentRemovedEvent<ExEcsRemoveMeComponent>) {
        event.entity.addComponent(event.component)
    }
}


class ExEcsRemoveEntitySystem : IterableActingSystem(only = ExEcsRemoveMyEntityComponent) {

    override fun act(entity: Entity) {
        entity.remove()
    }
}


class ExEcsAddEntitySystem : ActingSystem() {

    override fun act() {
        createEntity(ExEcsRemoveMyEntityComponent)
    }
}