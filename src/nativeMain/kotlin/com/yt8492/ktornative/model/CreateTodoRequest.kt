package com.yt8492.ktornative.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateTodoRequest(
    val title: String,
    val content: String,
)
