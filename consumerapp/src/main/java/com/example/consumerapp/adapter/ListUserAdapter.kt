package com.example.consumerapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.activities.DetailActivity
import com.example.consumerapp.data.UserData
import com.example.consumerapp.databinding.ItemRowUserBinding
import java.util.*
import kotlin.collections.ArrayList

class ListUserAdapter(private var listUser: ArrayList<UserData>)
    : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>(), Filterable {

    var userFilterList  = ArrayList<UserData>()

    init {
        userFilterList = listUser
    }

    private var onItemClickCallback: OnItemClickCallback? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup, false
        )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(userFilterList[position])
        val data = userFilterList[position]
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

    override fun getItemCount(): Int = userFilterList.size

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder (private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userData: UserData) {
            with(binding){
                Glide.with(itemView.context)
                    .load(userData.avatar)
                    .apply(RequestOptions().override(100,100))
                    .into(imgUser)

                tvUsername.text = userData.username
                tvNameUser.text = userData.name
                tvCompany.text = userData.company
                tvLocation.text = userData.location

            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(userData: UserData)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchUser = constraint.toString()
                userFilterList = if (searchUser.isEmpty()){
                    listUser
                } else {
                    val resultList = ArrayList<UserData>()
                    for (row in listUser) {
                        if (row.name!!.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResult = FilterResults()
                filterResult.values = userFilterList
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                userFilterList = results?.values as ArrayList<UserData>
                notifyDataSetChanged()
            }

        }
    }
}