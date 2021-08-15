package com.elnemr.foody.ui.fragments.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.elnemr.foody.R
import com.elnemr.foody.adapters.IngredientAdapter
import com.elnemr.foody.models.Result
import com.elnemr.foody.util.Constants.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_ingredients.view.*

class IngredientsFragment : Fragment() {
    lateinit var mView: View

    private val mAdapter: IngredientAdapter by lazy { IngredientAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_ingredients, container, false)
        setUpRecycler()

        val args = arguments
        val result: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        result?.extendedIngredients?.let {
            mAdapter.setDataList(it)
        }

        return mView
    }

    private fun setUpRecycler() {
        mView.recycler.layoutManager = LinearLayoutManager(requireContext())
        mView.recycler.adapter = mAdapter
    }

}