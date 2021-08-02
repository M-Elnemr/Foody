package com.elnemr.foody.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.elnemr.foody.R
import com.elnemr.foody.adapters.PagerAdapter
import com.elnemr.foody.ui.fragments.ingredients.IngredientsFragment
import com.elnemr.foody.ui.fragments.instructions.InstructionsFragment
import com.elnemr.foody.ui.fragments.overview.OverViewFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private val args by navArgs<DetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolBar)
        toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpTabLayout()
    }

    private fun setUpTabLayout() {
        val fragments = ArrayList<Fragment>()
        fragments.add(OverViewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("OverView")
        titles.add("Ingredients")
        titles.add("Instructions")

        val bundle = Bundle()
        bundle.putParcelable("result", args.result)

        val adapter = PagerAdapter(bundle, fragments, lifecycle, supportFragmentManager)
        viewPager.adapter = adapter
        TabLayoutMediator(
            tabLayout,
            viewPager
        ) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}