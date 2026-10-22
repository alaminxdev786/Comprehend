package com.example.model

data class PrayerTimes(
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String,
    val sehriEnd: String,
    val iftarTime: String,
    val nextPrayerNameBn: String,
    val nextPrayerNameEn: String,
    val timeRemainingFormatted: String
)

data class BengaliDate(
    val dayNumber: Int,
    val monthNameBn: String,
    val monthNameEn: String,
    val yearBn: String,
    val seasonBn: String,
    val seasonEn: String
)

data class HijriDate(
    val dayNumber: Int,
    val monthNameBn: String,
    val monthNameEn: String,
    val yearBn: String
)

data class BangladeshDistrict(
    val id: String,
    val nameBn: String,
    val nameEn: String,
    val divisionBn: String,
    val fajrOffsetMin: Int,
    val maghribOffsetMin: Int
)

data class BangladeshHoliday(
    val dateBn: String,
    val titleBn: String,
    val titleEn: String,
    val typeBn: String,
    val isGovernmentHoliday: Boolean
)
