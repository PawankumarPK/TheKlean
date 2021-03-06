package com.example.hp.thekleaners.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.databinding.DataBindingUtil

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.design.widget.TextInputEditText
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import com.example.hp.thekleaners.R
import com.example.hp.thekleaners.activities.NavigationDrawer
import com.example.hp.thekleaners.baseClasses.BaseNavigationFragment
import com.example.hp.thekleaners.databinding.FragmentFeedbackBinding
import com.example.hp.thekleaners.dto.EmailSendDto
import kotlinx.android.synthetic.main.app_bar_navigation_drawer.*
import kotlinx.android.synthetic.main.fragment_feedback.*


class Feedback  : BaseNavigationFragment() {

    private val emailSendDto = EmailSendDto()

    // private val EMAIL_REGEX = "^[\\w-+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$"

    private lateinit var binding: FragmentFeedbackBinding



     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback, container, false)

        mainActivity = activity as NavigationDrawer
        // mainActivity.title_name.text = resources.getString(R.string.signIn)
        mainActivity.toolbar.visibility = View.GONE
        mainActivity.tabLayout.visibility = View.GONE
        (activity as NavigationDrawer).setDrawerLocked(true)


        binding.emailDto = emailSendDto

        mainActivity = activity as NavigationDrawer

        mainActivity.toolbar.visibility = View.GONE

        mainActivity.title_name.text = resources.getString(R.string.feedback)

        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initListener()

        mFeedbackBackArrow.setOnClickListener { signInBackPress() }



    }



    private fun initListener() {

        binding.submit.setOnClickListener { submitDetails() }

        binding.reset.setOnClickListener { resetDto() }

    }



    private fun submitDetails() {

        if (!checkNullFields())

            return

        /*if (!checkValidField())
            return
*/

        val emailIntent = Intent(Intent.ACTION_SEND)

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback For : ${resources.getString(R.string.app_name)}")

        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("info@thekleaners.com"))

        emailIntent.putExtra(Intent.EXTRA_TEXT, emailData())

        emailIntent.type = "text/plain"

        val matches = context!!.packageManager.queryIntentActivities(emailIntent, 0)

        var best: ResolveInfo? = null

        for (info in matches)

            if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))

                best = info

        if (best != null)

            emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name)



        startActivity(emailIntent)

    }



    override fun onCreate(@Nullable savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }



    override fun onPrepareOptionsMenu(menu: Menu) {

        menu.clear()

    }

    private fun emailData(): String {

        return "\nFull Name : ${emailSendDto.fullNameDto}\nMobile No. : ${emailSendDto.emailIDDto}\n" + "Feedback : ${emailSendDto.appTitleDto}"

    }



    /* private fun checkValidField(): Boolean {
         val isValid: Boolean
         val p = Pattern.compile(EMAIL_REGEX)
         val m = p.matcher(emailSendDto.emailIDDto)
         isValid = m.find()
         if (!isValid) {
             Toast.makeText(context, "Invalid Email", Toast.LENGTH_LONG).show()
             return isValid
         }
         return isValid
     }*/



    /*private fun toastLong(value: String) {
        toastLong(value)
    }*/



    private fun resetDto() {

        emailSendDto.fullNameDto = ""

        emailSendDto.emailIDDto = ""

        emailSendDto.websiteUrlDto = ""

        emailSendDto.appTitleDto = ""

        binding.fullName.setText("")

        binding.emailAddress.setText("")

        // binding.website.setText("")

        binding.mFeedback.setText("")

    }



    private fun checkNullFields(): Boolean {

        clearError()

        return when {

            emailSendDto.fullNameDto == "" -> showError(binding.fullName, "Name Empty")

            emailSendDto.emailIDDto == "" -> showError(binding.emailAddress, "Mobile No. Empty")

            // emailSendDto.websiteUrlDto == "" -> showError(binding.website, "Website Empty")

            emailSendDto.appTitleDto == "" -> showError(binding.mFeedback, "Feedback Empty")

            else -> true

        }

    }



    private fun showError(editText: TextInputEditText, error: String): Boolean {

        editText.requestFocus()

        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED)

        editText.error = error

        return false

    }



    private fun clearError() {

        binding.fullName.error = null

        binding.emailAddress.error = null

        binding.mFeedback.error = null

    }



    private fun signInBackPress() {
        val intent = Intent(context, NavigationDrawer::class.java)
        startActivity(intent)

    }

}