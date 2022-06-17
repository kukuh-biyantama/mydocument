package com.dicoding.mystoryapps_submission.LoginResponse

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.mystoryapps_submission.R
import com.dicoding.mystoryapps_submission.RevisiCode.data.config.interfaceConfig.ApiConfig
import com.dicoding.mystoryapps_submission.RevisiCode_Final.model.Auth
import com.dicoding.mystoryapps_submission.RevisiCode_Final.pref.UserPreference
import com.dicoding.mystoryapps_submission.RevisiCode_Final.response.LoginResponse
import com.dicoding.mystoryapps_submission.RevisiCode_Final.viewModels.MainViewModel
import com.dicoding.mystoryapps_submission.RevisiCode_Final.viewModels.ViewModels
import com.dicoding.mystoryapps_submission.UI.MainActivity
import com.dicoding.mystoryapps_submission.databinding.ActivityMainLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
class MainLoginActivity : AppCompatActivity() {

//    private lateinit var LoginViewModel: loginViewModel
    private lateinit var binding: ActivityMainLoginBinding
//    private val loginViewModels: LoginViewModels by viewModels {
//        LoginViewModels.LoginViewModelFactory.getInstance(
//            LoginPref.getInstance(dataStore)
//        )
//    }
//    private lateinit var user: UserModel
    private lateinit var mainViewModels: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //deklarasi id form
        binding.myFormEmailLogin.type = "email"
        binding.myPasswordLogin.type = "password"

        //click button
        binding.myButtonLogin.setOnClickListener {
            val inputEmail = binding.myFormEmailLogin.text.toString()
            val inputPassword = binding.myPasswordLogin.text.toString()

            login(inputEmail, inputPassword)
        }

        setupView()
//        setupViewModel()
//        setupAction()
    }

    private fun setupView() {
        mainViewModels = ViewModelProvider(
            this,
            ViewModels(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        mainViewModels.getUser().observe(this) { user ->
            if (user.isLogin) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun login(inputEmail: String, inputPassword: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().login(inputEmail, inputPassword)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.message == "success") {
                    mainViewModels.saveUser(Auth(responseBody.loginResult.token, true))
                    Toast.makeText(this@MainLoginActivity,
                        getString(R.string.login_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@MainLoginActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainLoginActivity,
                        getString(R.string.not),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@MainLoginActivity,
                    getString(R.string.not),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

//    private fun setupView() {
//        supportActionBar?.title = getString(R.string.login)
//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        binding.myButtonLogin.setOnClickListener {
//            if (!binding.myFormEmailLogin.text.isNullOrEmpty() && !binding.myPasswordLogin.text.isNullOrEmpty()) {
//                val email = binding.myFormEmailLogin.text.toString()
//                val password = binding.myPasswordLogin.text.toString()
//                val result = loginViewModels.login(email, password)
//
//                result.observe(this){
//                    when (it) {
//                        is Result.Loading -> {
//                            binding.progressBar.visibility = View.VISIBLE
//                        }
//                        is Result.Error -> {
//                            binding.progressBar.visibility = View.INVISIBLE
//                            val error = it.error
//                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
//                        }
//                        is Result.Success -> {
//                            binding.progressBar.visibility = View.INVISIBLE
//                            val data = it.data
//                            loginViewModels.saveToken(data.loginResult.token)
//                            Log.d("MainLoginActivity", "Token: ${data.loginResult.token}")
//                            val intent = Intent(this, MainActivity::class.java)
//                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                            startActivity(intent)
//                            finish()
//                        }
//                    }
//                }
//            }else {
//                binding.myFormEmailLogin.error = resources.getString(R.string.not)
//
//                if (binding.myPasswordLogin.text.isNullOrEmpty()) {
//                    binding.myPasswordLogin.error =
//                        resources.getString(R.string.not)
//                }
//            }
//        }
//    }
}