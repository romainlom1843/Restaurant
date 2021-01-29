package fr.isen.lombardo.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.lombardo.androiderestaurant.adapter.DetailViewAdapter
import fr.isen.lombardo.androiderestaurant.databinding.ActivityDetailsBinding
import fr.isen.lombardo.androiderestaurant.models.Item

private lateinit var binding: ActivityDetailsBinding
class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dish  = intent.getSerializableExtra("dish") as Item

        binding.dishesIngredients.text = dish.ingredients.map { it.name }.joinToString(", ")
        dish.getAllPictures()?.let {
            binding.detailPager.adapter = DetailViewAdapter(this, it)
        }
    }

}