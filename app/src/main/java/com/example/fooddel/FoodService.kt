package com.example.fooddel
import retrofit2.Call
import retrofit2.http.GET
interface FoodService {
    @GET("foods/getAllFoods.php")
    fun listAllFood(): Call<AllFoodModel>
}