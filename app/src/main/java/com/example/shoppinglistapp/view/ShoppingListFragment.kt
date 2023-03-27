package com.example.shoppinglistapp.view

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.adabter.ShoppingListAdabter
import com.example.shoppinglistapp.databinding.FragmentShoppingListBinding
import com.example.shoppinglistapp.model.CompletedShopping
import com.example.shoppinglistapp.model.ShoppingList
import com.example.shoppinglistapp.room.ShoppingListDao
import com.example.shoppinglistapp.room.ShoppingListDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ShoppingListFragment : Fragment() {

    private lateinit var binding: FragmentShoppingListBinding
    private var list : ArrayList<ShoppingList> = arrayListOf()
    private var shoppingListAdabter = ShoppingListAdabter()
    private lateinit var shoppingListDao: ShoppingListDao
    private var tempCompletedShoppingList: ArrayList<CompletedShopping> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            (arguments)?.let {
                if (Build.VERSION.SDK_INT >= 33) {
                    tempCompletedShoppingList =
                        it.getParcelableArrayList ("completedShoppingList", CompletedShopping::class.java) as ArrayList<CompletedShopping>
                }else{
                    (it.get("completedShoppingList") as ArrayList<CompletedShopping>).let {
                        tempCompletedShoppingList=it
                    }
                }
            }


            rvShoppingList.layoutManager = LinearLayoutManager(context)
            rvShoppingList.adapter = shoppingListAdabter
            shoppingListAdabter.listFill(list)

            val db = context?.let { Room.databaseBuilder(it,ShoppingListDatabase::class.java,"List").fallbackToDestructiveMigration().allowMainThreadQueries().build() }
            if (db != null) {
                shoppingListDao = db.listDao()
            }

            shoppingListDao.getAllList().observe(requireActivity(),{
                it?.let {
                    list.clear()
                    it.forEach {
                        list.add(it)
                    }
                    shoppingListAdabter.listFill(list)
                    if(list.size == 0){
                        imgDelete.visibility = View.GONE
                    }
                    else{
                        imgDelete.visibility = View.VISIBLE
                    }
                    if(list.size == 0){
                        tvEnterAnItem.visibility = View.VISIBLE
                    }
                    else{
                        tvEnterAnItem.visibility = View.GONE
                    }
                }
            })





            shoppingListAdabter.pieceViewClikListener={ shopList,number,listSize->
                shopList.piece=number
                shoppingListDao.update(shopList)
                if(list.size == 0){
                    tvEnterAnItem.visibility=View.VISIBLE
                }
            }
            shoppingListAdabter.deleteViewClikListener={
                shoppingListDao.deleteMessage(it)
            }

            btnAdd.setOnClickListener {

                if (edtItem.text.isEmpty()){
                    Toast.makeText(context,"Please enter an item",Toast.LENGTH_SHORT).show()
                }else{
                if (list.size == 0){

                    list.add(ShoppingList(1,edtItem.text.toString(),1))
                    shoppingListDao.record(list.distinct())
                    edtItem.text = null
                }else{

                        for (x in 0..list.size-1) {
                            if (edtItem.text.toString() == list.get(x).name){
                                Toast.makeText(context,"Please enter different an item",Toast.LENGTH_SHORT).show()
                            }
                        }
                        list.add(ShoppingList(1,edtItem.text.toString(),1))
                        shoppingListDao.record(list.distinct())
                        edtItem.text = null
                    }

                }
            }

            imgDelete.setOnClickListener {
                shoppingListDao.deleteAllMessage()
                imgDelete.visibility = View.GONE
            }

            btnComplete.setOnClickListener {
                val now = Date()
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val now_str = formatter.format(now)
                val completedShopping = CompletedShopping().apply {
                    completedDate = now_str
                    completedShoppingList = list
                }
                tempCompletedShoppingList.add(completedShopping)
                Navigation.findNavController(it).navigate(
                    R.id.action_shoppingListFragment_to_pastShoppingFragment,
                    bundleOf("completedShoppingList" to tempCompletedShoppingList)
                )


            }

            imgBack.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_shoppingListFragment_to_mainFragment)
            }

            imgPastShopping.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    R.id.action_shoppingListFragment_to_pastShoppingFragment,
                    bundleOf("completedShoppingList" to tempCompletedShoppingList))
            }

        }
    }

}