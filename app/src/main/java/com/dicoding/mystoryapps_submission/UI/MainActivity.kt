package com.dicoding.mystoryapps_submission.UI

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mystoryapps_submission.LoginResponse.MainLoginActivity
import com.dicoding.mystoryapps_submission.R
import com.dicoding.mystoryapps_submission.RevisiCode_Final.adapter.LoadingAdapter
import com.dicoding.mystoryapps_submission.RevisiCode_Final.adapter.StoryAdapter
import com.dicoding.mystoryapps_submission.RevisiCode_Final.model.StoryViewModels
import com.dicoding.mystoryapps_submission.RevisiCode_Final.pref.UserPreference
import com.dicoding.mystoryapps_submission.RevisiCode_Final.viewModels.MainViewModel
import com.dicoding.mystoryapps_submission.RevisiCode_Final.viewModels.ViewModels
import com.dicoding.mystoryapps_submission.databinding.ActivityMainBinding
import com.dicoding.mystoryapps_submission.welcome.MainActivityWelcome


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")
class MainActivity : AppCompatActivity() {
//    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModels: MainViewModel
    private val storyViewModels: StoryViewModels by viewModels {
        StoryViewModels.ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setupViewModel()
        setupActionButton()
        val layoutManager = LinearLayoutManager(this)
        binding.rvStoryuser.layoutManager = layoutManager

        setupView()
        getStory()
    }

    private fun getStory() {
        val adapter = StoryAdapter()
        binding.rvStoryuser.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvStoryuser.setHasFixedSize(true)
        binding.rvStoryuser.adapter = adapter.withLoadStateFooter(
            footer = LoadingAdapter {
                adapter.retry()
            }
        )
        mainViewModels.getUser().observe(this) { userAuth ->
            if (userAuth != null) {
                storyViewModels.stories("Bearer " + userAuth.token).observe(this) { stories ->
                    adapter.submitData(lifecycle, stories)
                }
            }
        }

    }

    private fun setupView() {
        mainViewModels = ViewModelProvider(
            this,
            ViewModels(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]
        mainViewModels.getUser().observe(this) { user ->
            if (user.isLogin) {
            } else {
                startActivity(Intent(this, MainActivityWelcome::class.java))
                finish()
            }
        }
    }

    private fun setupActionButton() {
        binding.btnAddStory.setOnClickListener {
            val intent = Intent(this, PostingStory::class.java)
            startActivity(intent)
        }
    }

    //option menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Logout -> {
                mainViewModels.logout()
            }
            R.id.menu_map -> {
                val intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}