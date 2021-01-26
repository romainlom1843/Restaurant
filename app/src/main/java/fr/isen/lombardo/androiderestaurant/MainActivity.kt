package fr.isen.lombardo.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.util.Log
import android.widget.Button
import android.widget.Toast
import fr.isen.lombardo.androiderestaurant.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.entreeTitle.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
        binding.platTitle.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
        binding.dessertsTitle.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
        binding.menusTitle.setOnClickListener{
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }


    }
    private fun statCategoryActivity(item: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }
    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }
}