package fr.isen.lombardo.androiderestaurant.models

import com.google.gson.annotations.SerializedName

class Item( @SerializedName("name_fr")
            val name:String,
            @SerializedName("images") val image:List<String>,
            @SerializedName("ingredients") val ingredients: List<Ingredient>,
            @SerializedName("prices") val prices: List<Price>) {
    fun getPrice() = prices[0].price.toDouble()
    fun getFormattedPrice() = prices[0].price + "€"
    fun getFirstPicture() = if (image.isNotEmpty() && image[0].isNotEmpty()) {
        image[0]
    } else {
        null
    }

    fun getAllPictures() = if (image.isNotEmpty() && image.any { it.isNotEmpty() }) {
        image.filter { it.isNotEmpty() }
    } else {
        null
    }


}

