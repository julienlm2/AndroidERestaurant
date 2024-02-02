package fr.isen.lemoal.androiderestaurant.model

import com.google.gson.annotations.SerializedName


data class DishResult(

    @SerializedName("data") var data: ArrayList<Data> = arrayListOf()

)