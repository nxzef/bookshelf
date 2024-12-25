package com.example.bookshelf.data.model

data class Volumes(
    val kind: String,
    val items: List<Book>,
    val totalItems: Int
)

data class Book(
    val kind: String,
    val id: String,
    val etag: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>
)
