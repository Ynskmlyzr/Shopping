package com.example.shoppinglistapp.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.adabter.ShoppingPastAdapter
import com.example.shoppinglistapp.databinding.FragmentPastShoppingBinding
import com.example.shoppinglistapp.model.CompletedShopping
import com.example.shoppinglistapp.model.Constant
import com.example.shoppinglistapp.model.ShoppingList
import com.example.shoppinglistapp.room.ShoppingListDao
import com.example.shoppinglistapp.room.ShoppingListDatabase

class PastShoppingFragment : Fragment() {

    private lateinit var binding: FragmentPastShoppingBinding
    private var shoppingPastAdapter = ShoppingPastAdapter()
    private var tempCompletedShoppingList: ArrayList<CompletedShopping> = arrayListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPastShoppingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            (arguments)?.let {
                if (Build.VERSION.SDK_INT >= 33) {
                    tempCompletedShoppingList =
                        it.getParcelableArrayList (Constant.CONS_COMP_SHOP_LIST, CompletedShopping::class.java) as ArrayList<CompletedShopping>
                }else{
                    (it.get(Constant.CONS_COMP_SHOP_LIST) as ArrayList<CompletedShopping>).let {
                        tempCompletedShoppingList=it
                    }
                }
            }


            rvPastShopping.layoutManager=LinearLayoutManager(context)
            rvPastShopping.adapter=shoppingPastAdapter
            shoppingPastAdapter.listPastFill(tempCompletedShoppingList)


            if(tempCompletedShoppingList.size == 0){
                btnDelete.visibility = View.GONE
            }else{
                btnDelete.visibility = View.VISIBLE
            }

            btnDelete.setOnClickListener {
                tempCompletedShoppingList.clear()
                shoppingPastAdapter.listPastFill(tempCompletedShoppingList)
                btnDelete.visibility = View.GONE
            }


            shoppingPastAdapter.imageViewClikListener={

                Navigation.findNavController(rvPastShopping).navigate(
                    R.id.action_pastShoppingFragment_to_mainFragment,
                    bundleOf("dateSoppingList" to it)
                )
            }

            imgBack.setOnClickListener {

                Navigation.findNavController(it).navigate(
                    R.id.action_pastShoppingFragment_to_shoppingListFragment,
                    bundleOf(Constant.CONS_COMP_SHOP_LIST to tempCompletedShoppingList))

            }

        }
    }

}