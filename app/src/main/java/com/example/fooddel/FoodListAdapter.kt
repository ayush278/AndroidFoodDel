package com.example.fooddel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.StrictMode
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class FoodListAdapter(private var context: Context,private var foodlist:MutableList<FoodModel>):
    RecyclerView.Adapter<FoodListAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.food_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
           // Toast.makeText(context, "TEST: " + position, Toast.LENGTH_SHORT).show()
            // Error: "Please specify constructor invocation;
            // classifier 'Page2' does not have a companion object"
            AppData.selectedFoodItemDetail= AppData.tempFoodList[position]//AppData.AllFoodData?.foods!![position];
            val intent = Intent(context, FoodDetailActivity::class.java)
            // start your next activity
            startActivity(context,intent,null)
        } // click event

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val ItemsViewModel = foodlist[position]
        var image: Bitmap? = null
        try {

            val `in` = java.net.URL("http://kasimadalan.pe.hu/foods/images/"+ItemsViewModel.image).openStream()
            image = BitmapFactory.decodeStream(`in`);
            holder.foodImage.setImageBitmap(image);
            Log.e("ItemsViewModel.price",ItemsViewModel.price.toString())
        }
        catch (e: Exception) {
            Log.e("Error Message", e.message.toString())
            e.printStackTrace()
        }
        holder.foodName.text =ItemsViewModel.name;
        holder.foodPrice.text ="$"+ItemsViewModel.price.toString();
        holder.foodPorsion.text ="1 porsion";//ItemsViewModel.porsion;
        holder.foodCalories.text ="320kcal";//"ItemsViewModel.calories";
        holder.foodDuration.text ="15 min";//"ItemsViewModel.duration";


    }

    override fun getItemCount(): Int {
       return foodlist.size
    }

    class ItemViewHolder(ItemView: View):RecyclerView.ViewHolder(ItemView) {
        val foodName:TextView=ItemView.findViewById(R.id.textViewName)
        val foodPrice:TextView=ItemView.findViewById(R.id.textViewPrice)
        val foodPorsion:TextView=ItemView.findViewById(R.id.textViewPorsion)
        val foodCalories:TextView=ItemView.findViewById(R.id.textViewCalories)
        val foodDuration:TextView=ItemView.findViewById(R.id.textViewTime)

        val foodImage: ImageView =ItemView.findViewById(R.id.imageViewFlag)

    }
}