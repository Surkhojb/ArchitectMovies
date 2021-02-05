package com.surkhojb.architectmovies.ui.main.top_rated

import com.surkhojb.domain.Cast
import com.surkhojb.domain.Movie
import com.surkhojb.domain.MovieCast

val mockedMovie = Movie(
    0,
    "Title",
    "Overview",
    "01/01/2025",
    "",
    "",
    "EN",
    "Title",
    5.0,
    5.1,
    60,
    false,
    MovieCast(emptyList())
)

val mockedCast = Cast(
    true,
    "Character",
    "1",
    1,
    1,
    "deparmetn",
    "name",
    1,
    "originalName",
    10.0,
    ""
)