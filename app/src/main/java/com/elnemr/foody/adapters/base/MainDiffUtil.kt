package com.elnemr.foody.adapters.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class MainDiffUtil<T> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = oldItem === newItem


    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem


}
