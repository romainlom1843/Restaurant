package fr.isen.lombardo.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.lombardo.androiderestaurant.activity.CategoryActivity
import fr.isen.lombardo.androiderestaurant.activity.ItemType
import fr.isen.lombardo.androiderestaurant.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.entreeTitle.setOnClickListener{
            startCategoryActivity(ItemType.ENTREE)
        }
        binding.platTitle.setOnClickListener{
            startCategoryActivity(ItemType.MAIN)
        }
        binding.dessertsTitle.setOnClickListener{
            startCategoryActivity(ItemType.DESSERT)
        }



    }
    private fun startCategoryActivity(item: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }
    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }
}