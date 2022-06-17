package com.dicoding.mystoryapps_submission.RegisterResponse

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.dicoding.mystoryapps_submission.LoginResponse.MainLoginActivity
import com.dicoding.mystoryapps_submission.R
import com.dicoding.mystoryapps_submission.RevisiCode.data.config.interfaceConfig.ApiConfig
import com.dicoding.mystoryapps_submission.RevisiCode_Final.response.ApiResponse
import com.dicoding.mystoryapps_submission.databinding.ActivityMainRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainRegisterBinding
//    private val registerViewModels: RegisterViewModels by viewModels {
//        RegisterViewModels.RegisterViewModelFactory.getInstance()
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //deklarasi
    binding.myFormNamaRegister.type = "name"
    binding.myFormEmailRegister.type = "email"
    binding.myFormPasswordRegister.type = "password"

    //on click
    binding.btnRegister.setOnClickListener {
        val inputName = binding.myFormNamaRegister.text.toString()
        val inputEmail = binding.myFormEmailRegister.text.toString()
        val inputPassword = binding.myFormPasswordRegister.text.toString()
        createAkun(inputName, inputEmail, inputPassword)
    }
//    setupView()
//        setupViewModel()
//        setupAction()
    }

    private fun createAkun(inputName: String, inputEmail: String, inputPassword: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().createAccount(inputName, inputEmail, inputPassword)
        client.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.message == "User created") {
                    Toast.makeText(this@MainRegisterActivity,
                        getString(R.string.register_successful),
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@MainRegisterActivity, MainLoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainRegisterActivity,
                        getString(R.string.not),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@MainRegisterActivity,
                    getString(R.string.not),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}