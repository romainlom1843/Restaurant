package fr.isen.lombardo.androiderestaurant.models

import com.google.gson.annotations.SerializedName

class Category(@SerializedName("name_fr" ) val name:String, val items:List<Item>){}