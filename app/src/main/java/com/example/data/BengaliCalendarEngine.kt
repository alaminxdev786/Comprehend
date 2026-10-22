package com.example.data

import com.example.model.BengaliDate
import com.example.model.HijriDate
import java.util.*

object BengaliCalendarEngine {

    private val banglaMonths = listOf(
        "বৈশাখ", "জ্যৈষ্ঠ", "আষাঢ়", "শ্রাবণ", "ভাদ্র", "আশ্বিন",
        "কার্তিক", "অগ্রহায়ণ", "পৌষ", "মাঘ", "ফাল্গুন", "চৈত্র"
    )

    private val englishBanglaMonths = listOf(
        "Boishakh", "Jaishtha", "Asharh", "Srabon", "Bhadro", "Ashwin",
        "Kartik", "Agrahayan", "Poush", "Magh", "Falgun", "Choitra"
    )

    private val seasonsBn = listOf(
        "গ্রীষ্মকাল", "বর্ষাকাল", "শরৎকাল", "হেমন্তকাল", "শীতকাল", "বসন্তকাল"
    )
    private val seasonsEn = listOf(
        "Summer", "Monsoon", "Autumn", "Late Autumn", "Winter", "Spring"
    )

    private val hijriMonthsBn = listOf(
        "মুহাররম", "সফর", "রবিউল আউয়াল", "রবিউস সানি", "জমাদিউল আউয়াল", "জমাদিউস সানি",
        "রজব", "শাবান", "রমজান", "শাওয়াল", "জিলকদ", "জিলহজ"
    )
    private val hijriMonthsEn = listOf(
        "Muharram", "Safar", "Rabi' al-Awwal", "Rabi' al-Thani", "Jumada al-Awwal", "Jumada al-Thani",
        "Rajab", "Sha'ban", "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
    )

    fun getBengaliDate(cal: Calendar = Calendar.getInstance()): BengaliDate {
        val gYear = cal.get(Calendar.YEAR)
        val isLeapYear = (gYear % 4 == 0 && gYear % 100 != 0) || (gYear % 400 == 0)

        // Day counts for Bengali months (Bangla Academy 2019 standard)
        val monthLengths = intArrayOf(
            31, 31, 31, 31, 31, 30,
            30, 30, 30, 30, if (isLeapYear) 31 else 30, 30
        )

        // April 14 is Boishakh 1
        val boishakh1 = Calendar.getInstance().apply {
            set(gYear, Calendar.APRIL, 14, 0, 0, 0)
        }

        val bYear = if (cal.before(boishakh1)) gYear - 594 else gYear - 593

        val diffDays: Int
        if (cal.before(boishakh1)) {
            // In current Gregorian year before April 14, reference previous year's April 14
            val prevBoishakh1 = Calendar.getInstance().apply {
                set(gYear - 1, Calendar.APRIL, 14, 0, 0, 0)
            }
            diffDays = ((cal.timeInMillis - prevBoishakh1.timeInMillis) / (1000 * 60 * 60 * 24)).toInt()
        } else {
            diffDays = ((cal.timeInMillis - boishakh1.timeInMillis) / (1000 * 60 * 60 * 24)).toInt()
        }

        var accumulated = 0
        var bMonthIndex = 0
        var bDay = 1

        for (i in monthLengths.indices) {
            val length = monthLengths[i]
            if (accumulated + length > diffDays) {
                bMonthIndex = i
                bDay = diffDays - accumulated + 1
                break
            }
            accumulated += length
        }

        val seasonIndex = (bMonthIndex / 2).coerceIn(0, 5)

        return BengaliDate(
            dayNumber = bDay,
            monthNameBn = banglaMonths[bMonthIndex],
            monthNameEn = englishBanglaMonths[bMonthIndex],
            yearBn = PrayerTimeCalculator.convertToBengaliDigits(bYear),
            seasonBn = seasonsBn[seasonIndex],
            seasonEn = seasonsEn[seasonIndex]
        )
    }

    fun getEstimatedHijriDate(cal: Calendar = Calendar.getInstance()): HijriDate {
        // Approximate Julian Day algorithm for Hijri calculation
        val gYear = cal.get(Calendar.YEAR)
        val gMonth = cal.get(Calendar.MONTH) + 1
        val gDay = cal.get(Calendar.DAY_OF_MONTH)

        var m = gMonth
        var y = gYear
        if (m < 3) {
            y -= 1
            m += 12
        }

        val a = y / 100
        val b = 2 - a + a / 4
        val jd = (365.25 * (y + 4716)).toLong() + (30.6001 * (m + 1)).toInt() + gDay + b - 1524.5

        val z = jd - 1948439.5
        val hYear = ((30 * z + 10646) / 10631).toInt()
        val monthPriorDays = ((z - 29 - (10631 * hYear) / 30).coerceAtLeast(0.0)).toInt()
        val hMonth = (((monthPriorDays * 11 + 330) / 325) % 12).coerceIn(0, 11)
        val hDay = ((monthPriorDays % 30) + 1).coerceIn(1, 30)

        return HijriDate(
            dayNumber = hDay,
            monthNameBn = hijriMonthsBn[hMonth],
            monthNameEn = hijriMonthsEn[hMonth],
            yearBn = PrayerTimeCalculator.convertToBengaliDigits(hYear)
        )
    }
}
