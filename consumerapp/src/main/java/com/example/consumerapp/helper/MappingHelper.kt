package com.example.consumerapp.helper

import android.database.Cursor
import com.example.consumerapp.data.UserData
import com.example.consumerapp.db.DatabaseContract
import java.util.ArrayList

object MappingHelper {

    fun mapCursorToArrayList (notesCursor: Cursor?) : ArrayList<UserData> {
        val favoriteList = ArrayList<UserData>()

        notesCursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))

                favoriteList.add(
                    UserData(
                        username,
                        name,
                        avatar,
                        company,
                        location,
                    )
                )
            }
        }
        return favoriteList
    }
}