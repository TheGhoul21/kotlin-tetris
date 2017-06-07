package com.theghoul21.tetris.ui.blocks

import android.graphics.Color
import java.security.SecureRandom
import java.util.*

/**
 * Created by Luca on 02/06/2017.
 *
 */

class Grid(val width: Int, val height: Int, callback: (g: Grid) -> Unit) {
    var pieces: List<Piece> = emptyList()
    val random: SecureRandom = SecureRandom()
    var blocks: List<Block> = emptyList()


    var speed = 1.0f // tiles per second
    var lastUpdate = 0L
    var timeSpent = 0L
    var gameOn = true;

    fun restart() {
        pieces = emptyList()
        blocks = emptyList()


        speed = 1.0f // tiles per second
        lastUpdate = 0L
        timeSpent = 0L
        gameOn = true;
    }


    val availableColors = listOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA)


    private val update: TimerTask.() -> Unit = {

        if (timeSpent >= 1 / speed * 1000) {
            timeSpent = 0;
            if (gameOn) {

                if (pieces.isEmpty()) {
                    generateNewPiece()
                } else {
                    if (canMoveInDirection(Direction.DOWN)) {
                        pieces.last().moveDown()
                    } else {
                        if (checkGameOver()) {
                            gameOn = false
                        } else {
                            addBlocksToStack(pieces.last())
                            generateNewPiece()
                        }
                    }
                }
            }
        } else {
            timeSpent += System.currentTimeMillis() - lastUpdate;
            lastUpdate = System.currentTimeMillis()
        }

        callback(this@Grid);


    }
    val timer = kotlin.concurrent.fixedRateTimer("mainTimer", false, 0, 16L, update)

    private fun addPiece(p: Piece) {
        pieces += p
    }

    fun movePieceTo(direction: Direction) {
        when (direction) {
            Direction.LEFT -> if (canMoveInDirection(Direction.LEFT)) pieces.last().moveLeft()
            Direction.RIGHT -> if (canMoveInDirection(Direction.RIGHT)) pieces.last().moveRight()
            else -> {
            }
        }
    }

    fun canMoveInDirection(direction: Direction): Boolean {

        val collisionDetected: Boolean
        val lastPiece = pieces.last()
        val bottomPieceY = lastPiece.coordinates.second + lastPiece.blocks.sortedByDescending { it.y }.first().y

        if (!blocks.isEmpty()) {


            val offset: Pair<Int, Int> = when (direction) {
                Direction.RIGHT -> Pair(1, 0)
                Direction.LEFT -> Pair(-1, 0)
                Direction.DOWN -> Pair(0, 1)
            }

            collisionDetected = lastPiece.blocks.count { lastPiece.coordinates.second + it.y + offset.second >= getTopMostBlockFor(lastPiece.coordinates.first + it.x + offset.first) } > 0
        } else {
            collisionDetected = bottomPieceY + 1 >= height
        }

        return !collisionDetected
    }


    private fun generateNewPiece() {
        val type = Type.values()[random.nextInt(Type.values().size)]
        val rotation = Rotation.values()[random.nextInt(Rotation.values().size)]
        addPiece(Piece(
                type,
                availableColors[pieces.size % availableColors.size],
                rotation))
    }

    private fun getTopMostBlockFor(x: Int): Int {
        return blocks
                .filter { it.x == x }
                .minBy { it.y }
                ?.y ?: height
    }

    private fun addBlocksToStack(piece: Piece) {
        for (block in piece.blocks) {
//                println("${lastPiece.coordinates.second} + ${block.y}");
            block.x += piece.coordinates.first
            block.y += piece.coordinates.second
            // println("adding block to stack");
            // println(block);
            this.blocks += block
        }
    }

    fun speedUp() {
        speed = 75.0f;
    }
    fun slowDown() {
        speed = 1.0f;
    }

    private fun wouldCollide(): Boolean {
        val collisionDetected: Boolean
        val lastPiece = pieces.last()
        val bottomPieceY = lastPiece.coordinates.second + lastPiece.blocks.sortedByDescending { it.y }.first().y

        if (!blocks.isEmpty()) {
            collisionDetected = lastPiece.blocks.count { lastPiece.coordinates.second + it.y + 1 >= getTopMostBlockFor(lastPiece.coordinates.first + it.x) } > 0
//            val maxY = getTopMostBlockFor(lastPiece.coordinates.first, lastPiece.coordinates.first + lastPiece.size.first)
//            collisionDetected = bottomPieceY + 1 >= maxY
        } else {
            collisionDetected = bottomPieceY + 1 >= height
        }

        if (collisionDetected) {
            for (block in lastPiece.blocks) {
//                println("${lastPiece.coordinates.second} + ${block.y}");
                block.x += lastPiece.coordinates.first
                block.y += lastPiece.coordinates.second
                // println("adding block to stack");
                // println(block);
                this.blocks += block
            }
        }

        return collisionDetected
    }

    private fun checkGameOver(): Boolean {
        return (0..width - 1).any { getTopMostBlockFor(it) == 1 }
    }


}

object Sizes {
    val WIDTH = 9
    val HEIGHT = 12
}
