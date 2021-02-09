package fr.isen.lombardo.androiderestaurant.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.lombardo.androiderestaurant.MainActivity
import fr.isen.lombardo.androiderestaurant.R
import fr.isen.lombardo.androiderestaurant.adapter.CategoryAdapter
import fr.isen.lombardo.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.lombardo.androiderestaurant.models.Item
import fr.isen.lombardo.androiderestaurant.models.MenuResult
import org.json.JSONObject

enum class ItemType {
    ENTREE, PLAT, DESSERT

}
class CategoryActivity : BaseActivity() {
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedItem = intent.getSerializableExtra(MainActivity.CATEGORY_NAME) as? ItemType
        binding.categoryTitle.text = getCategoryTitle(selectedItem)
        val categoryTitle= getCategoryTitle(selectedItem)
        makeRequest(categoryTitle)
        Log.d("lifecycle", "onCreate")
    }

    private fun loadList(dishes: List<Item>?) {
        dishes?.let {
            val adapter =
                CategoryAdapter(it) { dish ->
                    val intent = Intent(this, DetailsActivity::class.java)
                    intent.putExtra("dish", dish)
                    startActivity(intent)
                    Log.d("dish", "selected dish ${dish.name}")
                }
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
            binding.recyclerView.adapter = adapter
        }
    }
    private fun getCategoryTitle(item: ItemType?): String {
        return when (item) {
            ItemType.ENTREE -> getString(
                R.string.entree
            )
            ItemType.PLAT -> getString(
                R.string.main
            )
            ItemType.DESSERT -> getString(
                R.string.dessert
            )

            else -> ""
        }

    }

    private fun makeRequest(categoryTitle:String) {
        val queue = Volley.newRequestQueue(this)
        val jsonData= JSONObject()
        jsonData.put("id_shop", 1)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        //val url="https://google.com
        val request = JsonObjectRequest(Request.Method.POST,
            url,
            jsonData,
            {
                    response ->
                    Log.d("Request",response.toString())
                    val menu=GsonBuilder().create().fromJson(response.toString(),MenuResult::class.java )
                    val items = menu.data.firstOrNull() {
                      categoryTitle == it.name
                    }?.items
                    if(items != null)
                    {
                        loadList(items)
                    }else
                    {
                        Log.d("categories", "no category")
                    }
            },
            {
                    error ->
                    Log.d("Request", error.localizedMessage)
            }
        )
        queue.add(request)
    }
    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle", "onRestart")
    }

    override fun onDestroy() {
        Log.d("lifecycle", "onDestroy")
        super.onDestroy()
    }
}
