package com.example.hp.thekleaners.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_number_verify.*

class NumberVerify : BaseNavigationFragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_number_verify,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.title_name.text = resources.getString(R.string.signIn)
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)

        mMobileVerBackButton.setOnClickListener { mMobileVerBackButtonFunction()  }
    }


    private fun mMobileVerBackButtonFunction() {
        val intent = Intent(context,NavigationDrawer::class.java)
        startActivity(intent)

    }
}
