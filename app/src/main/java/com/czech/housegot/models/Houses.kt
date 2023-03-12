package com.czech.housegot.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "houses")
data class Houses(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String? = null,
    val name: String? = null,
    val region: String? = null,
    val coatOfArms: String? = null,
    val words: String? = null,
    val titles: List<String?>? = null,
    val seats: List<String?>? = null,
    val currentLord: String? = null,
    val heir: String? = null,
    val overlord: String? = null,
    val founded: String? = null,
    val founder: String? = null,
    val diedOut: String? = null,
    val ancestralWeapons: List<String>? = null,
    val cadetBranches: List<String?>? = null,
    val swornMembers: List<String?>? = null,
    var page: Int,
)