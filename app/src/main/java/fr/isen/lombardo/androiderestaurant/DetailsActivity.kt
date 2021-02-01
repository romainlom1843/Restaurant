package fr.isen.lombardo.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import fr.isen.lombardo.androiderestaurant.adapter.DetailViewAdapter
import fr.isen.lombardo.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.lombardo.androiderestaurant.models.Item
import kotlin.math.max

private lateinit var binding: ActivityDetailsBinding
private var itemCount = 1

class DetailsActivity : AppCompatActivity() {
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
            itemCount = max(0,itemCount - 1)
            refreshShop(dish)
        }

        binding.more.setOnClickListener {
            itemCount += 1
            refreshShop(dish)
        }
        binding.shopButton.setOnClickListener {
            addToBasket(dish, itemCount)
        }

    }
    private fun refreshShop(dish: Item) {
        val price = itemCount * dish.prices.first().price.toFloat()
        binding.itemCount.text = itemCount.toString()
        binding.shopButton.text = "${getString(R.string.total)} $price€"

    }


    private fun addToBasket(dish: Item, count: Int) {

        val basket = Basket.getBasket(this)
        basket.addItem(BasketItem(dish, count))
        basket.save(this)
        val json = GsonBuilder().create().toJson(basket)
        Log.d("basket", json)
    }
}

