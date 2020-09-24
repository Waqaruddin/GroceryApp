package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.CartActivity
import com.example.groceryapp.app.Config
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_adapter_cart.view.*


class AdapterCart(var mContext: Context, var mList:ArrayList<Product>):RecyclerView.Adapter<AdapterCart.MyViewHolder>(){

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product, position: Int) {
            itemView.text_view_cart_name.text = product.productName
            itemView.text_view_quant.text = product.quantity.toString()
            itemView.text_view_price.text = product.price.toString()
            var dbHelper = DBHelper(mContext)

            Picasso.get()
                .load(Config.IMAGE_URL +  product.image)
                .into(itemView.image_view)

            itemView.button_remove.setOnClickListener {
                dbHelper.deleteProduct(product._id!!)
                mList.removeAt(position)
                notifyItemRemoved(position)
            }
            itemView.button_add.setOnClickListener {
//                if(product.quantity >= 0 ) {
//                    product.quantity += 1
//                    itemView.text_view_quant.text = product.quantity.toString()
//                }
                dbHelper.add1(product)
                restart()

            }
            itemView.button_dec.setOnClickListener {
//                if(product.quantity >0 ){
//                    product.quantity -= 1
//                    itemView.text_view_quant.text = product.quantity.toString()
//                }
                dbHelper.sub1(product)
                restart()
            }

    }


        private fun restart() {
            val activity: CartActivity = mContext as CartActivity
            activity.finish()
            activity.overridePendingTransition(0, 0);
            activity.startActivity(Intent(activity, CartActivity::class.java))
            activity.overridePendingTransition(0, 0);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_adapter_cart, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product, position)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(l:ArrayList<Product>){
        mList = l
        notifyDataSetChanged()
    }




}