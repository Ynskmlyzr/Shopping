package com.example.shoppinglistapp.view

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent.DispatcherState
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.shoppinglistapp.R
import com.example.shoppinglistapp.adabter.ShoppingListAdabter
import com.example.shoppinglistapp.databinding.FragmentShoppingListBinding
import com.example.shoppinglistapp.model.CompletedShopping
import com.example.shoppinglistapp.model.Constant
import com.example.shoppinglistapp.model.ShoppingList
import com.example.shoppinglistapp.room.ShoppingListDao
import com.example.shoppinglistapp.room.ShoppingListDatabase
import com.example.shoppinglistapp.viewmodel.ShoppingListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ShoppingListFragment : Fragment() {

    private lateinit var binding: FragmentShoppingListBinding
    private var list : ArrayList<ShoppingList> = arrayListOf()
    private var shoppingListAdabter = ShoppingListAdabter()
    private var tempCompletedShoppingList: ArrayList<CompletedShopping> = arrayListOf()
    private lateinit var shoppingListViewModel : ShoppingListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shoppingListViewModel = ViewModelProvider(this).get(ShoppingListViewModel::class.java)
        shoppingListViewModel.roomDataList(list)
        shoppingListViewModel.roomList(requireActivity(),list)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingListBinding.inflate(layoutInflater)
        return binding.root
    }

    @OptIn(ExperimentalCoroutinesApi::class)
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

            rvShoppingList.layoutManager = LinearLayoutManager(context)
            rvShoppingList.adapter = shoppingListAdabter
            shoppingListAdabter.listFill(list)
            lifecycleScope.launchWhenStarted {
                shoppingListViewModel.flowTotal.collect{
                    shoppingListAdabter.listFill(it)
                    visibility()
                }
            }




            shoppingListAdabter.pieceViewClikListener={ shopList,number,listSize,id->
                    shopList.piece=number
                    list.get(listSize).piece = number
                    shoppingListViewModel.roomDataUpdate(list,shopList)
                    shoppingListAdabter.listFill(list)
            }

            shoppingListAdabter.deleteViewClikListener={ uid,position ->
                    list.removeAt(position)
                    shoppingListViewModel.roomDeleteItem(list,uid)
                    shoppingListAdabter.listFill(list)
                    visibility()
            }

            btnAdd.setOnClickListener {
                dataAdd()
                shoppingListAdabter.listFill(list)
                visibility()
            }

            imgDelete.setOnClickListener {
                    list.clear()
                    shoppingListViewModel.roomDataAllDelete(list)
                    shoppingListAdabter.listFill(list)
                    visibility()
            }

            btnComplete.setOnClickListener {
                complate()
            }

            imgBack.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_shoppingListFragment_to_mainFragment)
            }

            imgPastShopping.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    R.id.action_shoppingListFragment_to_pastShoppingFragment,
                    bundleOf(Constant.CONS_COMP_SHOP_LIST to tempCompletedShoppingList))
            }

        }
    }

    fun visibility (){
        binding.apply {
            if(list.size == 0){
                imgDelete.visibility = View.GONE
                tvEnterAnItem.visibility = View.VISIBLE
            }else{
                imgDelete.visibility = View.VISIBLE
                tvEnterAnItem.visibility = View.GONE
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun dataAdd(){
        binding.apply {
            if (edtItem.text.isEmpty()){
                Toast.makeText(context,Constant.CONS_PLEASE_ITEM,Toast.LENGTH_SHORT).show()
            }else{
                if (list.size == 0){
                    list.add(ShoppingList(edtItem.text.toString().trim().lowercase().capitalize(),1))
                    shoppingListViewModel.roomDataStorage(list)
                    edtItem.text = null

                }else{
                    for (x in 0..list.size-1){
                        if(list.get(x).name!!.lowercase().trim() == edtItem.text.toString().lowercase().trim()){
                            var number: Int = list.get(x).piece!!.toInt()
                            number = number + 1
                            list.get(x).piece = number
                            list.get(x).name = list.get(x).name.toString().trim().lowercase().capitalize()
                            shoppingListViewModel.roomDataUpdate(list,list.get(x))
                            edtItem.text=null
                        }
                    }
                    if(edtItem.text.isNotEmpty()){
                        list.add(ShoppingList(edtItem.text.toString().trim().lowercase().capitalize(),1))
                        shoppingListViewModel.roomDataStorage(list)
                        edtItem.text = null
                    }
                }

            }
        }
    }

    fun complate (){
        binding.apply {
            if(list.size == 0){
                Toast.makeText(context,"Please enter different an item",Toast.LENGTH_SHORT).show()
            }else{
                val now = Date()
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
                val now_str = formatter.format(now)
                val completedShopping = CompletedShopping().apply {
                    completedDate = now_str
                    completedShoppingList = list
                }
                view?.let {
                    tempCompletedShoppingList.add(completedShopping)
                    Navigation.findNavController(it).navigate(
                        R.id.action_shoppingListFragment_to_pastShoppingFragment,
                        bundleOf(Constant.CONS_COMP_SHOP_LIST to tempCompletedShoppingList)
                    )
                }

            }
        }
    }

}