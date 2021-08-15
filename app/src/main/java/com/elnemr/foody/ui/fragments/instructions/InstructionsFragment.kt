package com.elnemr.foody.ui.fragments.instructions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.elnemr.foody.R
import com.elnemr.foody.models.Result
import com.elnemr.foody.util.Constants
import kotlinx.android.synthetic.main.fragment_instructions.view.*

class InstructionsFragment : Fragment() {
    lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val args = arguments
        val result: Result? = args?.getParcelable(Constants.RECIPE_RESULT_KEY)

        if (result != null)
        setWebView(result)

        return mView
    }

    private fun setWebView(result: Result) {
        mView.webView.webViewClient = WebViewClient()
        mView.webView.loadUrl(result.sourceUrl)
    }

}