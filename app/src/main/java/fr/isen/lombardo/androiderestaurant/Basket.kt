package fr.isen.lombardo.androiderestaurant

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import fr.isen.lombardo.androiderestaurant.models.Item
import java.io.File
import java.io.Serializable


class Basket (@SerializedName("items" ) val items: MutableList<BasketItem>): Serializable {

    fun addItem(item: BasketItem) {
        val existingItem = items.firstOrNull {
            it.dish.name == item.dish.name
        }
        existingItem?.let {
            existingItem.count += item.count
        } ?: run {
            items.add(item)
        }
    }

    fun save(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
        jsonFile.writeText(GsonBuilder().setPrettyPrinting().create().toJson(this))
    }

    companion object {
        fun getBasket(context: Context): Basket {
            val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
            return if(jsonFile.exists()) {
                val json = jsonFile.readText()
                GsonBuilder().setPrettyPrinting().create().fromJson(json, Basket::class.java)
            } else {
                Basket(mutableListOf())
            }
        }

        const val BASKET_FILE = "basket.json"
    }
}

class BasketItem(@SerializedName("dish" ) val dish: Item, var count: Int): Serializable {}