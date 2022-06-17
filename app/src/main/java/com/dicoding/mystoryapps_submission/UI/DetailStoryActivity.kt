package com.dicoding.mystoryapps_submission.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.mystoryapps_submission.R
import com.dicoding.mystoryapps_submission.RevisiCode_Final.model.StoryModel
import com.dicoding.mystoryapps_submission.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    companion object {
        const val DETAIL_STORY = "detail_story"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val story = intent.getParcelableExtra<StoryModel>(DETAIL_STORY) as StoryModel
        binding.tvDesc.text = story.description
        supportActionBar?.title = story.name
        Glide.with(this)
            .load(story.photo)
            .error(R.drawable.ic_broken)
            .into(binding.ivDetail)
    }
}
