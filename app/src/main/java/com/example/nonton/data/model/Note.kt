package com.example.nonton.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Note(
    @DocumentId
    val id: String = "",
    val content: String = "",
    val title: String = "",
    val userId: String = "",
    @ServerTimestamp
    val createdAt: Date? = null,
    val updateAt: Date? = null
)