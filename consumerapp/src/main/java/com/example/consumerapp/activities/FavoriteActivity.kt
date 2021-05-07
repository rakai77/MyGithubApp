package com.example.consumerapp.activities

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.R
import com.example.consumerapp.adapter.FavoriteAdapter
import com.example.consumerapp.data.UserData
import com.example.consumerapp.databinding.ActivityFavoriteBinding
import com.example.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.consumerapp.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: ActivityFavoriteBinding

    companion object{
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavoriteAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)
        if (savedInstanceState == null) {
            loadFavoriteAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserData>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavorite = list
            }
        }

        showRecyclerFavorite()
        setActionBarTittle()
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteAsync()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorite)
    }

    private fun showRecyclerFavorite() {
        binding.rvFavUser.layoutManager = LinearLayoutManager(this)
        binding.rvFavUser.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        binding.rvFavUser.adapter = adapter
    }

    private fun setActionBarTittle() {
        if (supportActionBar != null) {
            supportActionBar?.title  = "MyFavorite"
        }
    }

    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBarFav.visibility = View.VISIBLE
            val deferredFav = async(Dispatchers.IO){
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val  favData = deferredFav.await()
            binding.progressBarFav.visibility = View.INVISIBLE
            if (favData.size > 0){
                adapter.listFavorite = favData
            } else {
                adapter.listFavorite.clear()
                adapter.listFavorite = ArrayList()
                showSnackBarMessage()
            }
        }
    }

    private fun showSnackBarMessage() {
        Toast.makeText(this, getString(R.string.empty_favorite), Toast.LENGTH_SHORT).show()
    }
}