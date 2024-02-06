package com.example.mvvmjetpackcomposelogin.model

data class User(
    val id: String?,
    val userId: String,
    val avatarUrl: String
){
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "user_id" to this.userId,
            "avatar_url" to this.avatarUrl
        )
    }
}
