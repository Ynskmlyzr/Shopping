package com.example.shoppinglistapp.viewmodel

import android.app.Application
import android.content.Context
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.adabter.ShoppingDetailAdapter
import com.example.shoppinglistapp.model.ShoppingList
import com.example.shoppinglistapp.room.ShoppingListDao
import com.example.shoppinglistapp.room.ShoppingListDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ShoppingListViewModel (application: Application) : BaseViewModel(application) {

    var liste : ArrayList<ShoppingList> = arrayListOf()
    var shopList = MutableLiveData<ArrayList<ShoppingList>>()
    var _flowTotal = MutableStateFlow(liste)
    var flowTotal : StateFlow<ArrayList<ShoppingList>> = _flowTotal



    fun upload(listele: ArrayList<ShoppingList>){
        _flowTotal.value = listele
    }

    fun roomDataList(listeler : ArrayList<ShoppingList>){
           shopList.value = listeler
    }

    fun roomList(owner: LifecycleOwner,list: ArrayList<ShoppingList>){
        launch {
            val dao = ShoppingListDatabase(getApplication()).listDao()
            dao.getAllList().observe(owner,{
                it?.let {
                    list.clear()
                    it.forEach {
                        list.add(it)
                        roomDataList(list)
                    }
                    upload(list)
                }
            })
        }

    }

    fun roomDeleteItem (deleteList : ArrayList<ShoppingList>, position : Int){
        launch(Dispatchers.IO) {
            val dao = ShoppingListDatabase(getApplication()).listDao()
            dao.deleteMessage(position)
        }
    }

    fun roomDataStorage (storageList : ArrayList<ShoppingList>){
        launch(Dispatchers.IO) {
            val dao = ShoppingListDatabase(getApplication()).listDao()
            dao.record(storageList)
        }
    }

    fun roomDataUpdate (updateShopList : ArrayList<ShoppingList>, updateList : ShoppingList){
        launch(Dispatchers.IO) {
            val dao = ShoppingListDatabase(getApplication()).listDao()
            dao.update(updateList)
        }
    }
    fun roomDataUpdate2 (number:Int , id:Int,updateShopListe : ArrayList<ShoppingList>){
        launch(Dispatchers.IO) {
            val dao = ShoppingListDatabase(getApplication()).listDao()
            dao.up(number,id)
        }
    }
    fun roomDataAllDelete (deleteList : ArrayList<ShoppingList>){
        launch(Dispatchers.IO) {
            val dao = ShoppingListDatabase(getApplication()).listDao()
            dao.deleteAllMessage()
        }
    }
}