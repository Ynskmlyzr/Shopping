package com.example.shoppinglistapp.adabter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistapp.databinding.PastShoppingListViewLayoutBinding
import com.example.shoppinglistapp.model.CompletedShopping

class ShoppingPastAdapter : RecyclerView.Adapter<ShoppingPastAdapter.PastListHolder> () {

    private var list : ArrayList<CompletedShopping> = arrayListOf()
    var imageViewClikListener: (ArrayList<CompletedShopping>) -> Unit = {}

    class PastListHolder(var binding: PastShoppingListViewLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastListHolder {
        val binding = PastShoppingListViewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PastListHolder(binding)
    }

    override fun onBindViewHolder(holder: PastListHolder, position: Int) {
        holder.binding.apply {
            tvDate.text=list.get(position).completedDate
            consPastShopping.setOnClickListener{
                imageViewClikListener(list)
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    fun listPastFill(shopList : ArrayList<CompletedShopping>){
        list.clear()
        list.addAll(shopList)
        notifyDataSetChanged()
    }

}