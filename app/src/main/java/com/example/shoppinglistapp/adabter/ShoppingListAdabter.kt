package com.example.shoppinglistapp.adabter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.room.ShoppingList
import com.example.shoppinglistapp.databinding.ShoppingListViewLayoutBinding
import com.example.shoppinglistapp.room.ShoppingListDao
import com.example.shoppinglistapp.room.ShoppingListDatabase
import kotlinx.coroutines.delay
import kotlin.coroutines.coroutineContext

class ShoppingListAdabter() : RecyclerView.Adapter<ShoppingListAdabter.ListHolder>() {

    private var list : ArrayList<ShoppingList> = arrayListOf()
    private var number :Int = 0
    private lateinit var shoppingListDao: ShoppingListDao
    var pieceViewClikListener: (ShoppingList,Int,Int) -> Unit = {_,_,_ ->}
    var deleteViewClikListener: (Int) -> Unit = {}
    var emptyViewClikListener: (Int) -> Unit = {}

    class ListHolder(var binding: ShoppingListViewLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val binding = ShoppingListViewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListHolder(binding)
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.binding.apply {
            emptyViewClikListener(list.size)
            edtProduct.text = list.get(position).name
            tvPiece.text=list.get(position).piece.toString()
            number=list.get(position).piece!!.toInt()

                imgPlus.setOnClickListener {
                    number=list.get(position).piece!!.toInt()
                    number++
                    tvPiece.text = number.toString()
                    pieceViewClikListener(list.get(position),number,list.size)
                    emptyViewClikListener(list.size)
                }
                imgInterest.setOnClickListener {
                    if(number > 1){
                        number=list.get(position).piece!!.toInt()
                        number--
                        tvPiece.text = number.toString()
                        pieceViewClikListener(list.get(position),number,list.size)
                        emptyViewClikListener(list.size)
                    }
                    else{
                        deleteViewClikListener(list.get(position).uuid)
                        emptyViewClikListener(list.size)
                }

            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun listFill(shopList : ArrayList<ShoppingList>){
        list.clear()
        list.addAll(shopList)
        notifyDataSetChanged()
    }
}