package fr.isen.lombardo.androiderestaurant.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.lombardo.androiderestaurant.adapter.DetailViewAdapter
import fr.isen.lombardo.androiderestaurant.databinding.ActivityDetail2Binding
import fr.isen.lombardo.androiderestaurant.models.Item

private lateinit var binding : ActivityDetail2Binding
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val dish  = intent.getSerializableExtra("dish") as Item
        dish.getAllPictures()?.let {
            binding.detailPager.adapter = DetailViewAdapter(this, it)
        }
    }
}