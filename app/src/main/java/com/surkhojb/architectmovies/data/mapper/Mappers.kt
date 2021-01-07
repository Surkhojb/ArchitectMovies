package com.surkhojb.architectmovies.data.mapper

import com.surkhojb.architectmovies.data.local.room.model.MovieCast as RoomMovieCast
import com.surkhojb.architectmovies.data.local.room.model.Cast as RoomCast
import com.surkhojb.architectmovies.model.Cast as RemoteCast
import com.surkhojb.architectmovies.data.local.room.model.Movie as RoomMovie
import com.surkhojb.architectmovies.model.Movie as RemoteMovie

import com.surkhojb.domain.Movie  as DomainMovie
import com.surkhojb.domain.Cast  as DomainCast
import com.surkhojb.domain.MovieCast  as DomainMovieCast

fun RemoteMovie.mapToDomainMovie() = DomainMovie(
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
    DomainMovieCast(emptyList())
)

fun RemoteCast.mapToDomainCast() = DomainCast(
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

fun DomainMovie.toRoomMovie(): RoomMovie = RoomMovie(
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
    favorite,
    RoomMovieCast(emptyList())
)

fun RoomMovie.mapToDomainMovie(): DomainMovie = DomainMovie(
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
    favorite,
    DomainMovieCast(emptyList())
)

fun RoomCast.mapToDomainCast() = DomainCast(
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