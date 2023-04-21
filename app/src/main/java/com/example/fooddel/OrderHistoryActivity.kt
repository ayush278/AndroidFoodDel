package com.example.fooddel

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button

class OrderHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        var recyclerView: RecyclerView = findViewById(R.id.orderHistoryRecyclerView)

        var orderHistoryListAdapter=OrderHistoryListAdapter(this,AppData.orderList!!)

        recyclerView.adapter=orderHistoryListAdapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        orderHistoryListAdapter.notifyDataSetChanged()
    }
}