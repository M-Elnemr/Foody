package com.elnemr.foody.adapters

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    private val resultBundle: Bundle,
    private val fragmentList: ArrayList<Fragment>,
    lifecycle: Lifecycle,
    fm: FragmentManager
) : FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        fragmentList[position].arguments = resultBundle
        return fragmentList[position]
    }


}