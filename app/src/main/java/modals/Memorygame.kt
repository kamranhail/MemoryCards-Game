package modals

import android.text.BoringLayout
import com.example.matchcardsapp.Utils.DEFAULT_ICONS

class Memorygame(private val  boardSize: BoardSize)
{


   
    val cards: List<MemoryCard>
var numofPairsFound=0
private  var numCardFlip=0
private  var indexOfSingleSelectedCard: Int?=null
init {
    val chosenimg= DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
    val ranfomizeimgs=(chosenimg + chosenimg).shuffled()
    cards =ranfomizeimgs.map{ MemoryCard(it) }
}
    //game login inbeleow

    fun  flipCard(position: Int) : Boolean{
        numCardFlip++
           val card =cards[position]

           var foundMatch=false
        if(indexOfSingleSelectedCard==null){
            restoreCard()
            indexOfSingleSelectedCard=position
        }

        else
        {
             foundMatch = checkForMatch(indexOfSingleSelectedCard!!,position)
          indexOfSingleSelectedCard=null
        }
        card.isFacedUp=!card.isFacedUp
        return  foundMatch
    }

    private fun checkForMatch(position1: Int, position2: Int): Boolean {
if(cards[position1].identiefer != cards[position2].identiefer)
{
    return  false

}
        cards[ position1].isMatched=true
        cards[ position2].isMatched=true
        numofPairsFound++
        return  true

                }

    private fun restoreCard() {
        for(card in cards)
        {
if(!card.isMatched)
{
    card.isFacedUp=false
}

        }
    }



    fun haveWonGame(): Boolean{

        return  numofPairsFound==boardSize.getNumPairs()
    }
    fun isCardFaceUp(position: Int ) : Boolean{


        return cards[position].isFacedUp
    }

    fun getNumMove(): Int {
return numCardFlip / 2

    }

}