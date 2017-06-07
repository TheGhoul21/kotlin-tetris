package com.theghoul21.tetris.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.theghoul21.tetris.ui.blocks.Block
import com.theghoul21.tetris.ui.blocks.Direction
import com.theghoul21.tetris.ui.blocks.Grid
import com.theghoul21.tetris.ui.blocks.Sizes


/**
 * Created by Luca on 03/06/2017.
 */
class DrawSurfaceView(context: Context?, attributeSet: AttributeSet?) : SurfaceView(context, attributeSet), SurfaceHolder.Callback {
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        blockSideSize = (width / Sizes.WIDTH).toFloat();
    }

    var blockSideSize: Float = 0.0f
    private val grid: Grid;

    init {
        holder.addCallback(this)
//        val canvas = holder.lockCanvas()
//        canvas.drawColor(Color.BLUE)
//        holder.unlockCanvasAndPost(canvas)


        grid = Grid(Sizes.WIDTH, Sizes.HEIGHT/*, { g ->
            val canvas = holder.lockCanvas()

            if (canvas != null) {

                canvas.drawColor(Color.GRAY)
                g.blocks.map { drawTile(canvas, it) }

                val paintText = Paint()
                paintText.color = Color.WHITE
                paintText.textSize = 150.0f

                if (!g.gameOn) {
                    drawCenteredText(canvas, paintText, "Game Over")
                    paintText.textSize = 100.0f
                    drawCenteredText(canvas, paintText, "Tap to Restart", Pair(0, 180))
                }
                val lastPiece = if(g.pieces.isNotEmpty()) g.pieces.last() else null
                lastPiece?.blocks?.map { drawTile(canvas, it, lastPiece.coordinates.first, lastPiece.coordinates.second) }
                holder.unlockCanvasAndPost(canvas)
            }
        }*/)

        setOnClickListener {
            if(!grid.gameOn) {
                grid.restart();
            }
        }
    }

    fun draw() {
        val canvas = holder.lockCanvas()

        if (canvas != null) {

            //grid.update()

            canvas.drawColor(Color.GRAY)
            grid.blocks.map { drawTile(canvas, it) }

            val paintText = Paint()
            paintText.color = Color.WHITE
            paintText.textSize = 150.0f

            if (!grid.gameOn) {
                drawCenteredText(canvas, paintText, "Game Over")
                paintText.textSize = 100.0f
                drawCenteredText(canvas, paintText, "Tap to Restart", Pair(0, 180))
            }
            val lastPiece = if(grid.pieces.isNotEmpty()) grid.pieces.last() else null
            lastPiece?.blocks?.map { drawTile(canvas, it, lastPiece.coordinates.first, lastPiece.coordinates.second) }
            holder.unlockCanvasAndPost(canvas)
        }
    }




    private fun drawCenteredText(canvas: Canvas, paint: Paint, text: String, offset: Pair<Int, Int> = Pair(0, 0)) {
        val r = Rect();
        canvas.getClipBounds(r)
        val cHeight = r.height()
        val cWidth = r.width()
        paint.textAlign = Paint.Align.LEFT
        paint.getTextBounds(text, 0, text.length, r)
        val x = cWidth / 2f - r.width() / 2f - r.left
        val y = cHeight / 2f + r.height() / 2f - r.bottom
        canvas.drawText(text, x + offset.first, y + offset.second, paint)
    }

    fun drawTile(canvas: Canvas, it: Block, offsetX: Int = 0, offsetY: Int = 0) {
        val pTile = Paint(Paint.ANTI_ALIAS_FLAG);


        // pTile.color = Color.RED
        //canvas.drawRect(0.0f, 0.0f, 160.0f, 160.0f, pTile)


        //pTile.color = Color.YELLOW
        //canvas.drawRect(320.0f, height.toFloat() - (it.y * blockSideSize), 480.0f, height.toFloat() - ((1 + it.y) * blockSideSize), pTile)
        pTile.color = it.color
        canvas.drawRect(
                (offsetX + it.x) * blockSideSize,
                /*height.toFloat() - */((offsetY + 1 + it.y) * blockSideSize),
                (offsetX + it.x + 1) * blockSideSize,
                /*height.toFloat() - */((offsetY + it.y) * blockSideSize),
                pTile)


        //pTile.color = Color.GREEN
        //canvas.drawRect(320.0f, 0.0f, 320.0f, 160.0f, pTile)


        //println("${(blockSideSize * it.x).toFloat()}, ${(blockSideSize * it.y).toFloat()}, ${(blockSideSize * (Sizes.WIDTH - it.x + 1)).toFloat()}, ${(blockSideSize * (Sizes.HEIGHT - it.y + 1)).toFloat()}")
        //canvas.drawRect((blockSideSize * it.x).toFloat(), (blockSideSize * it.y).toFloat(), (blockSideSize * (Sizes.WIDTH - it.x + 1)).toFloat(), (blockSideSize * (Sizes.HEIGHT - it.y + 1)).toFloat(), pTile)
    }

    fun movePieceTo(direction: Direction) {
        grid.movePieceTo(direction)
    }

    fun speedUp() {
        grid.speedUp()
    }

    fun slowDown() {
        grid.slowDown()
    }

    fun updateGame() {
        grid.update()
    }
}
