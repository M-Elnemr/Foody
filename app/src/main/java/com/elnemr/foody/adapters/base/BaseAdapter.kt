package com.elnemr.foody.adapters.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.elnemr.foody.models.Result


abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    abstract fun setDataList(dataList: List<T>)

    abstract fun addDataList(dataList: List<T>)

    abstract fun clearDataList()

}