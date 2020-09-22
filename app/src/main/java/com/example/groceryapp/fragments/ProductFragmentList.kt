package com.example.groceryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.groceryapp.R
import com.example.groceryapp.adapters.AdapterProductList
import com.example.groceryapp.app.Endpoints
import com.example.groceryapp.models.Product
import com.example.groceryapp.models.ProductResult
import com.example.groceryapp.models.SubCategory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_product_list.view.*


class ProductFragment : Fragment() {
    var mList:ArrayList<Product> = ArrayList()
    lateinit var adapter:AdapterProductList
    private var subId:Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subId = it.getInt(SubCategory.KEY_SUB_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_product_list, container, false)
        init(view)

        return view
    }

    private fun init(view:View) {

        getData()
        adapter = AdapterProductList(view.context)
        view.fragment_recycler_view.adapter = adapter
        view.fragment_recycler_view.layoutManager = LinearLayoutManager(activity)
    }

    private fun getData(){
        var request = StringRequest(
            Request.Method.GET,
            Endpoints.getProductBySubId(subId),
            {
                var gson = Gson()
                var productResult = gson.fromJson(it, ProductResult::class.java)
                mList.addAll(productResult.data)
                adapter.setData(mList)

            },
            {

            }
        )
        Volley.newRequestQueue(activity).add(request)
    }

    companion object {

        @JvmStatic
        fun newInstance(subId: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putInt(SubCategory.KEY_SUB_ID, subId)
                }
            }
    }
}