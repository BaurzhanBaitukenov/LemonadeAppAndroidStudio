package com.example.lemonadeapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"

    private val SELECT = "select"

    private val SQUEEZE = "squeeze"

    private val DRINK = "drink"

    private val RESTART = "restart"

    private var lemonadeState = "select"

    private var lemonSize = -1

    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }

        lemonImage = findViewById(R.id.treeImage)
        setViewElements()
        lemonImage!!.setOnClickListener {
            clickStateImg()
        }
    }

    private fun clickStateImg() {
        when (lemonadeState) {
            SELECT -> {
                lemonadeState = SQUEEZE
                lemonSize = lemonTree.pick()
                squeezeCount = 0
            }
            SQUEEZE -> {
                squeezeCount += 1
                lemonSize -= 1
                when (lemonSize) {
                    0 -> {
                        lemonadeState = DRINK
                        lemonSize = -1
                    }
                }
            }
            DRINK -> {
                lemonadeState = RESTART
            }

            RESTART -> {
                lemonadeState = SELECT
            }

        }
        setViewElements()

    }

    @SuppressLint("ResourceType")
    private fun setViewElements() {
        val textAction: TextView = findViewById(R.id.textView)
        when(lemonadeState) {
            SELECT -> textAction.setText(R.string.lemon_select)
            SQUEEZE -> textAction.setText(R.string.lemon_squeeze)
            DRINK -> textAction.setText(R.string.lemon_drink)
            RESTART -> textAction.setText(R.string.lemon_select)
        }

        when(lemonadeState) {
            SELECT -> lemonImage?.setImageResource(R.drawable.lemon_tree)
            SQUEEZE -> lemonImage?.setImageResource(R.drawable.lemon_squeeze)
            DRINK -> lemonImage?.setImageResource(R.drawable.lemon_drink)
            RESTART -> lemonImage?.setImageResource(R.drawable.lemon_restart)
        }
    }
}

class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}