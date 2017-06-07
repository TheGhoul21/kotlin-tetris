package com.theghoul21.tetris.ui.blocks

/**
 * Created by Luca on 02/06/2017.
 */
data class Block(var x:Int, var y: Int, val color: Int) {
    override fun toString() = "$x, $y, $color"
}

