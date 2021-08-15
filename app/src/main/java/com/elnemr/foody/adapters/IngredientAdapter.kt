package com.elnemr.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import com.elnemr.foody.adapters.base.BaseAdapter
import com.elnemr.foody.adapters.base.BaseViewHolder
import com.elnemr.foody.adapters.base.MainDiffUtil
import com.elnemr.foody.databinding.IngredientRowLayoutBinding
import com.elnemr.foody.models.ExtendedIngredients

class IngredientAdapter : BaseAdapter<ExtendedIngredients>() {

    private val mDiff = AsyncListDiffer(this, MainDiffUtil<ExtendedIngredients>())

    override fun setDataList(dataList: List<ExtendedIngredients>) {
        mDiff.submitList(dataList)
    }

    override fun addDataList(dataList: List<ExtendedIngredients>) {
        mDiff.currentList.addAll(dataList)
    }

    override fun clearDataList() {
        mDiff.currentList.clear()
    }

    class IngredientHolder(private val binding: IngredientRowLayoutBinding) :
        BaseViewHolder<ExtendedIngredients>(binding) {
        override fun bind(result: ExtendedIngredients) {
            binding.data = result
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ExtendedIngredients> {
        val inflater = LayoutInflater.from(parent.context)
        val view = IngredientRowLayoutBinding.inflate(inflater, parent, false)

        return IngredientHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ExtendedIngredients>, position: Int) {
        val data = mDiff.currentList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = mDiff.currentList.size
}