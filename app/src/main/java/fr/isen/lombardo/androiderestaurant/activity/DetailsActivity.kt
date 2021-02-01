package fr.isen.lombardo.androiderestaurant.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import fr.isen.lombardo.androiderestaurant.R
import fr.isen.lombardo.androiderestaurant.adapter.DetailViewAdapter
import fr.isen.lombardo.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.lombardo.androiderestaurant.models.Basket
import fr.isen.lombardo.androiderestaurant.models.BasketItem
import fr.isen.lombardo.androiderestaurant.models.Item
import kotlin.math.max

private lateinit var binding: ActivityDetailsBinding
private var itemCount = 1

class DetailsActivity : BaseActivity() {
   companion object {
        const val ITEMS_COUNT = "ITEMS_COUNT"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityDetailsBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val dish = intent.getSerializableExtra("dish") as Item

            binding.dishesIngredients.text = dish.ingredients.map { it.name }.joinToString(", ")
            dish.getAllPictures()?.let {
                binding.detailPager.adapter = DetailViewAdapter(this, it)
            }
                increaseDecreaseStock(dish)
    }
    private fun increaseDecreaseStock(dish: Item) {
        binding.less.setOnClickListener {
            itemCount = max(0,
                itemCount - 1)
            refreshShop(dish)
        }

        binding.more.setOnClickListener {
            itemCount += 1
            refreshShop(dish)
        }
        binding.shopButton.setOnClickListener {
            addToBasket(dish,
                itemCount
            )
        }

    }
    private fun refreshShop(dish: Item) {
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.itemCount.text = itemCount.toString()
        binding.shopButton.text = "${getString(
            R.string.total
        )} $price€"

    }


    private fun addToBasket(dish: Item, count: Int) {

        val basket = Basket.getBasket(this)
        basket.addItem(
            BasketItem(
                dish,
                count
            )
        )
        basket.save(this)
        refreshMenu(basket)
        Snackbar.make(binding.root, getString(R.string.basket_validation), Snackbar.LENGTH_LONG).show()

        val json = GsonBuilder().create().toJson(basket)
        Log.d("basket", json)
    }
    private fun refreshMenu(basket: Basket) {
        val count = basket.itemsCount
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, count)
        editor.apply()
        invalidateOptionsMenu() // refresh l'affichage du menu
    }

}


