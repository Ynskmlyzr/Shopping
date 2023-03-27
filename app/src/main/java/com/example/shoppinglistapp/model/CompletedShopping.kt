package com.example.shoppinglistapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "completedShopping")
data class CompletedShopping(
    @ColumnInfo(name = "completedDate")
    var completedDate: String? = null,
    @ColumnInfo(name = "completedList")
    var completedShoppingList: ArrayList<ShoppingList> = arrayListOf()
) :Parcelable{
    @PrimaryKey(autoGenerate = true) var uuid : Int = 0
}
