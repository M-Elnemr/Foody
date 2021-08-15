package com.elnemr.foody.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import com.elnemr.foody.adapters.base.BaseAdapter
import com.elnemr.foody.adapters.base.BaseViewHolder
import com.elnemr.foody.databinding.RecipesRowLayoutBinding
import com.elnemr.foody.models.Result
import com.elnemr.foody.adapters.base.MainDiffUtil


class RecipesAdapter : BaseAdapter<Result>() {

    private val mDiffer = AsyncListDiffer(this, MainDiffUtil<Result>())

    class RecipesHolder(private val binding: RecipesRowLayoutBinding) :
        BaseViewHolder<Result>(binding) {
        override fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
        return RecipesHolder(binding)
    }

    override fun getItemCount(): Int = mDiffer.currentList.size

    override fun setDataList(dataList: List<Result>) {
        mDiffer.submitList(dataList)
    }

    override fun addDataList(dataList: List<Result>) {
        mDiffer.currentList.addAll(dataList)
    }

    override fun clearDataList() {
        mDiffer.currentList.clear()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Result>, position: Int) {
        val data = mDiffer.currentList[position]
        holder.bind(data)
    }

}

//class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipesHolder>() {
//
//    private var recipes = emptyList<Result>()
//
//    class RecipesHolder(private val binding: RecipesRowLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//            fun bind(result: Result){
//                binding.result = result
//                binding.executePendingBindings()
//            }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
//        return RecipesHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: RecipesHolder, position: Int) {
//        val data = recipes[position]
//        holder.bind(data)
//    }
//
//    override fun getItemCount(): Int = recipes.size
//
//    fun setData(data: FoodRecipe){
//        recipes = data.results
//        notifyDataSetChanged()
//    }
//}