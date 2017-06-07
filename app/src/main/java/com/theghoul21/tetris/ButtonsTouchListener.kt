package com.theghoul21.tetris

import android.view.MotionEvent
import android.view.View
import com.theghoul21.tetris.R.id.*

/**
 * Created by Luca on 06/06/2017.
 */

class ButtonsTouchListener(vararg buttons: View): View.OnTouchListener {
    init {
        buttons.map {
            it.setOnTouchListener(this)
        }
    }
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        val context = v?.context as? TetrisActionListener ?: throw Exception("Activity MUST implement TetrisActionListener");
        when(v.id) {
            moveLeftBtn -> if(event?.action == MotionEvent.ACTION_DOWN) context.onMoveLeft()
            moveRightBtn -> if(event?.action == MotionEvent.ACTION_DOWN) context.onMoveRight()
            moveDownBtn ->if(event?.action == MotionEvent.ACTION_DOWN)  {
                context.onSpeedUp()
            } else if(event?.action == MotionEvent.ACTION_UP) {
                context.onSlowDown()
            }
            else -> return false
        }

        return true
    }

}
