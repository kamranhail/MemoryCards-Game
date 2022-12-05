package com.example.matchcardsapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import modals.BoardSize

class Start_Activity : AppCompatActivity() , View.OnClickListener {


    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var btnPlay: Button? = null
    private var selectedoption : Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)


        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)

       btnPlay=findViewById(R.id.btn_play)
        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        btnPlay?.setOnClickListener(this)
        defaultOptionsView()
    }

    override fun onClick(v: View?) {


        when(v?.id)
        {
            R.id.tv_option_one->
            {
                tvOptionOne?.let {
selectedOptionView(it,1)
                }


            }
            R.id.tv_option_two->
            {
                tvOptionTwo?.let {
                    selectedOptionView(it,2)
                }
            }
            R.id.tv_option_three->
            {
                tvOptionThree?.let {
                    selectedOptionView(it,3)
                }
            }
            R.id.btn_play->
            {



val intent=Intent(this,MainActivity::class.java)
                intent.putExtra("Boardsize",selectedoption)
                startActivity(intent)

            }

        }
    }
    private fun selectedOptionView(tv: TextView, selectedOptionNum: Int) {
        Log.i("startact", "enter the fun")
        defaultOptionsView()
     selectedoption=selectedOptionNum


       // tv.setTextColor(
        //    Color.parseColor("#363A43")
      //  )
      //  tv.setTypeface(tv.typeface, Typeface.BOLD)  // make text bold
        tv.background=ContextCompat.getDrawable(this,R.drawable.outlinselected)
      //  tv.background = ContextCompat.getDrawable(
        //    this@Start_Activity,
          //  R.drawable.outlinselected
        //)  //make background
    }
    private fun defaultOptionsView() {

        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }


        for (option in options) {
           // option.setTextColor(Color.parseColor("#7A8089"))
          //  option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this@Start_Activity,
                R.drawable.outline
            )

        }
    }


}