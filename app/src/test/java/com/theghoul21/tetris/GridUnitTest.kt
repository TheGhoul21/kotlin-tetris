package com.theghoul21.tetris

import com.theghoul21.tetris.ui.blocks.Direction
import com.theghoul21.tetris.ui.blocks.Grid
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

/**
 * Created by Luca on 11/06/2017.
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class GridUnitTest {


    @Test
    fun stage0_gridTest() {
        assertEquals("Grid should be empty", 0, grid.blocks.size.toLong())
        assertEquals("No pieces should be there yet", 0, grid.pieces.size.toLong())
    }

    @Test
    fun stage1_firstTickTest() {
        // trigger game update
        // first tick initialize variables
        grid.update()
        // second tick add pieces
        grid.update()

        assertEquals("Initial speed", Grid.NORMAL_SPEED.toDouble(), grid.speed.toDouble(), DELTA)
        assertEquals("Grid should have 1 piece", 1, grid.pieces.size.toLong())
    }


    @Test
    fun stage2_speedTest() {
        // simulate speeding up
        grid.speedUp()
        assertEquals("After speed-up", Grid.FAST_SPEED.toDouble(), grid.speed.toDouble(), DELTA)

        //simulate releasing button
        grid.slowDown()
        assertEquals("After releasing speed-up", Grid.NORMAL_SPEED.toDouble(), grid.speed.toDouble(), DELTA)
    }

    @Test
    fun stage3_moveTest() {
        assertTrue("Grid should have 1 piece", grid.pieces.size.toLong() >= 1)
        val piecePosition = grid.pieces.last().coordinates

        grid.movePieceTo(Direction.LEFT);
        assertEquals(piecePosition.first - 1, grid.pieces.last().coordinates.first)

        grid.movePieceTo(Direction.RIGHT);
        assertEquals(piecePosition.first, grid.pieces.last().coordinates.first)

        grid.movePieceTo(Direction.RIGHT);
        assertEquals(piecePosition.first + 1, grid.pieces.last().coordinates.first)
    }

    companion object {
        private val DELTA = 1e-15
        var grid: Grid = Grid(9, 12)

    }
}
