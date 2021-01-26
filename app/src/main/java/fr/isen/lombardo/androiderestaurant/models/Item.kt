package fr.isen.lombardo.androiderestaurant.models

import com.google.gson.annotations.SerializedName

class Item( @SerializedName("name_fr")
            val name:String,
            val images:List<String>,
            val ingredients: List<Ingredient>,
            val prices: List<Price>) {
    fun getPrice() = prices[0].price.toDouble()
    fun getFormattedPrice() = prices[0].price + "€"
    fun getFirstPicture() = if (images.isNotEmpty() && images[0].isNotEmpty()) {
        images[0]
    } else {
        null
    }

    fun getAllPictures() = if (images.isNotEmpty() && images.any { it.isNotEmpty() }) {
        images.filter { it.isNotEmpty() }
    } else {
        null
    }
}

