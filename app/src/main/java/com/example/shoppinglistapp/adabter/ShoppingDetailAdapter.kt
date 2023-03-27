package com.example.shoppinglistapp.adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.databinding.DetailShoppingViewLayoutBinding
import com.example.shoppinglistapp.room.ShoppingList

class ShoppingDetailAdapter : RecyclerView.Adapter<ShoppingDetailAdapter.DetailListHolder> () {

    private var list:ArrayList<ShoppingList> = arrayListOf()

    class DetailListHolder(var binding: DetailShoppingViewLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailListHolder {
        val binding = DetailShoppingViewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DetailListHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailListHolder, position: Int) {
        holder.binding.apply {

            tvProduct.text=list.get(position).name
            tvPiece.text=list.get(position).piece.toString()

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun listDetailFill(shopList : ArrayList<ShoppingList>){
        list.clear()
        list.addAll(shopList)
        notifyDataSetChanged()
    }

}