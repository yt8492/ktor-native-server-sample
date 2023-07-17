package com.yt8492.ktornative.model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    val id: String,
    val title: String,
    val content: String,
    @SerialName("create_at")
    val createAt: Instant,
    @SerialName("update_at")
    val updateAt: Instant,
)
