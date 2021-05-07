package com.example.mygithub.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mygithub.activities.DetailActivity
import com.example.mygithub.data.UserData
import com.example.mygithub.databinding.ItemRowUserBinding

import java.util.ArrayList


class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    var listFavorite = ArrayList<UserData>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()

        }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FavoriteViewHolder {
        val binding = ItemRowUserBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])

        val data = listFavorite[position]
        holder.itemView.setOnClickListener {
            val userData = UserData(
                data.username,
                data.name,
                data.avatar,
                data.company,
                data.location,
                data.url,
                data.repository,
                data.followers,
                data.following
            )
            val detailIntent = Intent(it.context, DetailActivity::class.java)
            detailIntent.putExtra(DetailActivity.EXTRA_DATA, userData)
            it.context.startActivity(detailIntent)
        }
    }

    override fun getItemCount(): Int = this.listFavorite.size

    inner class FavoriteViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favData: UserData) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(favData.avatar)
                    .apply(RequestOptions().override(100,100))
                    .into(imgUser)
                tvUsername.text = favData.username
                tvNameUser.text = favData.name
                tvCompany.text = favData.company
                tvLocation.text = favData.location

//                itemView.setOnClickListener(
//                    CustomOnItemClickListener(
//                        adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback{
//                            override fun onItemClicked(view: View, position: Int) {
//                                val mIntent = Intent(activity, DetailActivity::class.java)
//                                mIntent.putExtra(DetailActivity.EXTRA_POSITION, position)
//                                mIntent.putExtra(DetailActivity.EXTRA_DATA, favData)
//                                activity.startActivity(mIntent)
//                            }
//                        }
//                    )
//                )
            }
        }

    }

}