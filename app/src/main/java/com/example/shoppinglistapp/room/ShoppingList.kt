package com.example.shoppinglistapp.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppinglistapp.model.CompletedShopping
import com.example.shoppinglistapp.model.PastShoppingList
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class ShoppingList(
    @ColumnInfo(name = "Random")
    var random : Int?,
    @ColumnInfo(name = "Name")
    var name : String?,
    @ColumnInfo(name = "Piece")
    var piece : Int?,
    @ColumnInfo(name = "Date")
    var date : String?

):Parcelable{
    @PrimaryKey(autoGenerate = true) var uuid : Int = 0
}
