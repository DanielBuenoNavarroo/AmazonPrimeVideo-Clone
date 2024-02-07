package com.example.mvvmjetpackcomposelogin.model

data class User(
    val id: String?,
    val userId: String,
    val nombre: String,
    val apellido: String
){
    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "user_id" to this.userId,
            "nombre" to this.nombre,
            "apellido" to this.apellido
        )
    }
}
