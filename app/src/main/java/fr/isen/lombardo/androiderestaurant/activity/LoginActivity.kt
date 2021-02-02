package fr.isen.lombardo.androiderestaurant.activity
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.lombardo.androiderestaurant.databinding.ActivityLoginBinding
import fr.isen.lombardo.androiderestaurant.models.JsonResult
import fr.isen.lombardo.androiderestaurant.models.User
import org.json.JSONObject

private lateinit var binding: ActivityLoginBinding
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            if(verifyLogin()) {
                doRequest()
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun doRequest() {
        val queue = Volley.newRequestQueue(this)
        val jsonData= JSONObject()
        jsonData.put("id_shop", "1")
        jsonData.put("email", binding.email.text)
        jsonData.put("password", binding.password.text)
        val url = "http://test.api.catering.bluecodegames.com/user/login"
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            {
                    response ->
                Log.d("Request",response.toString())
                val user= GsonBuilder().create().fromJson(response.toString(), JsonResult::class.java )
                saveUser(user.data)

            },
            {
                    error ->
                Log.d("Error Response", error.message ?:"message vide")
            }
        )
        queue.add(request)
    }

    fun verifyLogin(): Boolean {
        return (binding.email.text?.isNotEmpty() == true &&
                binding.password.text?.count() ?: 0 >= 6)
    }

    fun saveUser(user: User) {
        val sharedPreferences = getSharedPreferences(RegisterActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(RegisterActivity.ID_USER, user.id)
        editor.apply()

        setResult(Activity.RESULT_FIRST_USER)
        finish()
    }



}