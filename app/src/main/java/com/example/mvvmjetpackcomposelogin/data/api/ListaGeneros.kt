package com.example.mvvmjetpackcomposelogin.data.api

sealed class ListaGeneros(val id: Int) {
    data object Action : ListaGeneros(28)
    data object Adventure : ListaGeneros(12)
    data object Animation : ListaGeneros(16)
    data object Comedy : ListaGeneros(35)
    data object Crime : ListaGeneros(80)
    data object Documentary : ListaGeneros(99)
    data object Drama : ListaGeneros(18)
    data object Family : ListaGeneros(10751)
    data object Fantasy : ListaGeneros(14)
    data object History : ListaGeneros(36)
    data object Horror : ListaGeneros(27)
    data object Music : ListaGeneros(10402)
    data object Mystery : ListaGeneros(9648)
    data object Romance : ListaGeneros(10749)
    data object ScienceFiction : ListaGeneros(878)
    data object TVMovie : ListaGeneros(10770)
    data object Thriller : ListaGeneros(53)
    data object War : ListaGeneros(10752)
    data object Western : ListaGeneros(37)
}