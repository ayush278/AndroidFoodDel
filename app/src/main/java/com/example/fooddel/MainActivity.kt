package com.example.fooddel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.fooddel.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection
import java.net.URL
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreference"

    companion object {
        const val URL_COUNTRY_API = "http://kasimadalan.pe.hu/foods/"
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
      lateinit var foodlist:MutableList<FoodModel>
    private val context:Context=this

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val url = URL("http://kasimadalan.pe.hu/foods/getAllFoods.php")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"  // optional default is GET

            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")

            inputStream.bufferedReader().use {
                it.lines().forEach { line ->
                    println(line)
                }
            }
        }

        val retro = Retrofit.Builder()
            .baseUrl("http://kasimadalan.pe.hu/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        val service = retro.create(FoodService::class.java)
        val countryRequest = service.listAllFood()

//        countryRequest.enqueue(object : Callback<AllFoodModel> {
//            override fun onResponse(call: Call<AllFoodModel>, response: Response<AllFoodModel>) {
//                var listAllFood1: AllFoodModel? = response.body()
//                Log.v(
//                    MainActivity::class.simpleName,
//                    "NAME1111: ${listAllFood1?.foods?.size} \n "
//                )
//                AppData.AllFoodData=listAllFood1;
//                Log.v(
//                    MainActivity::class.simpleName,
//                    "NAME1111 allAbc: ${AppData.AllFoodData?.foods?.size} \n "
//                )
//
//
//            }
//
//            override fun onFailure(call: Call<AllFoodModel>, t: Throwable) {
//                Log.i(MainActivity::class.simpleName, "RETRY AND GOOD LUCK.")
//            }
//
//        }
//        )
        val response: Response<AllFoodModel> = countryRequest.execute()
       val apiResponse: AllFoodModel? = response.body()
        AppData.AllFoodData=apiResponse

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.cardViewFood.setBackgroundResource(R.drawable.upper_rounded_corner);

////        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        foodlist= arrayListOf()
////        foodlist.add(FoodModel(1,"India","https://images.dominos.co.in/new_margherita_2502.jpg",1,"320 kcal" ));
////        foodlist.add(FoodModel(2,"China","https://images.dominos.co.in/new_margherita_2502.jpg",2,"320 kcal" ));
////        foodlist.add(FoodModel(3,"Nepal","https://images.dominos.co.in/new_margherita_2502.jpg",3,"320 kcal" ));
////        foodlist.add(FoodModel(4,"Bhutan","https://images.dominos.co.in/new_margherita_2502.jpg",4,"320 kcal" ));
//
//        if(AppData.AllFoodData!=null&& AppData.AllFoodData?.foods!=null) {
//            Log.v(
//                MainActivity::class.simpleName,
//                "NAME1111 check AppData.AllFoodData!=null"
//            )
//            foodlist= AppData.AllFoodData?.foods!!
////            for (foodData in AppData.AllFoodData?.foods!!) {
////                foodlist.add(FoodModel(foodData.id,foodData.name,foodData.image,2,"sds"))
////            }
//        }else{
//            Log.v(
//                MainActivity::class.simpleName,
//                "NAME1111 check AppData.AllFoodData==null"
//            )
//        }
     //   foodlist= (AppData.AllFoodData?.foods as ArrayList<FoodModel>?)!!


//        LinearLayoutManager(this).also { binding.rvFood.layoutManager = it }
//        var foodListAdapter=FoodListAdapter(context,foodlist)
//
//        binding.rvFood.adapter=foodListAdapter
//
//        foodListAdapter.notifyDataSetChanged()



        //onclick func.
        binding.allFoodImageBtn .setOnClickListener {
            Log.e("Message", "setOnClickListener")
            AppData.whichCategorySelected="All"

            setFoodListNCategoryColorValue(AppData.AllFoodData?.foods!!)
        }
        binding.MealsImageBtn.setOnClickListener {
            AppData.whichCategorySelected="Meals"
            setFoodListNCategoryColorValue(AppData.AllFoodData?.foods!!)

        }
        binding.DessertsImageBtn.setOnClickListener {
            AppData.whichCategorySelected="Desserts"
            setFoodListNCategoryColorValue(AppData.AllFoodData?.foods!!)

        }
        binding.DrinksImageBtn.setOnClickListener {
            AppData.whichCategorySelected="Drinks"
            setFoodListNCategoryColorValue(AppData.AllFoodData?.foods!!)

        }
        setFoodListNCategoryColorValue(AppData.AllFoodData?.foods!!);


    }
    fun setFoodListNCategoryColorValue(  appDataFoodModelList:MutableList<FoodModel>){
       // foodlist= arrayListOf()
        Log.e("Message", "whichCategorySelected:${AppData.whichCategorySelected}")

        when (AppData.whichCategorySelected) {
            "Meals" ->{
                foodlist.clear()
               AppData.tempFoodList= arrayListOf()
                for (foodModel in appDataFoodModelList) {
                    if(foodModel.category=="Meals"){
                        AppData.tempFoodList.add(
                            foodModel
                        )
                    }
                }
                foodlist=AppData.tempFoodList

                binding.MealsFrame.setBackgroundColor(0xFF8e8e93.toInt());
                binding.AllFrame.setBackgroundColor(0xFFebe8e2.toInt());
                binding.DessertsFrame.setBackgroundColor(0xFFebe8e2.toInt());
                binding.DrinksFrame.setBackgroundColor(0xFFebe8e2.toInt());
            }
            "Desserts" -> {
                foodlist.clear()
                AppData.tempFoodList= arrayListOf()
                for (foodModel in appDataFoodModelList) {
                    if(foodModel.category=="Desserts"){
                        AppData.tempFoodList.add(
                            foodModel
                        )
                    }
                }
                foodlist=AppData.tempFoodList
                binding.DessertsFrame.setBackgroundColor(0xFF8e8e93.toInt());
                binding.AllFrame.setBackgroundColor(0xFFebe8e2.toInt());
                binding.MealsFrame.setBackgroundColor(0xFFebe8e2.toInt());
                binding.DrinksFrame.setBackgroundColor(0xFFebe8e2.toInt());
            }
            "Drinks" ->{
                foodlist.clear()
                AppData.tempFoodList= arrayListOf()
                for (foodModel in appDataFoodModelList) {
                    if(foodModel.category=="Drinks"){
                        AppData.tempFoodList.add(
                            foodModel
                        )
                    }
                }
                foodlist=AppData.tempFoodList
                binding.DrinksFrame.setBackgroundColor(0xFF8e8e93.toInt());
                binding.AllFrame.setBackgroundColor(0xFFebe8e2.toInt());
                binding.DessertsFrame.setBackgroundColor(0xFFebe8e2.toInt());
                binding.MealsFrame.setBackgroundColor(0xFFebe8e2.toInt());
            }
            else -> {

                binding.AllFrame.setBackgroundColor(0xFF8e8e93.toInt());
                binding.MealsFrame.setBackgroundColor(0xFFebe8e2.toInt());
                binding.DessertsFrame.setBackgroundColor(0xFFebe8e2.toInt());
                binding.DrinksFrame.setBackgroundColor(0xFFebe8e2.toInt());                if(AppData.AllFoodData!=null&& AppData.AllFoodData?.foods!=null) {
                    Log.v(
                        MainActivity::class.simpleName,
                        "NAME1111 check AppData.AllFoodData!=null"
                    )

                    AppData.tempFoodList= arrayListOf()
                    for (foodModel in appDataFoodModelList) {
                        AppData.tempFoodList.add(
                                foodModel
                            )

                    }
                    foodlist=AppData.tempFoodList

                }else{
                    Log.v(
                        MainActivity::class.simpleName,
                        "NAME1111 check AppData.AllFoodData==null"
                    )
                }
            }
        }

        LinearLayoutManager(this).also { binding.rvFood.layoutManager = it }
        var foodListAdapter=FoodListAdapter(context,foodlist)

        binding.rvFood.adapter=foodListAdapter

        foodListAdapter.notifyDataSetChanged()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_cart ->{
                if(AppData.cartlist!=null&& AppData.cartlist!!.size!=0){
                Log.e(
                    MainActivity::class.simpleName,
                    "NAME1111 check AppData.AllFoodData==null"
                )
                val intent = Intent(context, CartActivity::class.java)
                // start your next activity
                ContextCompat.startActivity(context, intent, null)
              return true
            }else{
                    Toast.makeText(this, "Cart is empty", Toast.LENGTH_LONG).show()

                    return  true
            }
            }
            R.id.action_order ->{
                val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                    Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                val gson = Gson()
                val json: String? = sharedPreferences.getString("orderList","")
                // below line is to get the type of our array list.
                val type: Type = object : TypeToken<ArrayList<CartModel?>?>() {}.type

                // in below line we are getting data from gson
                // and saving it to our array list

                var tempList:MutableList<CartModel> = gson.fromJson<Any>(json, type) as ArrayList<CartModel>

                // checking below if the array list is empty or not
                if (tempList == null) {

                    tempList = ArrayList()
                }else{
                    AppData.orderList=tempList
                }

                if(AppData.orderList!=null&& AppData.orderList!!.size!=0){
                    Log.e(
                        MainActivity::class.simpleName,
                        "NAME1111 check AppData.AllFoodData==null"
                    )
                    val intent = Intent(context, OrderHistoryActivity::class.java)
                    // start your next activity
                    ContextCompat.startActivity(context, intent, null)
                    return true
                }else{
                    Toast.makeText(this, "Order history is not present", Toast.LENGTH_LONG).show()

                    return  true
                }
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}