package com.example.fooddel

public class AppData {
    companion object{

        //    private val AllFoodList:List<FoodModel>
//        get() {
//           return AllFoodList;
//        }
        var whichCategorySelected="All"
        var AllFoodData: AllFoodModel? = null
        var selectedFoodItemDetail:FoodModel?=null
        var cartlist:MutableList<CartModel>?=null
        var orderList:MutableList<CartModel>?=null

        lateinit var tempFoodList:MutableList<FoodModel>

    }
}
