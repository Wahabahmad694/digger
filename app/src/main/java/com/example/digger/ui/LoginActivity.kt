package com.example.digger.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.dig.R
import com.example.dig.databinding.ActivityLoginBinding
import com.example.digger.state.Resource
import com.example.digger.utils.UiExtension.acceptNamesOnly
import com.example.digger.viewModel.AuthViewModels
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    val loginSignUpViewModel: AuthViewModels by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewInit()
    }

    private fun viewInit() {
        setUpClickListener()

    }

    private fun setUpClickListener() {
        binding.apply {
            tvRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
            tvForgetPassword.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ForgetPasswordActivity::class.java))
            }
            btnLogin.setOnClickListener {
                checkValidations()
            }

        }
    }

    private fun checkValidations() {

        val userName = binding.etEmail.text.toString().trim()
        val phoneNumber = binding.etPassword.text.toString().trim()
        val validationResult = loginSignUpViewModel.validateLoginCredentials(userName, phoneNumber)
        if (validationResult) {
            loginSignUpViewModel.userEmail = userName
            loginSignUpViewModel.password = phoneNumber
            bindSignUpObserver()
        } else {
            Toast.makeText(this, "Invalid Credential", Toast.LENGTH_SHORT).show()

        }
    }

    private fun bindSignUpObserver() {
        loginSignUpViewModel.login.observe(this) {
            binding.loading.isVisible = it is Resource.Loading
            if (it is Resource.Success) {
                gotoMainScreen()
            } else if (it is Resource.Error) {
                loginSignUpViewModel.login.removeObservers(this)
            }
        }
    }

    private fun gotoMainScreen() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))

    }


}