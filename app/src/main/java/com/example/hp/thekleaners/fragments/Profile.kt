package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.hp.thekleaners.BaseClasses.ForHomeServiceBaseFragment
import com.example.hp.thekleaners.BaseClasses.HomeBaseFragment
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.dialog_logout.*
import kotlinx.android.synthetic.main.fragment_profile.*

class Profile : ForHomeServiceBaseFragment() {


    private val displayRectangle = Rect()
    private var width = 0
    private lateinit var dialog: Dialog
    private lateinit var metrics: DisplayMetrics


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //var date = findViewById<View>(R.id.mUserNumber) as EditText

        metrics = DisplayMetrics()
        homeServiceActivity.window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        width = (displayRectangle.width() * 0.9f).toInt()
        homeServiceActivity.windowManager.defaultDisplay.getMetrics(metrics)
        dialog = Dialog(homeServiceActivity)
        mRelativeLayoutMyAddress.setOnClickListener { mRelativeLayoutMyAddressFunction() }
        mRelativeLayoutMyService.setOnClickListener { mRelativeLayoutMyServiceFunction() }
        mEditProfile.setOnClickListener { mEditProfileFunction() }
        mLogout.setOnClickListener { logoutDialog() }
        mProfileBackArrow.setOnClickListener { mProfileBackArrowFunction() }
    }

    private fun mRelativeLayoutMyAddressFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedAddress()).commit()
    }


    private fun mProfileBackArrowFunction() {
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)
    }

    private fun mRelativeLayoutMyServiceFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedServices()).commit()
    }

    private fun mEditProfileFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, UserEditProfile()).commit()
    }

    private fun mDialogLogoutFunction() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)
        dialog.dismiss()
    }

    @SuppressLint("InflateParams")
    private fun logoutDialog() {
        val layout = LayoutInflater.from(homeServiceActivity).inflate(R.layout.dialog_logout, null, false)
        layout.minimumWidth = width
        dialog.setContentView(layout)
        dialog.buttonLogout.setOnClickListener { mDialogLogoutFunction() }
        dialog.mCancelDialog.setOnClickListener { dialog.dismiss() }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

}
