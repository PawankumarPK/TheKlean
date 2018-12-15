package com.example.hp.thekleaners.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thekleaners.BaseClasses.HomeBaseFragment
import com.example.hp.thekleaners.R
import kotlinx.android.synthetic.main.fragment_sign_up_password.*

class SignUpPassword : HomeBaseFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_up_password,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mDone.setOnClickListener { mDoneFunction() }
       // mPasswordBackArrow.setOnClickListener { mPasswordBackArrowFunction() }
    }

    private fun mDoneFunction() {
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.mSignUpFrameContainer, Profile()).commit()
    }
    /*private fun mPasswordBackArrowFunction(){
        fragmentManager!!.beginTransaction().addToBackStack(null).replace(R.id.containerView, SignUpKleaners()).commit()
    }*/

}