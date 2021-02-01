package fr.isen.lombardo.androiderestaurant.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.lombardo.androiderestaurant.adapter.BasketAdapter
import fr.isen.lombardo.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.lombardo.androiderestaurant.models.Basket

class BasketActivity : AppCompatActivity()/*, BasketCellInterface */{
    lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reloadData(Basket.getBasket(this))
    }

    private fun reloadData(basket: Basket) {
        binding.basketRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.basketRecyclerView.adapter = BasketAdapter(basket, this) {
            val basket = Basket.getBasket(this)
            val itemToDelete = basket.items.firstOrNull { it.dish.name == it.dish.name }
            basket.items.remove(itemToDelete)
            basket.save(this)
            reloadData(basket)
        }
    }

    /*
    override fun onDeleteItem(item: BasketItem) {
        val basket = Basket.getBasket(this)
        val itemToDelete = basket.items.firstOrNull { it.dish.name == item.dish.name }
        basket.items.remove(itemToDelete)
        basket.save(this)
        reloadData(basket)
    }

    override fun onShowDetail(item: BasketItem) {
        TODO("Not yet implemented")
    }
     */
}
