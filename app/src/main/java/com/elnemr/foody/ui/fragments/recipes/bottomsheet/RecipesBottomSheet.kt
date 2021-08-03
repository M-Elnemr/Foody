package com.elnemr.foody.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import com.elnemr.foody.R
import com.elnemr.foody.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.elnemr.foody.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.elnemr.foody.util.Constants.Companion.PREFERENCES_DIET_TYPE
import com.elnemr.foody.util.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.elnemr.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.elnemr.foody.util.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.elnemr.foody.viewmodels.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.recipes_bottom_sheet.view.*
import java.util.*
import kotlin.math.log

class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    lateinit var mView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.recipes_bottom_sheet, container, false)

        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        updateChips()

        mView.mealType_chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            mealTypeChip = chip.text.toString().lowercase(Locale.ROOT)
            mealTypeChipId = checkedId
        }

        mView.dietType_chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            dietTypeChip = chip.text.toString().lowercase(Locale.ROOT)
            dietTypeChipId = checkedId
        }

        clickAction()
        return mView
    }

    private fun updateChips() {
        recipesViewModel.readStringData(PREFERENCES_MEAL_TYPE).observe(viewLifecycleOwner, {
            mealTypeChip = it
        })

        recipesViewModel.readStringData(PREFERENCES_DIET_TYPE).observe(viewLifecycleOwner, {
            dietTypeChip = it
        })

        recipesViewModel.readIntData(PREFERENCES_MEAL_TYPE_ID).observe(viewLifecycleOwner, {
            Log.d("TAG", "updateChips: $it")
            mealTypeChipId = it
            if (it != 0)
                try {
                    mView.mealType_chipGroup.findViewById<Chip>(it).isChecked = true
                } catch (e: Exception) {
                    Log.d("TAG", "updateChips: $e")
                }
        })

        recipesViewModel.readIntData(PREFERENCES_DIET_TYPE_ID).observe(viewLifecycleOwner, {
            dietTypeChipId = it
            if (it != 0)
                try {
                    mView.dietType_chipGroup.findViewById<Chip>(it).isChecked = true
                } catch (e: Exception) {
                    Log.d("TAG", "updateChips: $e")
                }
        })

    }

    private fun clickAction() {
        mView.apply_btn.setOnClickListener {
            Log.d("TAG", "clickAction: $mealTypeChipId" )
            recipesViewModel.saveData(mealTypeChip, mealTypeChipId, dietTypeChip, dietTypeChipId)

            val action = RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)

            findNavController().navigate(action)
        }
    }

}