package com.rdude.execsbenchmarks.fleks

import com.github.quillraven.fleks.*
import com.github.quillraven.fleks.World.Companion.family

class FleksPositionComponent(var x: Int, var y: Int) : Component<FleksPositionComponent> {

    override fun type(): ComponentType<FleksPositionComponent> = FleksPositionComponent

    companion object : ComponentType<FleksPositionComponent>()
}


class FleksSpeedComponent(var x: Int, var y: Int) : Component<FleksSpeedComponent> {

    override fun type(): ComponentType<FleksSpeedComponent> = FleksSpeedComponent

    companion object : ComponentType<FleksSpeedComponent>()
}


object FleksRemoveMeComponent : Component<FleksRemoveMeComponent>, ComponentType<FleksRemoveMeComponent>() {

    override fun type(): ComponentType<FleksRemoveMeComponent> = FleksRemoveMeComponent
}


object FleksRemoveMyEntityComponent : Component<FleksRemoveMyEntityComponent>, ComponentType<FleksRemoveMyEntityComponent>() {

    override fun type(): ComponentType<FleksRemoveMyEntityComponent> = FleksRemoveMyEntityComponent
}



class FleksMovingSystem : IteratingSystem(family { all(FleksPositionComponent, FleksSpeedComponent) } ) {

    override fun onTickEntity(entity: Entity) {
        val position = entity[FleksPositionComponent]
        val speed = entity[FleksSpeedComponent]

        position.x += speed.x
        position.y += speed.y
    }
}


class FleksRemoveComponentSystem(family: Family) : IteratingSystem(family) {

    override fun onTickEntity(entity: Entity) {
        entity.configure {
            it -= FleksRemoveMeComponent
        }
    }
}


class FleksRemoveEntitySystem : IteratingSystem(family { all(FleksRemoveMyEntityComponent) }) {

    override fun onTickEntity(entity: Entity) {
        entity.remove()
    }
}


class FleksAddEntitySystem : IntervalSystem() {

    override fun onTick() {
        world.entity {
            it += FleksRemoveMyEntityComponent
        }
    }
}