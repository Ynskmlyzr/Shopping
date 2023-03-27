package com.example.shoppinglistapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters


@Database(entities = [ShoppingList::class], version = 4)
abstract class  ShoppingListDatabase : RoomDatabase() {
    abstract fun listDao () : ShoppingListDao
}