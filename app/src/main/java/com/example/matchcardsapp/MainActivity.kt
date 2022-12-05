package com.example.matchcardsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import modals.BoardSize
import modals.Memorygame

class MainActivity : AppCompatActivity() {

companion object{
    private const val  TAG="MainActivity"

}
    private lateinit var clRoot: ConstraintLayout
    private lateinit var adapter: MemoryBoardAdapter
    private lateinit var memoryGame: Memorygame

    private  lateinit var rvBoard:  RecyclerView
    private  lateinit var tw_Moves:  TextView
    private  lateinit var tv_NumPairs:  TextView
      private  var boardSize: BoardSize=BoardSize.EASY


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clRoot=findViewById(R.id.clRoot)
        rvBoard=findViewById(R.id.rv_board)
        tw_Moves=findViewById(R.id.tw_moves)
        tv_NumPairs=findViewById(R.id.tv_pair)

   val boardsizee =intent.getIntExtra("Boardsize",1)
        when(boardsizee)
        {
         1->
         {
             boardSize=BoardSize.EASY
         }
            2->
            {
                boardSize=BoardSize.MEDIUM
            }
            3->
            {
                boardSize=BoardSize.HARD
            }
        }

      setupBoard()


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
         R.id.mi_refresh->{

             if(memoryGame.getNumMove()>0&&!memoryGame.haveWonGame())
             {
               shoeAlertDoalig("Quit your Curent game " , null ,View.OnClickListener {
setupBoard()

               })
             }else {
                 setupBoard()
             }
//setep the game again
         }
            R.id.mi_newSize ->{

               showNewSizeDialog()
                return true
            }
        }
    return  super.onOptionsItemSelected(item)
    }
    private fun showNewSizeDialog(){

   val boardSizeview = LayoutInflater.from(this).inflate(R.layout.dialog_board_size,null)
         val radioGroupSize=boardSizeview.findViewById<RadioGroup>(R.id.radiogroup)

        when (boardSize){
            BoardSize.EASY -> radioGroupSize.check(R.id.rbEasy)
            BoardSize.MEDIUM -> radioGroupSize.check(R.id.rbMedium)
            BoardSize.HARD -> radioGroupSize.check(R.id.rbHard)
        }



        shoeAlertDoalig("Choose new size", boardSizeview,View.OnClickListener {

            // set new value of board size

            boardSize = when (radioGroupSize.checkedRadioButtonId) {

            R.id.rbEasy -> BoardSize.EASY
               R.id.rbMedium -> BoardSize.MEDIUM
               else -> BoardSize.HARD


        }
            setupBoard()
        })

    }

    private fun shoeAlertDoalig(title: String,view :View?,positiveButtonClickListener: View.OnClickListener ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setView(view)
            .setNegativeButton("Cancel",null)
            .setPositiveButton("OK"){_,_->
                positiveButtonClickListener.onClick((null))

            }.show()
    }

    private fun updateGamewithFlip(position: Int) {

// error handling
        if(memoryGame.haveWonGame())
        {
           // elertuserforinvalidmove
            Snackbar.make(clRoot,"you already won ", Snackbar.LENGTH_LONG ).show()

            return
        }
        if(memoryGame.isCardFaceUp(position) )
        {
            Log.i(TAG, "Found a match ! ${memoryGame.numofPairsFound} ")
            Snackbar.make(clRoot,"invalid Move", Snackbar.LENGTH_SHORT ).show()
            return
        }
        //actually flipping the card
        if(memoryGame.flipCard(position)) {
            Log.i(TAG, "Found a match ! num pair Found ${memoryGame.numofPairsFound} ")
            tv_NumPairs.text = "Pairs : ${memoryGame.numofPairsFound} / ${boardSize.getNumPairs()}"
        }
       if(memoryGame.haveWonGame())
       {
           Snackbar.make(clRoot,"You WON!! ",Snackbar.LENGTH_LONG).show()
       }



        tw_Moves.text="Moves : ${memoryGame.getNumMove()}"
        adapter.notifyDataSetChanged()
    }


    fun setupBoard()
    {
when(boardSize){
    BoardSize.EASY ->
    {
        tw_Moves.text="Easy: 4 x 2"
        tv_NumPairs.text="pairs: 0/4 "
    }
    BoardSize.MEDIUM -> {
        tw_Moves.text="Medium: 6 x 3"
        tv_NumPairs.text="pairs: 0/9 "
    }
    BoardSize.HARD ->
    {
        tw_Moves.text="Easy: 6 x 4"
        tv_NumPairs.text="pairs: 0/12"

    }
}


        memoryGame=Memorygame(boardSize)

        adapter=MemoryBoardAdapter(this,boardSize , memoryGame.cards, object : MemoryBoardAdapter.CardClickListener{
            override fun onCardClicked(position: Int) {


                updateGamewithFlip(position)
            }


        })
        rvBoard.adapter=adapter
        rvBoard.hasFixedSize()
        rvBoard.layoutManager=GridLayoutManager(this, boardSize.getWidth())

    }


}