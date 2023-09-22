package com.dicoding.submissionandroidfundamental.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionandroidfundamental.data.remote.response.GithubUsers
import com.dicoding.submissionandroidfundamental.databinding.ItemUserGithubBinding

class ListUserAdapter: ListAdapter<GithubUsers, ListUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class MyViewHolder(val binding: ItemUserGithubBinding): RecyclerView.ViewHolder(binding.root) {

    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUsers>(){
            override fun areItemsTheSame(oldItem: GithubUsers, newItem: GithubUsers): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GithubUsers, newItem: GithubUsers): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val githubUsers = getItem(position)
        Glide.with(holder.binding.root)
                .load(githubUsers.avatarUrl)
                .centerCrop()
                .into(holder.binding.imgAvatar)
        holder.binding.tvUsername.text = githubUsers.login
        holder.binding.itemRoot.setOnClickListener {
            onItemClickCallback.onItemClicked(githubUsers)
        }
    }

    interface OnClickCallback{
        fun onItemClicked(data: GithubUsers)
    }
}