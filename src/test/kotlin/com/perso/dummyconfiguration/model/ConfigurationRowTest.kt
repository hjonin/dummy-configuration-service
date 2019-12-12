package com.perso.dummyconfiguration.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ConfigurationRowTest {

    private data class DummyConfigurationRowWith2Indexes(
            @Index val publisher: String,
            @Index val genre: String,
            val dummyProperty: Int? = null
    ) : ConfigurationRow

    private class DummyConfigurationRowWith3Indexes(
            @Index val publisher: String,
            @Index val genre: String,
            @Index val year: String
    ) : ConfigurationRow

    @Nested
    inner class TestMatch {
        @Test
        fun `test match any`() {
            val acr = DummyConfigurationRowWith2Indexes("*", "*")
            assertTrue(acr.match(DummyConfigurationRowWith2Indexes("DARGAUD", "Adventure")))
        }

        @Test
        fun `test match publisher`() {
            val acr = DummyConfigurationRowWith2Indexes("DELCOURT", "*")
            assertTrue(acr.match(DummyConfigurationRowWith2Indexes("DELCOURT", "Adventure")))
            assertTrue(acr.match(DummyConfigurationRowWith2Indexes("DELCOURT", "Fantasy")))
            assertFalse(acr.match(DummyConfigurationRowWith2Indexes("DARGAUD", "Adventure")))
        }

        @Test
        fun `test match publisher and genre`() {
            val acr = DummyConfigurationRowWith2Indexes("DELCOURT", "Fantasy")
            assertTrue(acr.match(DummyConfigurationRowWith2Indexes("DELCOURT", "Fantasy")))
            assertFalse(acr.match(DummyConfigurationRowWith2Indexes("DELCOURT", "Adventure")))
            assertFalse(acr.match(DummyConfigurationRowWith2Indexes("DARGAUD", "Fantasy")))
        }

        @Test
        fun `test match genre`() {
            val acr = DummyConfigurationRowWith2Indexes("*", "Fantasy")
            assertTrue(acr.match(DummyConfigurationRowWith2Indexes("DELCOURT", "Fantasy")))
            assertTrue(acr.match(DummyConfigurationRowWith2Indexes("DARGAUD", "Fantasy")))
            assertFalse(acr.match(DummyConfigurationRowWith2Indexes("DARGAUD", "Adventure")))
        }

        @Test
        fun `test match different genre`() {
            val acr = DummyConfigurationRowWith2Indexes("*", "Fantasy")
            assertThrows(IllegalArgumentException::class.java) {
                acr.match(DummyConfigurationRowWith3Indexes("*", "*", "Fantasy"))
            }
        }
    }
}