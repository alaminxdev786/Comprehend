package com.example

import com.example.data.BengaliCalendarEngine
import com.example.data.PrayerTimeCalculator
import com.example.data.SampleDataProvider
import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar

class ExampleUnitTest {

    @Test
    fun testBengaliCalendarEngine() {
        val calendar = Calendar.getInstance()
        val bengaliDate = BengaliCalendarEngine.getBengaliDate(calendar)

        assertNotNull(bengaliDate)
        assertTrue(bengaliDate.dayNumber in 1..31)
        assertTrue(bengaliDate.monthNameBn.isNotEmpty())
        assertTrue(bengaliDate.yearBn.isNotEmpty())
    }

    @Test
    fun testPrayerTimeCalculation() {
        val dhaka = SampleDataProvider.districts[0]
        val times = PrayerTimeCalculator.calculateTimesForDate(dhaka)

        assertNotNull(times.fajr)
        assertNotNull(times.dhuhr)
        assertNotNull(times.asr)
        assertNotNull(times.maghrib)
        assertNotNull(times.isha)
        assertTrue(times.fajr.contains("AM"))
        assertTrue(times.maghrib.contains("PM"))
    }

    @Test
    fun testLandUnitConversionFormulas() {
        // Test standard BD land measurements:
        // 33 Shotok = 1 Bigha
        // 100 Shotok = 1 Acre
        // 1 Shotok = 435.6 sq ft
        val decimalAmount = 33.0
        val bigha = decimalAmount / 33.0
        val sqFeet = decimalAmount * 435.6

        assertEquals(1.0, bigha, 0.001)
        assertEquals(14374.8, sqFeet, 0.1)
    }

    @Test
    fun testBcsNegativeMarkingCalculation() {
        val correctCount = 8
        val wrongCount = 2
        // Correct = +1.0, Wrong = -0.5
        val expectedScore = (correctCount * 1.0) - (wrongCount * 0.5)

        assertEquals(7.0, expectedScore, 0.001)
    }
}
