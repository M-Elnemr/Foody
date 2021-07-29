package com.elnemr.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.elnemr.foody.R
import com.elnemr.foody.adapters.RecipesAdapter
import com.elnemr.foody.adapters.base.BaseAdapter
import com.elnemr.foody.databinding.FragmentRecipesBinding
import com.elnemr.foody.models.Result
import com.elnemr.foody.util.NetworkResult
import com.elnemr.foody.util.observeOnce
import com.elnemr.foody.viewmodels.MainViewModel
import com.elnemr.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _bindings : FragmentRecipesBinding? = null
    private val binding get() = _bindings!!

    lateinit var mAdapter: BaseAdapter<Result>
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

        _bindings = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setRecyclerView()
//        requestData()
        reaDatabase()
        return binding.root
    }

    private fun setRecyclerView() {
        mAdapter = RecipesAdapter()
        binding.shimmerRecyclerView.adapter = mAdapter
        binding.shimmerRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmer()
    }

    private fun reaDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes().observeOnce(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    Log.d("TAG", "requestData: database")
                    mAdapter.setDataList(it[0].foodRecipe.results)
                    hideShimmer()
                } else {
                    requestData()
                }
            })
        }
    }

    private fun requestData() {
        Log.d("TAG", "requestData: server")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Error -> {
                    hideShimmer()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                    if (binding.shimmerRecyclerView.isEmpty()) {
                        loadDataFromCache()
                    }
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

    private fun loadDataFromCache() {
        showShimmer()
        lifecycleScope.launch {
            mainViewModel.readRecipes().observe(viewLifecycleOwner, {
                hideShimmer()
                if (it.isNotEmpty()) {
                    mAdapter.setDataList(it[0].foodRecipe.results)
                }
            })
        }
    }

    fun showShimmer() {
        binding.shimmerRecyclerView.showShimmer()
    }

    fun hideShimmer() {
        binding.shimmerRecyclerView.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindings = null
    }
}