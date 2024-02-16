package com.example.mvvmjetpackcomposelogin.data.model

data class User(
    val id: String?,
    val userId: String,
    val mail : String,
    val nombre : String,
    val imagen : String
){
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "user_id" to this.userId,
            "mail" to this.mail,
            "nombre"  to this.nombre,
            "imagen" to this.imagen
        )
    }
}
