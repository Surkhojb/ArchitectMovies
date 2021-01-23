package com.surkhojb.architectmovies.data.mapper

import com.surkhojb.architectmovies.data.local.room.model.MovieCast as RoomMovieCast
import com.surkhojb.architectmovies.data.local.room.model.Cast as RoomCast
import com.surkhojb.architectmovies.data.remote.model.Cast as RemoteCast
import com.surkhojb.architectmovies.data.local.room.model.Movie as RoomMovie
import com.surkhojb.architectmovies.data.remote.model.Movie as RemoteMovie

import com.surkhojb.domain.Movie  as DomainMovie
import com.surkhojb.domain.Cast  as DomainCast
import com.surkhojb.domain.MovieCast  as DomainMovieCast

fun RemoteMovie.mapToDomainMovie() = DomainMovie(
    id,
    title ?: "",
    overview ?: "",
    releaseDate,
    posterPath ?: "",
    backdropPath ?: "",
    originalLanguage ?: "",
    originalTitle ?: "",
    popularity?: 0.0,
    voteAverage,
    voteCount ?: 0,
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

fun DomainMovie.toRoomMovie(type: String = "top_rated"): RoomMovie = RoomMovie(
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
    RoomMovieCast(this.cast?.cast?.map { it.mapToRoomCast()} ?: emptyList()),
    movieType = type
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

fun DomainCast.mapToRoomCast() = RoomCast(
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