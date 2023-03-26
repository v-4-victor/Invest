package com.v4victor.core.dto

data class News(
    val datetime: Long,
    val headline: String,
    val id: Int,
    val image: String,
    val source: String,
    val summary: String,
    val url: String)