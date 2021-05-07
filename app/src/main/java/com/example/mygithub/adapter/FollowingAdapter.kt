package com.example.mygithub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mygithub.data.UserData
import com.example.mygithub.databinding.ItemRowUserBinding

class FollowingAdapter(private val listFollowing: ArrayList<UserData>) :
    RecyclerView.Adapter<FollowingAdapter.ListViewHolder>(){


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder:ListViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount(): Int = listFollowing.size

    class ListViewHolder (private val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(dataFollowing: UserData) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataFollowing.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(imgUser)

                tvUsername.text = dataFollowing.username
                tvNameUser.text = dataFollowing.name
                tvCompany.text = dataFollowing.company
                tvLocation.text = dataFollowing.location
            }
        }
    }
}