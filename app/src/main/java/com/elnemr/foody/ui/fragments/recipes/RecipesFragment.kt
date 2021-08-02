package com.elnemr.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.elnemr.foody.R
import com.elnemr.foody.adapters.RecipesAdapter
import com.elnemr.foody.adapters.base.BaseAdapter
import com.elnemr.foody.databinding.FragmentRecipesBinding
import com.elnemr.foody.models.Result
import com.elnemr.foody.util.NetworkListener
import com.elnemr.foody.util.NetworkResult
import com.elnemr.foody.util.observeOnce
import com.elnemr.foody.viewmodels.MainViewModel
import com.elnemr.foody.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipesFragment : Fragment(), SearchView.OnQueryTextListener , SearchView.OnCloseListener {

    private val args by navArgs<RecipesFragmentArgs>()

    @Inject
    lateinit var networkListener: NetworkListener

    private var _bindings: FragmentRecipesBinding? = null
    private val binding get() = _bindings!!

    lateinit var mAdapter: BaseAdapter<Result>
    lateinit var mainViewModel: MainViewModel
    lateinit var recipesViewModel: RecipesViewModel
    lateinit var searchView: SearchView

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

        setHasOptionsMenu(true)

        setRecyclerView()
//        requestData()
        reaDatabase()
        onClickMethods()

        lifecycleScope.launch {
            networkListener.checkInternetAvailability().collect {
                recipesViewModel.networkStatus = it
            }
        }

        return binding.root
    }

    private fun onClickMethods() {
        binding.recipesFab.setOnClickListener {
            val navExtra = FragmentNavigatorExtras(
                binding.recipesFab to "fabImage"
            )
            findNavController().navigate(
                R.id.action_recipesFragment_to_recipesBottomSheet,
                null,
                null,
                navExtra
            )
        }
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
                if (it.isNotEmpty() && !args.backFromBottomSheet) {
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
            binding.shimmerRecyclerView.visibility = View.VISIBLE
            when (response) {
                is NetworkResult.Error -> {
                    Log.d("TAG", "error: ")
                    hideShimmer()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_LONG)
                        .show()
                    if (response.message.equals("No Recipes Found.")) {
                        binding.shimmerRecyclerView.visibility = View.INVISIBLE

                    } else if (binding.shimmerRecyclerView.isEmpty()) {
                        loadDataFromCache()
                    }
                }
                is NetworkResult.Loading -> showShimmer()
                is NetworkResult.Success -> {
                    Log.d("TAG", "sucess: ")

                    hideShimmer()
                    response.data?.let {
                        mAdapter.addDataList(it.results)
                    }
                }
            }
        })
    }

    private fun searchApiData(searchValue: String?) {
        if (searchValue.isNullOrBlank()) {
            requestData()
            return
        }
        mainViewModel.getSearchRecipes(recipesViewModel.applySearchQuery(searchValue))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner, {
            when (it) {
                is NetworkResult.Error -> {
                    hideShimmer()
//                        if (it.message.equals("No Recipes Found.")) {
//                            binding.shimmerRecyclerView.visibility = View.INVISIBLE
//
//                        } else
                    if (binding.shimmerRecyclerView.isEmpty()) {
                        loadDataFromCache()
                    }
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                is NetworkResult.Loading -> {
                    showShimmer()
                }
                is NetworkResult.Success -> {
                    hideShimmer()
                    val data = it.data
                    data?.let {
                        mAdapter.setDataList(it.results)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchApiData(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindings = null
    }

    override fun onClose(): Boolean {
        reaDatabase()
        searchView.onActionViewCollapsed()
        return true
    }


}