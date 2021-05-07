package com.example.mygithub.helper

import android.database.Cursor
import com.example.mygithub.data.UserData
import com.example.mygithub.db.DatabaseContract
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