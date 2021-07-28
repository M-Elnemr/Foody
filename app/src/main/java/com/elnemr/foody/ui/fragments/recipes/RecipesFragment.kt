package com.elnemr.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.elnemr.foody.R
import com.elnemr.foody.adapters.RecipesAdapter
import com.elnemr.foody.adapters.base.BaseAdapter
import com.elnemr.foody.models.Result
import com.elnemr.foody.util.NetworkResult
import com.elnemr.foody.viewmodels.MainViewModel
import com.elnemr.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    lateinit var mAdapter: BaseAdapter<Result>
    lateinit var mView: View
    lateinit var mainViewModel: MainViewModel
    lateinit var recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_recipes, container, false)

        setRecyclerView()
        requestData()
        return mView
    }

    private fun requestData() {
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            Log.d("TAG", "requestData: "+ response)
            when (response) {
                is NetworkResult.Error -> {
                    hideShimmer()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is NetworkResult.Loading -> showShimmer()
                is NetworkResult.Success -> {
                    hideShimmer()
                    response.data?.let {
                        mAdapter.addDataList(it.results)
                    }
                }
            }
        })
    }

    fun setRecyclerView() {
        mAdapter = RecipesAdapter()
        mView.shimmerRecyclerView.adapter = mAdapter
        mView.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    fun showShimmer() {
        mView.shimmerRecyclerView.showShimmer()
    }

    fun hideShimmer() {
        mView.shimmerRecyclerView.hideShimmer()
    }

}