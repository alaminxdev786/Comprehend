package com.example.data

import com.example.model.BangladeshDistrict
import com.example.model.PrayerTimes
import java.text.SimpleDateFormat
import java.util.*

object PrayerTimeCalculator {

    fun calculateTimesForDate(district: BangladeshDistrict, calendar: Calendar = Calendar.getInstance()): PrayerTimes {
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

        // Baseline Dhaka solar curve approximation (minutes from midnight)
        // Spring/Summer vs Autumn/Winter curve
        val angle = 2.0 * Math.PI * (dayOfYear - 80) / 365.0
        val solarDeclination = 23.44 * Math.sin(angle)

        // Solar noon in Dhaka is approx 11:58 AM to 12:05 PM standard time
        val noonMinute = 12 * 60 + (Math.sin(angle * 2) * 5).toInt()

        // Approximate half-day length variation in Dhaka (latitude 23.8° N)
        val daylightHalfMinutes = (6 * 60 + 10 + (Math.sin(angle) * 65)).toInt()

        var sunriseMin = noonMinute - daylightHalfMinutes
        var sunsetMin = noonMinute + daylightHalfMinutes

        // Fajr is approximately 80 to 90 minutes before sunrise in Bangladesh
        var fajrMin = sunriseMin - 82
        // Asr (Shafi/Hanafi average in BD Islamic Foundation)
        var asrMin = noonMinute + (daylightHalfMinutes * 0.62).toInt()
        var maghribMin = sunsetMin
        var ishaMin = maghribMin + 78

        // Apply district geographical offset (+ to west, - to east)
        fajrMin += district.fajrOffsetMin
        sunriseMin += district.fajrOffsetMin
        asrMin += (district.fajrOffsetMin + district.maghribOffsetMin) / 2
        maghribMin += district.maghribOffsetMin
        ishaMin += district.maghribOffsetMin

        val sehriMin = fajrMin - 5
        val iftarMin = maghribMin

        val nowMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE)

        val nextPrayerInfo = determineNextPrayer(nowMinutes, fajrMin, dhuhrMin = noonMinute + 3, asrMin, maghribMin, ishaMin)

        return PrayerTimes(
            fajr = formatMinutes(fajrMin),
            sunrise = formatMinutes(sunriseMin),
            dhuhr = formatMinutes(noonMinute + 3),
            asr = formatMinutes(asrMin),
            maghrib = formatMinutes(maghribMin),
            isha = formatMinutes(ishaMin),
            sehriEnd = formatMinutes(sehriMin),
            iftarTime = formatMinutes(iftarMin),
            nextPrayerNameBn = nextPrayerInfo.nameBn,
            nextPrayerNameEn = nextPrayerInfo.nameEn,
            timeRemainingFormatted = formatRemaining(nextPrayerInfo.remainingMinutes)
        )
    }

    private fun determineNextPrayer(
        nowMin: Int,
        fajr: Int,
        dhuhrMin: Int,
        asr: Int,
        maghrib: Int,
        isha: Int
    ): NextPrayerResult {
        return when {
            nowMin < fajr -> NextPrayerResult("ফজর", "Fajr", fajr - nowMin)
            nowMin < dhuhrMin -> NextPrayerResult("জোহর", "Dhuhr", dhuhrMin - nowMin)
            nowMin < asr -> NextPrayerResult("আসর", "Asr", asr - nowMin)
            nowMin < maghrib -> NextPrayerResult("মাগরিব (ইফতার)", "Maghrib (Iftar)", maghrib - nowMin)
            nowMin < isha -> NextPrayerResult("ইশা", "Isha", isha - nowMin)
            else -> {
                // Next is tomorrow's Fajr
                val toMidnight = 1440 - nowMin
                NextPrayerResult("ফজর (আগামীকাল)", "Tomorrow Fajr", toMidnight + fajr)
            }
        }
    }

    private data class NextPrayerResult(
        val nameBn: String,
        val nameEn: String,
        val remainingMinutes: Int
    )

    private fun formatMinutes(minutes: Int): String {
        val total = (minutes + 1440) % 1440
        var hour = total / 60
        val min = total % 60
        val amPm = if (hour >= 12) "PM" else "AM"
        if (hour > 12) hour -= 12
        if (hour == 0) hour = 12
        return String.format(Locale.US, "%d:%02d %s", hour, min, amPm)
    }

    private fun formatRemaining(minutes: Int): String {
        val h = minutes / 60
        val m = minutes % 60
        return if (h > 0) {
            "${convertToBengaliDigits(h)} ঘণ্টা ${convertToBengaliDigits(m)} মিনিট"
        } else {
            "${convertToBengaliDigits(m)} মিনিট"
        }
    }

    fun convertToBengaliDigits(num: Int): String {
        val bnDigits = charArrayOf('০', '১', '২', '৩', '৪', '৫', '৬', '৭', '৮', '৯')
        val str = num.toString()
        val sb = StringBuilder()
        for (c in str) {
            if (c in '0'..'9') {
                sb.append(bnDigits[c - '0'])
            } else {
                sb.append(c)
            }
        }
        return sb.toString()
    }
}
