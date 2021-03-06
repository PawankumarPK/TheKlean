package com.example.hp.thekleaners.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.pojoClass.ForService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.dialog_thanku_car.*
import kotlinx.android.synthetic.main.fragment_date_time_carservice.*

class DateAndTimeCarService : BaseNavigationFragment() {

    private val displayRectangle = Rect()
    private var width = 0
    private lateinit var dialog: Dialog
    private lateinit var metrics: DisplayMetrics

    private var user_id: String? = null
    private val db = FirebaseFirestore.getInstance()
    private val notebookRef = db.collection("Users")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_date_time_carservice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as NavigationDrawer
        mainActivity.toolbar.visibility = View.VISIBLE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)
        metrics = DisplayMetrics()
        mainActivity.window.decorView.getWindowVisibleDisplayFrame(displayRectangle)
        width = (displayRectangle.width() * 0.9f).toInt()
        dialog = Dialog(mainActivity)

        mSavedNewCarService.setOnClickListener { addNote() }
        mDateCarBackArrow.setOnClickListener { mDateCarBackArrowFunction() }

        val name = this.arguments!!.getString("doctor_id").toString()
        mDailyCarServiceAmount.text = name

        user_id = FirebaseAuth.getInstance().uid

    }


    private fun addNote() {

        mDailyCarServiceTiming.text = getCurrentDate()
        val serviceTaken = mDailyCarServiceTaken!!.text.toString()
        val amount = mDailyCarServiceAmount!!.text.toString()
        val timing = mDailyCarServiceTiming!!.text.toString()
        val note = ForService(serviceTaken, amount, timing)

        notebookRef.document(user_id!!).collection("Services").document("For Car Service").collection("Car Washing").add(note)
        thankuDialog()
    }

    private fun mDialogContinueFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SavedServices()).commit()
        dialog.dismiss()
    }

    @SuppressLint("InflateParams")
    private fun thankuDialog() {
        val layout = LayoutInflater.from(mainActivity).inflate(R.layout.dialog_thanku_car, null)
        layout.minimumWidth = width
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(0, 20, 0, 0)
        dialog.setContentView(layout)
        dialog.mContinueDialogCar.setOnClickListener { mDialogContinueFunction() }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }

    private fun getCurrentDate(): String {
        val builder = StringBuilder()
        builder.append((date_picker_for_car.dayOfMonth).toString() + "-")
        builder.append((date_picker_for_car.month + 1).toString() + "-")
        builder.append(date_picker_for_car.year)
        return builder.toString()
    }

    private fun mDateCarBackArrowFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, CarPricingDetails()).commit()
        dialog.dismiss()
    }
}