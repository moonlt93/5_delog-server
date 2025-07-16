package com.delog.server.aggregate.order.domain

enum class DeliveryPlatform(
    val description: String,
) {
    BAEMIN("배달의민족"),
    COUPANG_EATS("쿠팡이츠"),
    YOGIYO("요기요"),
    DDANGYO("땡겨요"),
    ETC("기타"),
}
