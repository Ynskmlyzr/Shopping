package com.example.shoppinglistapp.model

import android.os.Parcelable
import androidx.room.Entity
import com.example.shoppinglistapp.room.ShoppingList
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PastShoppingList(
    var shoppingList :ArrayList<String>,
):Parcelable {

}