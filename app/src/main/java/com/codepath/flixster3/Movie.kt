package com.codepath.flixster3

const val IMAGE_PREFIX = "https://image.tmdb.org/t/p/w342/"

data class Movie(
    val id: Int,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Float
): java.io.Serializable {
    fun getPoster() = "$IMAGE_PREFIX$poster_path"
    fun getBackdrop() = "$IMAGE_PREFIX$backdrop_path"
}

data class Movies(val results: List<Movie>)