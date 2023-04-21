package com.example.fooddel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.StrictMode
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class OrderHistoryListAdapter(private var context: Context, private var cartlist:MutableList<CartModel>):
    RecyclerView.Adapter<OrderHistoryListAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        holder.itemView.setOnClickListener {
//            // Toast.makeText(context, "TEST: " + position, Toast.LENGTH_SHORT).show()
//            // Error: "Please specify constructor invocation;
//            // classifier 'Page2' does not have a companion object"
//            AppData.selectedFoodItemDetail= AppData.AllFoodData?.foods!![position];
//            val intent = Intent(context, FoodDetailActivity::class.java)
//            // start your next activity
//            ContextCompat.startActivity(context, intent, null)
//        } // click event

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val ItemsViewModel = cartlist[position]
        var image: Bitmap? = null
        try {

            val `in` = java.net.URL("http://kasimadalan.pe.hu/foods/images/" + ItemsViewModel.image)
                .openStream()
            image = BitmapFactory.decodeStream(`in`);
            holder.foodImage.setImageBitmap(image);
            Log.e("ItemsViewModel.price", ItemsViewModel.price.toString())
        } catch (e: Exception) {
            Log.e("Error Message", e.message.toString())
            e.printStackTrace()
        }
        holder.cartName.text = ItemsViewModel.name;
        holder.cartOrderAmount.text = "Order Amount: ${ItemsViewModel.count}";//ItemsViewModel.porsion;
        holder.cartPrice.text = "Price: $${ItemsViewModel.price} x ${ItemsViewModel.count} = $${ItemsViewModel.price*ItemsViewModel.count}"

        holder.cartCategory.text = "Category: ${ItemsViewModel.category}";//"ItemsViewModel.calories";


    }

    override fun getItemCount(): Int {
        return cartlist.size
    }

    class ItemViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cartName: TextView = ItemView.findViewById(R.id.CartItemNameTextView)
        val cartPrice: TextView = ItemView.findViewById(R.id.CartItemPriceTextView)
        val cartOrderAmount: TextView = ItemView.findViewById(R.id.CartItemOrderAmountTextView)
        val cartCategory: TextView = ItemView.findViewById(R.id.CartItemCategoryTextView)

        val foodImage: ImageView = ItemView.findViewById(R.id.CartItemImageView)

    }
}