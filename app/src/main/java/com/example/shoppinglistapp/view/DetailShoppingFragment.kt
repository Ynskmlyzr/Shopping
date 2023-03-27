package com.example.shoppinglistapp.view

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglistapp.adabter.ShoppingDetailAdapter
import com.example.shoppinglistapp.databinding.FragmentDetailShoppingBinding
import com.example.shoppinglistapp.model.CompletedShopping
import com.example.shoppinglistapp.model.ShoppingList


class DetailShoppingFragment : Fragment() {

    private lateinit var binding: FragmentDetailShoppingBinding
    private var dateComplateShoppingList : ArrayList<CompletedShopping> = arrayListOf()
    private var dateComplateShopList : ArrayList<ShoppingList> = arrayListOf()
    private var shoppingDetailAdapter = ShoppingDetailAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailShoppingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            (arguments)?.let {
                if (Build.VERSION.SDK_INT >= 33) {
                    dateComplateShoppingList =
                        it.getParcelableArrayList ("dateSoppingList", CompletedShopping::class.java) as ArrayList<CompletedShopping>
                }else{
                    (it.get("dateSoppingList") as ArrayList<CompletedShopping>).let {
                        dateComplateShoppingList=it
                    }
                }
            }
            dateComplateShoppingList.forEach {
                tvTitle.text = it.completedDate
            }


            dateComplateShoppingList.forEach {
                dateComplateShopList=it.completedShoppingList
            }

            rvProducts.layoutManager=LinearLayoutManager(context)
            rvProducts.adapter=shoppingDetailAdapter
            shoppingDetailAdapter.listDetailFill(dateComplateShopList)



        }
    }

}