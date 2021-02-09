package fr.isen.lombardo.androiderestaurant.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.lombardo.androiderestaurant.adapter.BasketAdapter
import fr.isen.lombardo.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.lombardo.androiderestaurant.models.Basket
import org.json.JSONObject
import com.android.volley.Request
import fr.isen.lombardo.androiderestaurant.MainActivity
import fr.isen.lombardo.androiderestaurant.models.BasketItem

private lateinit var binding: ActivityBasketBinding
private lateinit var basket : Basket

class BasketActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        basket = Basket.getBasket(this)
        reloadData(basket)


        val intent = Intent(this, RegisterActivity::class.java)
        binding.orderButton.setOnClickListener {
            startActivityForResult(intent, RegisterActivity.REQUEST_CODE)
        }


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

  /*  override fun onDeleteItem(item: BasketItem) {
        basket.items.remove(item)
        basket.save(this)
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RegisterActivity.REQUEST_CODE ) {
            val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
            val idUser = sharedPreferences.getInt(RegisterActivity.ID_USER, -1)
            if(idUser != -1) {
                Log.d("ici", "ici")
                sendOrder(idUser)
            }
        }
    }


    private fun sendOrder(idUser: Int) {
        val message = basket.items.map { "${it.count}x ${it.dish.name}" }.joinToString("\n")
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/user/order"

        val jsonData = JSONObject()
        jsonData.put("id_shop", "1")
        jsonData.put("id_user", idUser)
        jsonData.put("msg", message)

        var request = JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonData,
                { response ->
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Votre commande a bien été prise en compte")
                    builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
                        basket.clear()
                        basket.save(this)
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                    builder.show()
                },
                { error ->
                    error.message?.let {
                        Log.d("request", it)
                    } ?: run {
                        Log.d("request", error.toString())
                        Log.d("request", String(error.networkResponse.data))
                    }
                }
        )
        queue.add(request)
    }

}

