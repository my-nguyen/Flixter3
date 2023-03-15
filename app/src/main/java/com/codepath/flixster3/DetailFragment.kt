package com.codepath.flixster3

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.codepath.flixster3.databinding.FragmentDetailBinding
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "DetailActivity-Truong"

// reference: https://guides.codepath.com/android/Streaming-Youtube-Videos-with-YouTubePlayerView
const val YOUTUBE_API_KEY = "AIzaSyAXtaluc1WXogSMzdNbtq030tL5EqS5O20"

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentDetailBinding.inflate(inflater, container, false)

        // supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Log.d(TAG, "version: ${Build.VERSION.SDK_INT}")
//        val movie = intent.getSerializableExtra("MOVIE") as Movie
//        val genreMap = intent.getSerializableExtra("GENRES") as GenreMap
//        val movie = Movie(0, "", listOf(), "", "", "", "", 0.0f)
//        val genreMap = GenreMap()
        val movie = args.movie
        val genreMap = args.genres
        val API_KEY = "1fca74d1a066b2433a06dea9b96239fe"

        binding.apply {
            // val release = movie.release_date.split("-").asReversed().joinToString("/")
            val year = movie.release_date.split("-")[0]
            title.text = Html.fromHtml("<b>${movie.title}</b> ($year)")
            rating.rating = movie.vote_average
            genres.text = movie.genre_ids.joinToString(", ") { genreMap.map[it]!! }
            overview.text = movie.overview

            MovieService.instance.getVideos(movie.id, API_KEY).enqueue(object : Callback<Videos> {
                override fun onFailure(call: Call<Videos>, t: Throwable) {
                    Log.d(TAG, "onFailure")
                }

                override fun onResponse(call: Call<Videos>, response: Response<Videos>) {
                    val key = response.body()!!.results[0].key
                    Log.d(TAG, "onResponse, body ${response.body()}")
//                    val player =
//                        supportFragmentManager.findFragmentById(R.id.player) as YouTubePlayerSupportFragment
                    player.initialize(
                        YOUTUBE_API_KEY,
                        object : YouTubePlayer.OnInitializedListener {
                            override fun onInitializationFailure(
                                p0: YouTubePlayer.Provider?,
                                p1: YouTubeInitializationResult?
                            ) {
                                Log.d(TAG, "onInitializationFailure: $p1")
                            }

                            override fun onInitializationSuccess(
                                p0: YouTubePlayer.Provider?,
                                p1: YouTubePlayer?,
                                p2: Boolean
                            ) {
                                Log.d(TAG, "onInitializationSuccess")
                                p1?.cueVideo(key)
                            }
                        })
                }
            })
        }

        return binding.root
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                onBackPressed()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}