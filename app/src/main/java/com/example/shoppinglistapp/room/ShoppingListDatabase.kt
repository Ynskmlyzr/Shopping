package com.example.shoppinglistapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppinglistapp.model.ShoppingList


@Database(entities = [ShoppingList::class], version = 4)
abstract class  ShoppingListDatabase : RoomDatabase() {
    abstract fun listDao () : ShoppingListDao
}