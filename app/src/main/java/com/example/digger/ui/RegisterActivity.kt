package com.example.digger.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.dig.R
import com.example.dig.databinding.ActivityRegisterBinding
import com.example.digger.state.Resource
import com.example.digger.utils.UiExtension.acceptNamesOnly
import com.example.digger.viewModel.AuthViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    val loginSignUpViewModel: AuthViewModels by viewModels()

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        viewInit()
    }

    private fun viewInit() {
        setUpClickListener()
        binding.etFirstName.acceptNamesOnly()
        binding.etSecondName.acceptNamesOnly()
        binding.etCode.acceptNamesOnly()

    }

    private fun setUpClickListener() {
        binding.apply {
            tvLogin.setOnClickListener {
                finish()
            }
            btnSignUp.setOnClickListener {
                checkValidations()
            }

        }
    }

    private fun checkValidations() {

        val userName = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val firstName = binding.etFirstName.text.toString().trim()
        val lastName = binding.etSecondName.text.toString().trim()
        val code = binding.etCode.text.toString().trim()
        val validationResult = loginSignUpViewModel.validateSignupCredentials(
            userName,
            password,
            firstName,
            lastName,
            code
        )
        if (validationResult) {
            loginSignUpViewModel.userEmail = userName
            loginSignUpViewModel.password = password
            loginSignUpViewModel.first_name = firstName
            loginSignUpViewModel.last_name = lastName
            loginSignUpViewModel.code = code
            bindSignUpObserver()
        } else {
            Toast.makeText(this, "Invalid Credential", Toast.LENGTH_SHORT).show()

        }
    }

    private fun bindSignUpObserver() {
        loginSignUpViewModel.login.observe(this) {
            binding.loading.isVisible = it is Resource.Loading
            if (it is Resource.Success) {
                gotoLogin()
            } else if (it is Resource.Error) {
                loginSignUpViewModel.login.removeObservers(this)
            }
        }
    }

    private fun gotoLogin() {
        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

    }
}