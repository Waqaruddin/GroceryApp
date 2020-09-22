package com.example.groceryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.activities.ProductDetailActivity
import com.example.groceryapp.app.Config
import com.example.groceryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product_list_adapter.view.*

class AdapterProductList(var mContext: Context):RecyclerView.Adapter<AdapterProductList.ViewHolder>(){

    var mList:ArrayList<Product> = ArrayList()

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(product:Product){
            itemView.text_view_name.text = product.productName
            itemView.text_view_price.text = product.price.toString()
            Picasso.get()
                .load(Config.IMAGE_URL + product.image)
                .into(itemView.image_view)

            itemView.setOnClickListener {
                var intent = Intent(mContext, ProductDetailActivity::class.java)
                intent.putExtra(Product.KEY_PRODUCT, product)
                mContext.startActivity(intent)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_product_list_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(l:ArrayList<Product>){
        mList = l
        notifyDataSetChanged()
    }
}