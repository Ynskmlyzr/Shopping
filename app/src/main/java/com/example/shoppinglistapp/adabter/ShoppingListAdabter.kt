package com.example.shoppinglistapp.adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.model.ShoppingList
import com.example.shoppinglistapp.databinding.ShoppingListViewLayoutBinding
import com.example.shoppinglistapp.room.ShoppingListDao

class ShoppingListAdabter() : RecyclerView.Adapter<ShoppingListAdabter.ListHolder>() {

    private var list : ArrayList<ShoppingList> = arrayListOf()
    private var number :Int = 0
    var pieceViewClikListener: (ShoppingList, Int, Int) -> Unit = { _, _, _ ->}
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