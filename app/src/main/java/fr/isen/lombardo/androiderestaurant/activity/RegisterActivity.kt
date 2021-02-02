package fr.isen.lombardo.androiderestaurant.activity


import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.lombardo.androiderestaurant.databinding.ActivityRegisterBinding
import fr.isen.lombardo.androiderestaurant.models.JsonResult
import fr.isen.lombardo.androiderestaurant.models.User
import org.json.JSONObject

private lateinit var binding : ActivityRegisterBinding
class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegister.setOnClickListener{
            if(verifyRegister()) {
                doRequest()
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
            }
        }
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private fun doRequest() {
        val queue = Volley.newRequestQueue(this)
        val jsonData= JSONObject()
        jsonData.put("id_shop", "1")
        jsonData.put("firstname", binding.firstname.text)
        jsonData.put("lastname", binding.lastname.text)
        jsonData.put("address", binding.address.text)
        jsonData.put("email", binding.email.text)
        jsonData.put("password", binding.password.text)
        val url = "http://test.api.catering.bluecodegames.com/user/register"
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonData,
            {
                   response ->
                Log.d("Request",response.toString())
                val user= GsonBuilder().create().fromJson(response.toString(), JsonResult::class.java)
                        saveUser(user.data)

            },
            {
                    error ->
                Log.d("Error Response", error.message ?:"message vide")
            }
        )
        queue.add(request)
    }
    fun verifyRegister(): Boolean {
        return (binding.email.text?.isNotEmpty() == true &&
                binding.firstname.text?.isNotEmpty() == true &&
                binding.address.text?.isNotEmpty() == true &&
                binding.lastname.text?.isNotEmpty() == true &&
                binding.password.text?.count() ?: 0 >= 6)
    }
    fun saveUser(user: User) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(ID_USER, user.id)
        editor.apply()

        setResult(Activity.RESULT_FIRST_USER)
        finish()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            setResult(Activity.RESULT_FIRST_USER)
            finish()
        }
    }

    companion object {
        const val REQUEST_CODE = 111
        const val ID_USER = "ID_USER"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }
}

