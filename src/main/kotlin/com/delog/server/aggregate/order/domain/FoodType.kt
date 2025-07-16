package com.delog.server.aggregate.order.domain

enum class FoodType(
    val description: String,
) {
    KOREAN_FOOD("한식"),
    WESTERN_FOOD("양식"),
    CHINESE_FOOD("중식"),
    JAPANESE_FOOD("일식"),
    FAST_FOOD("패스트푸드"),
    DESSERT("디저트"),
}
