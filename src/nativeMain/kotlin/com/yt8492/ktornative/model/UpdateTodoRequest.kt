package com.yt8492.ktornative.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoRequest(
    val title: String?,
    val content: String?,
)
