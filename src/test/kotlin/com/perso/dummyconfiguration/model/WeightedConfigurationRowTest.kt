package com.perso.dummyconfiguration.model

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class WeightedConfigurationRowTest {

    private data class DummyWeightedConfigurationRowWith2Indexes(
            @Index(1) val publisher: String,
            @Index(2) val genre: String,
            val dummyProperty: Int? = null
    ) : WeightedConfigurationRow

    private class DummyWeightedConfigurationRowWith3Indexes(
            @Index(1) val publisher: String,
            @Index(2) val genre: String,
            @Index(3) val year: String
    ) : WeightedConfigurationRow

    @Nested
    inner class TestGetWeight {
        @Test
        fun `test get weight when 2 indexes`() {
            val acr1 = DummyWeightedConfigurationRowWith2Indexes("*", "*")
            val acr2 = DummyWeightedConfigurationRowWith2Indexes("DELCOURT", "*")
            val acr3 = DummyWeightedConfigurationRowWith2Indexes("*", "Fantasy")
            val acr4 = DummyWeightedConfigurationRowWith2Indexes("DELCOURT", "Fantasy")
            assertEquals(0, acr1.getWeight())
            assertEquals(2, acr2.getWeight())
            assertEquals(4, acr3.getWeight())
            assertEquals(6, acr4.getWeight())
        }

        @Test
        fun `test get weight when 3 indexes`() {
            val acr1 = DummyWeightedConfigurationRowWith3Indexes("*", "*", "Fantasy")
            val acr2 = DummyWeightedConfigurationRowWith3Indexes("DELCOURT", "Fantasy", "*")
            assertTrue(acr1.getWeight() > acr2.getWeight())
        }
    }

    @Nested
    inner class TestSort {
        @Test
        fun `remove duplicates and sort`() {
            val acr1 = DummyWeightedConfigurationRowWith2Indexes("*", "*")
            val acr2 = DummyWeightedConfigurationRowWith2Indexes("DELCOURT", "*")
            val acr3 = DummyWeightedConfigurationRowWith2Indexes("DELCOURT", "Fantasy")
            val acr4 = DummyWeightedConfigurationRowWith2Indexes("*", "Fantasy")
            val acr5 = DummyWeightedConfigurationRowWith2Indexes("*", "Fantasy")
            val priceConfiguration = listOf(
                    acr1,
                    acr2,
                    acr3,
                    acr4,
                    acr5
            )
            assertArrayEquals(arrayOf(acr1, acr2, acr3, acr4, acr5), priceConfiguration.toTypedArray())
            val sortedPriceConfiguration = priceConfiguration.toSortedSet()
            assertArrayEquals(arrayOf(acr3, acr4, acr2, acr1), sortedPriceConfiguration.toTypedArray())
        }

        @Test
        fun `sort when no index`() {
            data class TestConfigurationNoIndex(
                    private val aircraftType: String
            ) : WeightedConfigurationRow

            val acr1 = TestConfigurationNoIndex("*")
            val acr2 = TestConfigurationNoIndex("DELCOURT")
            val priceConfiguration = listOf(
                    acr1,
                    acr2
            )
            assertArrayEquals(arrayOf(acr1, acr2), priceConfiguration.toTypedArray())
            val sortedPriceConfiguration = priceConfiguration.toSortedSet()
            assertArrayEquals(arrayOf(acr1, acr2), sortedPriceConfiguration.toTypedArray())
        }

        @Test
        fun `sort when index not comparable`() {
            data class Aircraft(val type: String)

            data class TestConfigurationIndexNotComparable(
                    @Index val aircraft: Aircraft
            ) : WeightedConfigurationRow

            val acr1 = TestConfigurationIndexNotComparable(Aircraft("*"))
            val acr2 = TestConfigurationIndexNotComparable(Aircraft("DELCOURT"))
            val priceConfiguration = listOf(
                    acr1,
                    acr2
            )
            assertArrayEquals(arrayOf(acr1, acr2), priceConfiguration.toTypedArray())
            assertThrows(ClassCastException::class.java) {
                priceConfiguration.toSortedSet()
            }
        }
    }
}