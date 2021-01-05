package com.surkhojb.architectmovies.data.mapper

import com.surkhojb.architectmovies.data.local.model.MovieCast
import com.surkhojb.architectmovies.model.Cast as RemoteCast
import com.surkhojb.architectmovies.data.local.model.Movie as DbMovie
import com.surkhojb.architectmovies.data.local.model.Cast as DbCast
import com.surkhojb.architectmovies.model.Movie as RemoteMovie

fun RemoteMovie.mapToDbMovie() = DbMovie(
    id,
    title,
    overview,
    releaseDate,
    posterPath,
    backdropPath,
    originalLanguage,
    originalTitle,
    popularity,
    voteAverage,
    voteCount,
    false,
    MovieCast(emptyList())
)

fun RemoteCast.mapToDbCast() = DbCast(
    adult,
    character,
    credit_id,
    gender, id,
    known_for_department,
    name,
    order,
    original_name,
    popularity,
    profile_path
)