package fr.isen.lombardo.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.lombardo.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.lombardo.androiderestaurant.models.Item
import fr.isen.lombardo.androiderestaurant.models.MenuResult
import org.json.JSONObject

enum class ItemType {
    ENTREE, MAIN, DESSERT

}
class CategoryActivity : AppCompatActivity() {
    private lateinit var bindind: ActivityCategoryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindind = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(bindind.root)

        val selectedItem = intent.getSerializableExtra(MainActivity.CATEGORY_NAME) as? ItemType
        bindind.categoryTitle.text = getCategoryTitle(selectedItem)
        val categoryTitle= getCategoryTitle(selectedItem)
        makeRequest(categoryTitle)
        Log.d("lifecycle", "onCreate")
    }

    private fun loadList(dishes: List<Item>?) {
        val entries = dishes?.map { it.name }
        entries?.let {
            val adapter = CategoryAdapter(entries)
            bindind.recyclerView.layoutManager = LinearLayoutManager(this)
            bindind.recyclerView.adapter = adapter
        }
    }
    private fun getCategoryTitle(item: ItemType?): String {
        return when (item) {
            ItemType.ENTREE -> getString(R.string.entree)
            ItemType.MAIN -> getString(R.string.main)
            ItemType.DESSERT -> getString(R.string.dessert)

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
}
