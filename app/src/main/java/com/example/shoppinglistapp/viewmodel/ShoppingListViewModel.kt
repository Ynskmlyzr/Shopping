package com.example.shoppinglistapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.adabter.ShoppingDetailAdapter
import com.example.shoppinglistapp.model.ShoppingList
import com.example.shoppinglistapp.room.ShoppingListDao
import com.example.shoppinglistapp.room.ShoppingListDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingListViewModel (application: Application) : BaseViewModel(application) {

    var shopList = MutableLiveData<ArrayList<ShoppingList>>()

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
                }
            })
        }

    }

    fun roomDeleteItem (deleteList : ArrayList<ShoppingList>, position : Int){
        launch(Dispatchers.IO) {
            val dao = ShoppingListDatabase(getApplication()).listDao()
            dao.deleteMessage(position)
            shopList.postValue(deleteList)
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
            shopList.postValue(updateShopList)
        }
    }
    fun roomDataAllDelete (deleteList : ArrayList<ShoppingList>){
        launch(Dispatchers.IO) {
            val dao = ShoppingListDatabase(getApplication()).listDao()
            dao.deleteAllMessage()
            deleteList.clear()
            shopList.postValue(deleteList)
        }
    }
}