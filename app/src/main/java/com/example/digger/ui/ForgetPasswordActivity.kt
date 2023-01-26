package com.example.digger.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.dig.R
import com.example.dig.databinding.ActivityForgetPasswordBinding
import com.example.digger.state.Resource
import com.example.digger.viewModel.AuthViewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordActivity : AppCompatActivity() {


    val loginSignUpViewModel: AuthViewModels by viewModels()

    private lateinit var binding: ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forget_password)
        viewInit()
    }

    private fun viewInit() {
        setUpClickListener()
    }

    private fun setUpClickListener() {
        binding.apply {
            btnForget.setOnClickListener {
                verifyUser()
            }
        }
    }


    private fun bindForgetObserver(email: String) {
        loginSignUpViewModel.forgetPassword(email).observe(this) {
            binding.loading.isVisible = it is Resource.Loading
            if (it is Resource.Success) {
                gotoMainScreen()
            } else if (it is Resource.Error) {
                Toast.makeText(this, "Something Went Wrong...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun gotoMainScreen() {
        startActivity(Intent(this@ForgetPasswordActivity, LoginActivity::class.java))
    }


    private fun verifyUser() {
        val email = binding.etEmail.text.trim().toString()
        loginSignUpViewModel.forgetPassword(email)
        bindForgetObserver(email)
    }
}