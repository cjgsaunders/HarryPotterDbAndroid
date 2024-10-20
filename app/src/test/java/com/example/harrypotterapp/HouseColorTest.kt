package com.example.harrypotterapp

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.harrypotterapp.data.mappers.HouseColor
import com.example.harrypotterapp.data.mappers.mapHouseToColor
import io.mockk.every
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test

class HouseColorTest {

    @Test
    fun `given a string, when map method called, return correct enum`() {
        assertEquals(HouseColor.GRYFFINDOR, "Gryffindor".mapHouseToColor())
        assertEquals(HouseColor.SLYTHERIN, "Slytherin".mapHouseToColor())
        assertEquals(HouseColor.HUFFLEPUFF, "Hufflepuff".mapHouseToColor())
        assertEquals(HouseColor.RAVENCLAW, "Ravenclaw".mapHouseToColor())
    }

    @Test
    fun `given a bad string, when map method called, return default enum`() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0

        assertEquals(HouseColor.DEFAULT, "UnknownHouse".mapHouseToColor())
        assertEquals(HouseColor.DEFAULT, "".mapHouseToColor())
        assertEquals(HouseColor.DEFAULT, "123454".mapHouseToColor())
    }

    @Test
    fun `given each house is stored in an enum, then enum matches all houses plus default case`() {
        val expectedValues = listOf(
            HouseColor.GRYFFINDOR,
            HouseColor.SLYTHERIN,
            HouseColor.HUFFLEPUFF,
            HouseColor.RAVENCLAW,
            HouseColor.DEFAULT
        )

        val actualValues = HouseColor.entries

        assertEquals(expectedValues, actualValues)
    }

    @Test
    fun `given each house has a color, then each house should match the correct color`() {
        assertEquals(Color(0xFF740001), HouseColor.GRYFFINDOR.hexColor)
        assertEquals(Color(0xFF1A472A), HouseColor.SLYTHERIN.hexColor)
        assertEquals(Color(0xFFEEB939), HouseColor.HUFFLEPUFF.hexColor)
        assertEquals(Color(0xFF0C1A40), HouseColor.RAVENCLAW.hexColor)
        assertEquals(null, HouseColor.DEFAULT.hexColor)
    }
}
