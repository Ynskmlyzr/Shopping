package com.example.shoppinglistapp.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglistapp.model.ShoppingList
import java.util.concurrent.Flow


@androidx.room.Dao
interface ShoppingListDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun record (shoppingList: List<ShoppingList>)

        @Query("SELECT * FROM shoppingList")
        fun getAllList(): LiveData<List<ShoppingList>>

        @Update
        fun update(shoppingList: ShoppingList)

        @Query("DELETE FROM shoppingList")
        fun deleteAllMessage()

        @Query("DELETE FROM shoppingList WHERE uuid= :id ")
        fun deleteMessage(id :Int)
}