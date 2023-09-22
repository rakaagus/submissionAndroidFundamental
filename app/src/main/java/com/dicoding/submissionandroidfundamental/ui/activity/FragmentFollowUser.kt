package com.dicoding.submissionandroidfundamental.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submissionandroidfundamental.adapter.FollowUserAdapter
import com.dicoding.submissionandroidfundamental.data.remote.response.GithubUsers
import com.dicoding.submissionandroidfundamental.databinding.FragmentFollowUserBinding
import com.dicoding.submissionandroidfundamental.ui.viewModels.FragmentFollowUserViewModel

class FragmentFollowUser : Fragment() {

    private lateinit var binding:FragmentFollowUserBinding

    private val fragmentFollowUserViewModel by viewModels<FragmentFollowUserViewModel>()


    companion object{
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
        const val TAG = "FragmentFollowUser"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUsers.layoutManager = layoutManager


        fragmentFollowUserViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        val index = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME)

        if(index == 1){
            fragmentFollowUserViewModel.getFollowersUser(username.toString())
            fragmentFollowUserViewModel.listFollowerUser.observe(viewLifecycleOwner){
                setUpUserFollower(it)
            }
        }else{
            fragmentFollowUserViewModel.getFollowingUser(username.toString())
            fragmentFollowUserViewModel.listFollowingUser.observe(viewLifecycleOwner){
                setUpUserFollowing(it)
            }
        }

    }

    private fun setUpUserFollowing(listUser: List<GithubUsers>?) {
        val adapter = FollowUserAdapter()
        adapter.submitList(listUser)
        binding.rvUsers.adapter = adapter
    }

    private fun setUpUserFollower(listUser: List<GithubUsers>) {
        val adapter = FollowUserAdapter()
        adapter.submitList(listUser)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}