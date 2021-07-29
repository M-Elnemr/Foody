package com.elnemr.foody.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elnemr.foody.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class RecipesBottomSheet : BottomSheetDialogFragment() {

    lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)

        return mView
    }

}