package com.example.chatbotsolvisv1.models

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("queryText") val content: String,
    @SerializedName("sessionId") val sessionId: String,
)
