package com.example.learningdevelopment.data.remote

import com.example.learningdevelopment.data.model.Post
import retrofit2.http.GET

interface JsonPlaceholderApi {
    @GET("posts")
    suspend fun getPostById(): List<Post>
}