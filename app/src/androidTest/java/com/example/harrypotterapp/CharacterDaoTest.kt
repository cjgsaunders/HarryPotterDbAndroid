package com.example.harrypotterapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.harrypotterapp.data.database.CharacterDao
import com.example.harrypotterapp.data.database.CharacterDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class CharacterDaoTest {

    private lateinit var dao: CharacterDao
    private lateinit var dataBase: CharacterDatabase



    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dataBase = Room.inMemoryDatabaseBuilder(
            context, CharacterDatabase::class.java).build()
        dao = dataBase.dao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        dataBase.close()
    }
}
