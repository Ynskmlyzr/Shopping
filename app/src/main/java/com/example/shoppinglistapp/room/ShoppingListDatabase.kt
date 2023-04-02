package com.example.shoppinglistapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglistapp.model.Constant
import com.example.shoppinglistapp.model.ShoppingList


@Database(entities = [ShoppingList::class], version = 5)
abstract class  ShoppingListDatabase : RoomDatabase() {
    abstract fun listDao () : ShoppingListDao

    companion object{

        @Volatile private var instance : ShoppingListDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){
            instance ?: databaseStorage(context).also {
                instance =it
            }
        }

        private fun databaseStorage(context: Context) = Room.databaseBuilder(
            context.applicationContext,ShoppingListDatabase::class.java,Constant.CONS_LIST).build()
    }


}