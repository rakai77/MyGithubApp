package com.example.mygithub.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData(

        var username: String? = null,
        var name: String? = null,
        var avatar: String? = null,
        var company: String? = null,
        var location: String? = null,
        var url: String? = null,
        var repository: Int = 0,
        var followers: Int = 0,
        var following: Int = 0,
) :Parcelable
