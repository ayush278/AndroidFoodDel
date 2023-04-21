package com.example.fooddel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class FoodDetailActivity : AppCompatActivity() {
    var count = 1
    lateinit var extraslist:MutableList<ExtrasModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("FoodDetailActivity", AppData.selectedFoodItemDetail?.price.toString())
        setContentView(R.layout.activity_food_detail)
        val minusButton: Button = findViewById (R.id.minusButton)
        val plusButton: Button = findViewById (R.id.plusButton)
        val selectedFoodImage: ImageView = findViewById (R.id.selectedFoodImage)
        val itemCountTextView: TextView = findViewById (R.id.itemCountTextView)
        val priceTextView: TextView = findViewById (R.id.priceTextView)
        val selectedFoodName: TextView = findViewById (R.id.selectedFoodName)
        val addToCartButton: Button = findViewById(R.id.addToCartButton)


        addToCartButton.setOnClickListener {
            Log.e("Message", "setOnClickListener")
            if(AppData.cartlist==null){
                AppData.cartlist= arrayListOf()
            }
            AppData.cartlist!!.add(CartModel(AppData.selectedFoodItemDetail?.id,
                AppData.selectedFoodItemDetail?.name!!,
                AppData.selectedFoodItemDetail?.image!!,
                AppData.selectedFoodItemDetail?.price!!,
                AppData.selectedFoodItemDetail?.category!!,
                count
                ));
            Log.e("Message", "${AppData.cartlist?.size!!}")
            Toast.makeText(this, "${AppData.selectedFoodItemDetail?.name!!} is added to the cart", Toast.LENGTH_SHORT).show()

        }


        itemCountTextView.setText(count.toString())
        priceTextView.setText("$"+(count* AppData.selectedFoodItemDetail?.price!!).toString())

        var image: Bitmap? = null
        try {

            val `in` = java.net.URL("http://kasimadalan.pe.hu/foods/images/"+AppData.selectedFoodItemDetail?.image).openStream()
            image = BitmapFactory.decodeStream(`in`);
            selectedFoodImage.setImageBitmap(image);
        }
        catch (e: Exception) {
            Log.e("Error Message", e.message.toString())
            e.printStackTrace()
        }

        minusButton.setOnClickListener {
           if(count>1) {
               count = count - 1

               itemCountTextView.setText(count.toString())
               priceTextView.setText("$"+(count* AppData.selectedFoodItemDetail?.price!!).toString())
           }
        }

        plusButton.setOnClickListener {

                count = count + 1

                itemCountTextView.setText(count.toString())
            priceTextView.setText("$"+(count* AppData.selectedFoodItemDetail?.price!!).toString())


        }
        selectedFoodName.text=AppData.selectedFoodItemDetail?.name
        extraslist=arrayListOf()


        when (AppData.selectedFoodItemDetail?.category) {
            "Drinks"-> {
                Log.e("Message", "Drinks")
                extraslist.add(
                    ExtrasModel(R.drawable.ice_cube
                        ,"Ice cubes"))
                extraslist.add(
                    ExtrasModel(R.drawable.straw
                        ,"Straw"))


            }
            "Meals" -> {
                Log.e("Message", "Meals")
                extraslist.add(
                    ExtrasModel(R.drawable.meal_mustard
                        ,"Sauces"))
                extraslist.add(
                    ExtrasModel(R.drawable.meal_pickles
                        ,"Pickels"))
                extraslist.add(
                    ExtrasModel(R.drawable.meal_spice
                        ,"Seasoning"))


            }
            "Desserts" -> {
                Log.e("Message", "Desserts")
                Log.e("Message", "Meals")
                extraslist.add(
                    ExtrasModel(R.drawable.caramel
                        ,"Caramel"))
                extraslist.add(
                    ExtrasModel(R.drawable.whip_cream
                        ,"Cream"))
                extraslist.add(
                    ExtrasModel(R.drawable.macadamia
                        ,"HZazelNut"))

            }

            else -> {
                Log.e("Message", "Meals")
                extraslist.add(
                    ExtrasModel(R.drawable.meal_mustard
                        ,"Sauces"))
                extraslist.add(
                    ExtrasModel(R.drawable.meal_pickles
                        ,"Pickels"))
                extraslist.add(
                    ExtrasModel(R.drawable.meal_spice
                        ,"Seasoning"))
                Log.e("Message", "Else")
            }
        }

        var recyclerView: RecyclerView = findViewById(R.id.extrasRecyclerView)
        var extrasListAdapter=ExtrasListAdapter(this,extraslist)
        recyclerView.adapter=extrasListAdapter

        val layoutManager = LinearLayoutManager(applicationContext,LinearLayoutManager.HORIZONTAL,false)
        recyclerView.layoutManager = layoutManager
        extrasListAdapter.notifyDataSetChanged()

    }
}