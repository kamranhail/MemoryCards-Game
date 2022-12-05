package com.example.matchcardsapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import modals.BoardSize
import modals.MemoryCard
import kotlin.math.min

class MemoryBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val  cards:  List<MemoryCard>,
     private val cardClickListener : CardClickListener
) :

    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {
    companion object{
        private    const val MARGIN_SIZE=10
private const val TAG="MemoryBoardAdapter"

    }

     interface  CardClickListener{
          fun onCardClicked(position : Int)

     }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardWidth= parent.width / boardSize.getWidth()-(2* MARGIN_SIZE)
        val cardHeight  = parent.height / boardSize.getHeight()-(2* MARGIN_SIZE)
        val cardSideLength  = min(cardWidth,cardHeight)
       val View= LayoutInflater.from(context).inflate(R.layout.memory_card,parent,false)
   val layoutPramsofcard =View.findViewById<CardView>(R.id.cardview2).layoutParams as ViewGroup.MarginLayoutParams

      //Toast.makeText(View.context," $cardSideLength   sds $cardHeight",Toast.LENGTH_LONG).show()

        layoutPramsofcard.width = cardSideLength
        layoutPramsofcard.height = cardSideLength
layoutPramsofcard.setMargins(MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE,MARGIN_SIZE)
    return ViewHolder(View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(position)

    }

    override fun getItemCount() = boardSize.numcards

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private  val imageButton =itemView.findViewById<ImageButton>(R.id.imageButton)


        fun bind (position: Int)
        {
            val memoryCard =cards[position]
            imageButton.setImageResource(if(cards[position].isFacedUp) cards[position].identiefer else R.drawable.ic_launcher_background)
           imageButton.alpha=if(memoryCard.isMatched).4f else 1.0f
            val colorStateList= if(memoryCard.isMatched) ContextCompat.getColorStateList(context,R.color.lightgray) else null
          ViewCompat.setBackgroundTintList(imageButton,colorStateList)
            imageButton.setOnClickListener{
            Log.i(TAG,"Clicked on img $position")
                cardClickListener.onCardClicked(position)

        }

        }


    }
}


