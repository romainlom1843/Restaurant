package fr.isen.lombardo.androiderestaurant.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User (
            @SerializedName("firstname" ) val name:String,
            @SerializedName("lastname" ) val surname:String,
            @SerializedName("id" ) val id:String):Serializable



