package com.czech.housegot.models


data class HouseDetail(
    val url: String?,
    val name: String?,
    val region: String?,
    val coatOfArms: String?,
    val words: String?,
    val titles: List<String?>?,
    val seats: List<String?>?,
    val currentLord: String?,
    val heir: String?,
    val overlord: String?,
    val founded: String?,
    val founder: String?,
    val diedOut: String?,
    val ancestralWeapons: List<String?>?,
    val cadetBranches: List<Any?>?,
    val swornMembers: List<String?>?
)