package com.dicoding.github

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.github.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val LOGIN = "LOGIN"
        private  val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val login = intent.getStringExtra(LOGIN)

        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        detailViewModel.findDetailUser(login!!)
        detailViewModel.detailUserGithub.observe(this){ item ->
            setDetailUser(item)
        }
        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }
        val sectionsPagerAdapter = SectionPagerAdapter(this)
        sectionsPagerAdapter.username = login
        val viewPager: ViewPager2 = findViewById(R.id.view_pager_follow)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    @Suppress("DEPRECATION")
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    @SuppressLint("SetTextI18n")
    private fun setDetailUser(item: DetailUserResponse) {
        supportActionBar?.title = "Detail User ${item.login} "
        Glide.with(this)
            .load(item.avatarUrl)
            .into(binding.imgItemPhotoUserDetail)
        binding.tvItemName.text = item.name
        binding.tvItemUsername.text = item.login
        binding.tvItemFollowersDetail.text = "${item.followers} Followers"
        binding.tvItemFollowingDetail.text = "${item.following} Following"

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}