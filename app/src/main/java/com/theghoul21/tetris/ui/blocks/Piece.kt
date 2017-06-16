package com.theghoul21.tetris.ui.blocks

import kotlin.reflect.KProperty

/**
 * Created by Luca on 01/06/2017.
 */

enum class Type {
    I, O, T, S, Z, J, L
}

enum class Rotation(val degrees: Int) {
    x0(0),
    x90(0),
    x180(0),
    x270(0),
}

enum class Direction {
    LEFT, RIGHT, DOWN
}


data class Piece(val type: Type, val color: Int, val rotation: Rotation) {

    var coordinates: Pair<Int, Int>
    var size: Pair<Int, Int>
    val center: Pair<Int, Int>

    init {

        when (type) {
            Type.I -> {
                size = Pair(4, 4)
                center = Pair(1, 1)
            }
            Type.O -> {
                size = Pair(2, 2)
                center = Pair(1,1)
            }
            else -> {
                size = Pair(3, 3)
                center = Pair(1,1)
            }
        }
        coordinates = Pair(Math.floor((Sizes.WIDTH - size.second) / 2.0).toInt(), 0)
    }

    val blocks: MutableList<Block> by lazy {
        when (piece.type) {
            // TODO change this values for correct centering
            Type.I -> mutableListOf(Block(0, 1, piece.color), Block(1, 1, piece.color), Block(2, 1, piece.color), Block(3, 1, piece.color));
            Type.O -> mutableListOf(Block(0, 1, piece.color), Block(1, 0, piece.color), Block(1, 1, piece.color), Block(0, 0, piece.color));
            Type.T -> mutableListOf(Block(0, 2, piece.color), Block(1, 1, piece.color), Block(1, 2, piece.color), Block(2, 2, piece.color));
            Type.S -> mutableListOf(Block(0, 2, piece.color), Block(1, 1, piece.color), Block(1, 2, piece.color), Block(2, 1, piece.color));
            Type.Z -> mutableListOf(Block(0, 1, piece.color), Block(1, 1, piece.color), Block(1, 2, piece.color), Block(2, 2, piece.color));


            Type.J -> mutableListOf(Block(0, 1, piece.color), Block(0, 2, piece.color), Block(1, 2, piece.color), Block(2, 2, piece.color));
            Type.L -> mutableListOf(Block(0, 2, piece.color), Block(1, 2, piece.color), Block(2, 2, piece.color), Block(2, 1, piece.color));
        }
    }

    fun moveDown() {
        this.coordinates = Pair(coordinates.first, coordinates.second + 1)
    }

    fun moveLeft() {
        if (coordinates.first - 1 >= 0)
            this.coordinates = Pair(coordinates.first - 1, coordinates.second)
    }

    fun moveRight() {
        if (coordinates.first + size.second + 1 <= Sizes.WIDTH)
            this.coordinates = Pair(coordinates.first + 1, coordinates.second)
    }

    fun getBlockCoordinates(b: Block): Pair<Int, Int> = Pair(coordinates.first + b.x, coordinates.second + b.y)

    fun rotateLeft() {
        if (type != Type.O) {
            blocks.map { it.x += size.first-1-it.x }
        }
    }


}
