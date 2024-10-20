package com.example.harrypotterapp

import android.util.Log
import com.example.harrypotterapp.data.mappers.toFormattedDate
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class DateConversionTest {

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
    }

    @Test
    fun `given single digit day, when formatted, return formatted date`() {
        val date = "1-1-2023"
        val expected = "01 Jan 2023"
        assertEquals(expected, date.toFormattedDate())
    }

    @Test
    fun `given  valid date with full format, when formatted, return formatted date`() {
        val date = "10-12-2023"
        val expected = "10 Dec 2023"
        assertEquals(expected, date.toFormattedDate())
    }

    @Test
    fun `given single digit month, when formatted, return formatted date`() {
        val date = "31-1-2023"
        val expected = "31 Jan 2023"
        assertEquals(expected, date.toFormattedDate())
    }

    @Test
    fun `given out of bounds date, when formatted, return closest previous day`() {
        val date = "31-02-2023"
        val expected = "28 Feb 2023"
        assertEquals(expected, date.toFormattedDate())
    }

    @Test
    fun `given American style date, when formatted, return null`() {
        val date = "12-31-2024"
        assertNull(date.toFormattedDate())
    }

    @Test
    fun `given Japanese style date, when formatted, return null`() {
        val date = "2024-12-31"
        assertNull(date.toFormattedDate())
    }

    @Test
    fun `given random string, when formatted, return null`() {
        val date = "randomString"
        assertNull(date.toFormattedDate())
    }

    @Test
    fun `test null input`() {
        val date: String? = null
        assertNull(date.toFormattedDate())
    }
}
