package fr.isen.lombardo.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.lombardo.androiderestaurant.databinding.ActivityCategoryBinding

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

        loadList()

        Log.d("lifecycle", "onCreate")
    }
    private fun loadList() {
        var entries = listOf<String>("salade", "boeuf", "glace")
        val adapter = CategoryAdapter(entries)
        bindind.recyclerView.layoutManager = LinearLayoutManager(this)
        bindind.recyclerView.adapter = adapter
    }

    private fun getCategoryTitle(item: ItemType?): String {
        return when(item) {
            ItemType.ENTREE -> getString(R.string.entrée)
            ItemType.MAIN -> getString(R.string.main)
            ItemType.DESSERT -> getString(R.string.dessert)
            ItemType.MENU -> getString(R.string.menu)
            else -> ""
        }

     /*   val q = Volley.newRequestQueue(this)
        val url = "https://google.com"
        val Stringrequest = StringRequest(Request.method.GET,
            url,Response.Listener<String>{response ->
                Log.d("Request", response)
            },Response.ErrorListener{Log.d("Request", it.Localized)}*/
    }
}