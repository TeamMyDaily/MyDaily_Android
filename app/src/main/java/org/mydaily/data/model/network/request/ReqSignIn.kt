package org.mydaily.data.model.network.request


import com.google.gson.annotations.SerializedName

data class ReqSignIn(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)