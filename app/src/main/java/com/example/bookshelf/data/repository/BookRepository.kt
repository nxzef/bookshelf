package com.example.bookshelf.data.repository

import com.example.bookshelf.data.model.Volumes
import com.example.bookshelf.data.network.BookApiService
import javax.inject.Inject

class BookRepository @Inject constructor(private val bookApiService: BookApiService) {
    suspend fun getVolumes(query: String): Volumes = bookApiService.getVolumes(query)
}