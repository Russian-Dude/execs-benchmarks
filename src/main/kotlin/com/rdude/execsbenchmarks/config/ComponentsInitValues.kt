package com.rdude.execsbenchmarks.config

import kotlin.random.Random

object ComponentsInitValues {

    private val random = Random(RANDOM_SEED)

    val positionComponentXs = IntArray(ENTITIES_AMOUNT) { random.nextInt(-100, 100) }

    val positionComponentYs = IntArray(ENTITIES_AMOUNT) { random.nextInt(-100, 100) }

    val speedComponentXs = IntArray(ENTITIES_AMOUNT) { random.nextInt(-10, 10) }

    val speedComponentYs = IntArray(ENTITIES_AMOUNT) { random.nextInt(-10, 10) }

}