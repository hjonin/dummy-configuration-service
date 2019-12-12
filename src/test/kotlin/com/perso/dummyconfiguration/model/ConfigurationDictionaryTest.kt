package com.perso.dummyconfiguration.model

import com.perso.dummyconfiguration.model.computation.configuration.FooConfiguration
import com.perso.dummyconfiguration.model.computation.configuration.FooConfigurationValue
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ConfigurationDictionaryTest {

    private val priceConfiguration = FooConfiguration(
            FooConfigurationValue("*", "*"),
            FooConfigurationValue("DELCOURT", "*"),
            FooConfigurationValue("DARGAUD", "*"),
            FooConfigurationValue("*", "Adventure"),
            FooConfigurationValue("DELCOURT", "Adventure"),
            FooConfigurationValue("*", "Fantasy")
    )

    @Nested
    inner class TestSortAndSearch {
        @Test
        fun `test search any and sort`() {
            priceConfiguration.filter(SearchRow(objectType = null, objectSubType = null))
            val priceConfigurationValues = priceConfiguration.values.toList()
            assertEquals(6, priceConfigurationValues.size)
            assertEquals("DELCOURT", priceConfigurationValues[0].objectType)
            assertEquals("Adventure", priceConfigurationValues[0].objectSubType)
            assertEquals("*", priceConfigurationValues[1].objectType)
            assertEquals("Adventure", priceConfigurationValues[1].objectSubType)
            assertEquals("*", priceConfigurationValues[2].objectType)
            assertEquals("Fantasy", priceConfigurationValues[2].objectSubType)
            assertEquals("DARGAUD", priceConfigurationValues[3].objectType)
            assertEquals("*", priceConfigurationValues[3].objectSubType)
            assertEquals("DELCOURT", priceConfigurationValues[4].objectType)
            assertEquals("*", priceConfigurationValues[4].objectSubType)
            assertEquals("*", priceConfigurationValues[5].objectType)
            assertEquals("*", priceConfigurationValues[5].objectSubType)
        }

        @Test
        fun `test search publisher`() {
            priceConfiguration.filter(SearchRow(objectType = "DELCOURT", objectSubType = null))
            val priceConfigurationValues = priceConfiguration.values.toList()
            assertEquals(5, priceConfigurationValues.size)
            assertEquals("DELCOURT", priceConfigurationValues[0].objectType)
            assertEquals("Adventure", priceConfigurationValues[0].objectSubType)
            assertEquals("*", priceConfigurationValues[1].objectType)
            assertEquals("Adventure", priceConfigurationValues[1].objectSubType)
            assertEquals("*", priceConfigurationValues[2].objectType)
            assertEquals("Fantasy", priceConfigurationValues[2].objectSubType)
            assertEquals("DELCOURT", priceConfigurationValues[3].objectType)
            assertEquals("*", priceConfigurationValues[3].objectSubType)
            assertEquals("*", priceConfigurationValues[4].objectType)
            assertEquals("*", priceConfigurationValues[4].objectSubType)
        }

        @Test
        fun `test search genre`() {
            priceConfiguration.filter(SearchRow(objectType = null, objectSubType = "Adventure"))
            val priceConfigurationValues = priceConfiguration.values.toList()
            assertEquals(5, priceConfigurationValues.size)
            assertEquals("DELCOURT", priceConfigurationValues[0].objectType)
            assertEquals("Adventure", priceConfigurationValues[0].objectSubType)
            assertEquals("*", priceConfigurationValues[1].objectType)
            assertEquals("Adventure", priceConfigurationValues[1].objectSubType)
            assertEquals("DARGAUD", priceConfigurationValues[2].objectType)
            assertEquals("*", priceConfigurationValues[2].objectSubType)
            assertEquals("DELCOURT", priceConfigurationValues[3].objectType)
            assertEquals("*", priceConfigurationValues[3].objectSubType)
            assertEquals("*", priceConfigurationValues[4].objectType)
            assertEquals("*", priceConfigurationValues[4].objectSubType)
        }

        @Test
        fun `test search publisher and genre`() {
            priceConfiguration.filter(SearchRow(objectType = "DELCOURT", objectSubType = "Adventure"))
            val priceConfigurationValues = priceConfiguration.values.toList()
            assertEquals(4, priceConfigurationValues.size)
            assertEquals("DELCOURT", priceConfigurationValues[0].objectType)
            assertEquals("Adventure", priceConfigurationValues[0].objectSubType)
            assertEquals("*", priceConfigurationValues[1].objectType)
            assertEquals("Adventure", priceConfigurationValues[1].objectSubType)
            assertEquals("DELCOURT", priceConfigurationValues[2].objectType)
            assertEquals("*", priceConfigurationValues[2].objectSubType)
            assertEquals("*", priceConfigurationValues[3].objectType)
            assertEquals("*", priceConfigurationValues[3].objectSubType)
        }

        @Test
        fun `test search unknown publisher`() {
            priceConfiguration.filter(SearchRow(objectType = "L'ASSOCIATION", objectSubType = null))
            val priceConfigurationValues = priceConfiguration.values.toList()
            assertEquals(3, priceConfigurationValues.size)
            assertEquals("*", priceConfigurationValues[0].objectType)
            assertEquals("Adventure", priceConfigurationValues[0].objectSubType)
            assertEquals("*", priceConfigurationValues[1].objectType)
            assertEquals("Fantasy", priceConfigurationValues[1].objectSubType)
            assertEquals("*", priceConfigurationValues[2].objectType)
            assertEquals("*", priceConfigurationValues[2].objectSubType)
        }

        @Test
        fun `test search unknown genre`() {
            priceConfiguration.filter(SearchRow(objectType = null, objectSubType = "Historical"))
            val priceConfigurationValues = priceConfiguration.values.toList()
            assertEquals(3, priceConfigurationValues.size)
            assertEquals("DARGAUD", priceConfigurationValues[0].objectType)
            assertEquals("*", priceConfigurationValues[0].objectSubType)
            assertEquals("DELCOURT", priceConfigurationValues[1].objectType)
            assertEquals("*", priceConfigurationValues[1].objectSubType)
            assertEquals("*", priceConfigurationValues[2].objectType)
            assertEquals("*", priceConfigurationValues[2].objectSubType)
        }

        @Test
        fun `test search unknown publisher and genre`() {
            priceConfiguration.filter(SearchRow(objectType = "L'ASSOCIATION", objectSubType = "Historical"))
            val priceConfigurationValues = priceConfiguration.values.toList()
            assertEquals(1, priceConfigurationValues.size)
            assertEquals("*", priceConfigurationValues[0].objectType)
            assertEquals("*", priceConfigurationValues[0].objectSubType)
        }
    }
}