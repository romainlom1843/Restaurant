package fr.isen.lombardo.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

//private lateinit var binding: MainActivityBinding


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        findViewById<TextView>(R.id.entréeTitle).setOnClickListener{
            val intent = Intent(
                this.MainActivity::class.java)
            intent.putExtra("category", "Les Entrées")
            startActivity(intent)

        }
        findViewById<TextView>(R.id.platTitle).setOnClickListener{
            val intent = Intent(
                this.MainActivity::class.java)
            intent.putExtra("category", "Les plats")
            startActivity(intent)

        }
        findViewById<TextView>(R.id.dessertsTitle).setOnClickListener{
            val intent = Intent(
                this.MainActivity::class.java)
            intent.putExtra( "category", "Les desserts")
            startActivity(intent)
        }
        findViewById<TextView>(R.id.menusTitle).setOnClickListener{
            val intent = Intent(
                this.MainActivity::class.java)
            intent.putExtra( "category", "Les menus")
            startActivity(intent)
        }
    }
}