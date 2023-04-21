package com.example.fooddel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.Toast
import com.google.gson.Gson

class CartActivity : AppCompatActivity() {
    private val sharedPrefFile = "kotlinsharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        var recyclerView: RecyclerView = findViewById(R.id.cartRecyclerView)
        var payButton:Button=findViewById(R.id.payButton)

        var cartListAdapter=CartListAdapter(this,AppData.cartlist!!)

        recyclerView.adapter=cartListAdapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        cartListAdapter.notifyDataSetChanged()


        payButton.setOnClickListener {

            if(AppData.orderList==null){
                AppData.orderList= arrayListOf()
            }
            for (cartData in AppData.cartlist!!) {
                AppData.orderList!!.add(cartData)
            }

            val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
                Context.MODE_PRIVATE)
            val editor:SharedPreferences.Editor =  sharedPreferences.edit()

            val gson = Gson()
            val json: String = gson.toJson(AppData.orderList)

            editor.putString("orderList", json)
            editor.apply()
            editor.commit()
            AppData.cartlist= null
            Toast.makeText(this, "Payment done", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}