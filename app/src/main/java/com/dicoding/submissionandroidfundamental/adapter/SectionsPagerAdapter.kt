package com.dicoding.submissionandroidfundamental.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.submissionandroidfundamental.ui.activity.FragmentFollowUser

class SectionsPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    var username: String? = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FragmentFollowUser()
        fragment.arguments = Bundle().apply {
            putInt(FragmentFollowUser.ARG_POSITION, position + 1)
            putString(FragmentFollowUser.ARG_USERNAME, username)
        }
        return fragment
    }
}