package com.sooum.domain.model

sealed class PlaceRank(
    open val rank: Int
) {
    data class RankHeader(
        override val rank: Int,
    ) : PlaceRank(rank)

    data class PostItem(
        override val rank: Int,
        val place: PlaceItem
    ) : PlaceRank(rank)
}