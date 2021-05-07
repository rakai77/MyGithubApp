package com.example.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.data.UserData
import com.example.consumerapp.databinding.ItemRowUserBinding

class FollowersAdapter(private val listFollowers: ArrayList<UserData>)
    : RecyclerView.Adapter<FollowersAdapter.ListViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowers[position])
    }

    override fun getItemCount(): Int = listFollowers.size

    inner class ListViewHolder (private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(dataFollowers: UserData) {
            with(binding){
                Glide.with(itemView.context)
                    .load(dataFollowers.avatar)
                    .apply(RequestOptions().override(100,100))
                    .into(imgUser)

                tvUsername.text = dataFollowers.username
                tvNameUser.text = dataFollowers.name
                tvCompany.text = dataFollowers.company
                tvLocation.text = dataFollowers.location

            }
        }
    }

}