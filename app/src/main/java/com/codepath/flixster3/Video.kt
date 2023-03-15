package com.codepath.flixster3

data class Video(val name: String, val key: String, val id: String)

data class Videos(val results: List<Video>)