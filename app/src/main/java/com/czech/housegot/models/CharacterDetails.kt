package com.czech.housegot.models


import com.google.gson.annotations.SerializedName

data class CharacterDetails(
    val url: String?,
    val name: String?,
    val gender: String?,
    val culture: String?,
    val born: String?,
    val died: String?,
    val titles: List<String?>?,
    val aliases: List<String?>?,
    val father: String?,
    val mother: String?,
    val spouse: String?,
    val allegiances: List<String?>?,
    val books: List<String?>?,
    val povBooks: List<Any?>?,
    val tvSeries: List<String?>?,
    val playedBy: List<String?>?
)