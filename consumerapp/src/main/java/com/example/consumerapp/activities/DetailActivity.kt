package com.example.consumerapp.activities

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.consumerapp.R
import com.example.consumerapp.adapter.SectionsPagerAdapter
import com.example.consumerapp.data.UserData
import com.example.consumerapp.databinding.ActivityDetailBinding
import com.example.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.example.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.example.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.example.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.NAME
import com.example.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.USERNAME

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityDetailBinding
    private var statusFavorite = false

    companion object{
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_POSITION = "extra_position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setActionBarTitle()


//        val userFav = intent.getParcelableExtra<UserData>(EXTRA_DATA) as UserData
//        val cursor: Cursor = favHelper.queryById(userFav.username.toString())

//        if (cursor.moveToNext()) {
//            statusFavorite = true
//            setStatusFavorite(statusFavorite)
//        }

        mBinding.fabFavorite.setOnClickListener(this)

        setData()
        setViewPager()
    }

    private fun setStatusFavorite(status: Boolean) {
        if (status){
            mBinding.fabFavorite.setImageResource(R.drawable.ic_favorite_24dp)
        } else {
            mBinding.fabFavorite.setImageResource(R.drawable.ic_favorite_border_24)
        }
    }

    private fun setViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        mBinding.viewPager.adapter = sectionsPagerAdapter
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager)
        supportActionBar?.elevation = 0f
    }

    private fun setActionBarTitle() {
        if (supportActionBar != null) {
            supportActionBar?.title  = "Profile"
        }
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setData() {
        val mUserData = intent.getParcelableExtra<UserData>(EXTRA_DATA) as UserData
        Glide.with(this)
            .load(mUserData.avatar)
            .apply(RequestOptions().override(150,150))
            .into(mBinding.imgDetailUser)
        mBinding.tvDetailName.text = mUserData.name
        mBinding.tvDetailUsername.text = mUserData.username
        mBinding.tvDetailCompany.text = mUserData.company
        mBinding.tvDetailLocation.text = mUserData.location
        mBinding.tvDetailRepos.text = mUserData.repository.toString()

    }

    override fun onClick(view: View) {
        val favData = intent.getParcelableExtra<UserData>(EXTRA_DATA) as UserData
        when (view.id) {
            R.id.fab_favorite -> {
                if (!statusFavorite) {
                    statusFavorite = !statusFavorite

                    val values = ContentValues().apply {
                        put(USERNAME, favData.username)
                        put(NAME, favData.name)
                        put(AVATAR, favData.avatar)
                        put(COMPANY, favData.company)
                        put(LOCATION, favData.location)
                    }

                    contentResolver.insert(CONTENT_URI, values)
                    Toast.makeText(this, getString(R.string.add_favorite), Toast.LENGTH_SHORT).show()
                    setStatusFavorite(statusFavorite)

                } else {
                    statusFavorite = false
                    Toast.makeText(this, getString(R.string.delete_favorite), Toast.LENGTH_SHORT).show()
                    setStatusFavorite(statusFavorite)
                }
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        favHelper.close()
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.language -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
            R.id.setting -> startActivity(Intent(this, SettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


}