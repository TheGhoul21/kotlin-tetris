package com.theghoul21.tetris.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.theghoul21.tetris.ButtonsTouchListener
import com.theghoul21.tetris.JobHolder
import com.theghoul21.tetris.R
import com.theghoul21.tetris.TetrisActionListener
import com.theghoul21.tetris.ui.blocks.Direction
import kotlinx.android.synthetic.main.main_layout.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.NonCancellable
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlin.concurrent.fixedRateTimer
/**
 * Created by Luca on 31/05/2017.
 *
 */

class MainActivity : AppCompatActivity(), JobHolder, TetrisActionListener {
    override fun onSlowDown() {
        main.slowDown()
    }

    override fun onGameRestart() {

    }

    override fun onMoveLeft() {
        main.movePieceTo(Direction.LEFT)
    }

    override fun onMoveRight() {
        main.movePieceTo(Direction.RIGHT)
    }

    override fun onSpeedUp() {
        main.speedUp()
    }

    override val job: Job = Job();



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.main_layout)

        val surfaceView = main;
        ButtonsTouchListener(moveLeftBtn, moveRightBtn, moveDownBtn, main)



        launch(job + UI) {
            fixedRateTimer("draw", period = 16L) {
                main.draw()
            }
        }
        launch(job + UI) {
            fixedRateTimer("gameUpdate", period = 10) {
                main.updateGame()
            }
        }

//        moveLeftBtn.setOnClickListener { surfaceView.movePieceTo(Direction.LEFT) }
//        moveRightBtn.setOnClickListener { surfaceView.movePieceTo(Direction.RIGHT) }
//        moveDownBtn.setOnTouchListener { v, event ->
//            if (event.action == MotionEvent.ACTION_DOWN) {
//                surfaceView.speedUp()
//            }
//            if (event.action == MotionEvent.ACTION_UP) {
//                surfaceView.slowDown()
//            }
//
//            true
//        }
//
//        moveDownBtn.onClick {
//            println(this)
//        }
    }



    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}

val View.contextJob: Job
    get() = (context as? JobHolder)?.job ?: NonCancellable