package no.gruppe02.hiof.calendown.model

import java.util.Date

data class Event(
    val uid: String = "",
    var owner: User,

    var title: String = "",
    var description: String = "",
    var image: String = "",
    var icon: String = "",
    var category: String = "",

    var date: Date,
    )
