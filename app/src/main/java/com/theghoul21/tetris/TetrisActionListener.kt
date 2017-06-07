package com.theghoul21.tetris

/**
 * Created by Luca on 06/06/2017.
 */

interface TetrisActionListener {

    fun onMoveLeft()
    fun onMoveRight()
    fun onSpeedUp()
    fun onSlowDown()
    fun onGameRestart()
}
