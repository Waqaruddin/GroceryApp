package com.example.groceryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.groceryapp.R
import com.example.groceryapp.database.DBHelper
import com.example.groceryapp.models.FinalOrder
import com.example.groceryapp.models.OrderSummary
import kotlinx.android.synthetic.main.row_order_history_adapter.view.*

class AdapterOrderHistory (val mContext: Context, var mList:ArrayList<FinalOrder>):RecyclerView.Adapter<AdapterOrderHistory.MyViewHolder>(){
lateinit var dbHelper:DBHelper
    inner class MyViewHolder(val itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(orders:FinalOrder){
            itemView.text_view_order_date.text = orders.date.subSequence(0,10)
            if(orders.orderSummary != null){
                itemView.text_view_order_count.text = orders.products.size.toString()
            }
            itemView.text_view_order_total.text = "$"+ orders.orderSummary.ourPrice.toString()
            itemView.text_view_order_id.text = orders._id


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_history_adapter, parent, false)
        dbHelper = DBHelper(mContext)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var order = mList[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

     fun setData(l:ArrayList<FinalOrder>){
        mList = l
        notifyDataSetChanged()
    }
}