package com.example.groceryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.groceryapp.fragments.ProductFragment
import com.example.groceryapp.models.SubCategory

class AdapterFragment(fm:FragmentManager):FragmentPagerAdapter(fm){

    var mFragmentList:ArrayList<Fragment> = ArrayList()
    var mTitleList:ArrayList<String> = ArrayList()
    override fun getCount(): Int {
        return mTitleList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun addFragment(subCategory: SubCategory){
        mTitleList.add(subCategory.subName)
        mFragmentList.add(ProductFragment.newInstance(subCategory.subId))
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }



}