package fr.isen.lombardo.androiderestaurant

import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.AccessNetworkConstants
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.lombardo.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.lombardo.androiderestaurant.models.Item
import fr.isen.lombardo.androiderestaurant.models.MenuResult
import org.json.JSONObject

enum class ItemType {
    ENTREE, MAIN, DESSERT, MENU
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

       // if (bindind.categoryTitle.text == "Entrées") {
        loadList(/*categoryTitle*/)
        makeRequest(categoryTitle)
       // }
        Log.d("lifecycle", "onCreate")
    }

    private fun loadList(/*categoryTitle:String*/) {
        var entries = listOf<String>("tagliatelles pesto", "tartare de dorade", "salade césar")
        val adapter = CategoryAdapter(entries)
        bindind.recyclerView.layoutManager = LinearLayoutManager(this)
        bindind.recyclerView.adapter = adapter
    }

    private fun getCategoryTitle(item: ItemType?): String {
        return when (item) {
            ItemType.ENTREE -> getString(R.string.entrée)
            ItemType.MAIN -> getString(R.string.main)
            ItemType.DESSERT -> getString(R.string.dessert)
            ItemType.MENU -> getString(R.string.menu)
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
                    menu.data.firstOrNull() {
                      categoryTitle == it.name
                    }
            },
            {
                    error ->
                    Log.d("Request", error.localizedMessage)
            }
        )

        /*val stringRequest = StringRequest(
            Request.Method.GET,
            url, Response.Listener<String> { response ->
                Log.d("Request", response)
            }, Response.ErrorListener { error ->
                Log.d("Request", error.localizedMessage)
            }
        )*/

        queue.add(request)
    }
/*    private fun displayCategories(menu: List<Item>){
        binding.categorieLoader.isVisible = false
        binding.listCategory.isVisible = true

        binding.listCategory.LayoutManager = LinearLayoutManager(this)
        binding.listCategory.adpater = CategoryAdapter(menu){
            val intent = Intent( this, DetailsActivity::class.java)
            intent.putExtra("dish", it)
        startActivity(intent)
        }
    }*/
}
