package com.elnemr.foody.ui.fragments.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import coil.load
import com.elnemr.foody.R
import com.elnemr.foody.models.Result
import kotlinx.android.synthetic.main.fragment_over_view.view.*
import org.jsoup.Jsoup

class OverViewFragment : Fragment() {

    lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_over_view, container, false)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        val args = arguments
        val result : Result? = args?.getParcelable("result")

        if (result != null)
            loadDataIntoView(result)

        return mView
    }

    private fun loadDataIntoView(result: Result) {
        mView.main_imageView.load(result.image)
        mView.likesTextView.text = result.aggregateLikes.toString()
        mView.clockTextView.text = result.readyInMinutes.toString()
        mView.summary_tv.text = Jsoup.parse(result.summary).text()

        if (result.vegetarian) {
            mView.vegetarian_iv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            mView.vegetarian_tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (result.vegan) {
            mView.vegan_iv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            mView.vegan_tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (result.glutenFree) {
            mView.glutenFree_iv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            mView.glutenFree_tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (result.dairyFree) { mView.diaryFree_iv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            mView.diaryFree_tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (result.veryHealthy) {
            mView.healthy_iv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            mView.healthy_tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }
        if (result.cheap) {
            mView.cheap_iv.setColorFilter(ContextCompat.getColor(requireContext(), R.color.green))
            mView.cheap_tv.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

    }

}