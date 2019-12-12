package com.perso.dummyconfiguration.service

import com.perso.dummyconfiguration.db.ConfigurationObject
import com.perso.dummyconfiguration.model.computation.calibration.FooCalibration
import com.perso.dummyconfiguration.service.DummyConfigurationProvider.Companion.PRIVAT_CODE
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ConfigurationServiceTest {

    companion object {
        private const val UNKNOWN_CODE = "XXX"
        private const val UNKNOWN_CATEGORY = "XXX"
        private const val UNKNOWN_TYPE = "XXX"
        private const val UNKNOWN_KEY = "XXX"
    }

    private val configurationService: ConfigurationService = ConfigurationService(DummyConfigurationProvider())

    @Nested
    inner class GetConfiguration {
        @Test
        fun `get configuration nominal`() {
            val priceConfiguration = configurationService.getConfiguration(PRIVAT_CODE, "REVENUE", "CALIBRATION", "PRICE")
            assertEquals(2, (priceConfiguration as FooCalibration).values.size)
            val priceConfigurationValues = priceConfiguration.values.toList()
            assertEquals("DARGAUD", priceConfigurationValues[0].objectType)
            assertEquals(91, priceConfigurationValues[0].someCalibrationValue)
            assertEquals("DELCOURT", priceConfigurationValues[1].objectType)
            assertEquals(55, priceConfigurationValues[1].someCalibrationValue)
        }

        @Test
        fun `get configuration for unknown customer (ie default)`() {
            val priceConfiguration = configurationService.getConfiguration(UNKNOWN_CODE, "REVENUE", "CALIBRATION", "PRICE")
            assertEquals(1, (priceConfiguration as FooCalibration).values.size)
            val priceConfigurationValues = priceConfiguration.values.toList()
            assertEquals("*", priceConfigurationValues[0].objectType)
            assertEquals(42, priceConfigurationValues[0].someCalibrationValue)
        }

        @Test
        fun `get configuration for unknown customer and key (ie no default)`() {
            val priceConfiguration = configurationService.getConfiguration(UNKNOWN_CODE, "REVENUE", "CALIBRATION", UNKNOWN_KEY)
            assertNull(priceConfiguration)
        }
    }

    @Nested
    inner class InsertOrUpdateConfiguration {
        @Test
        fun `new configuration is inserted`() {
            val configurationStore = (configurationService.configurationProvider as DummyConfigurationProvider).configurationStore
            assertEquals(2, configurationStore.size)
            configurationService.insertOrUpdateConfiguration(PRIVAT_CODE, UNKNOWN_CATEGORY, "CALIBRATION", "PRICE", ConfigurationObject())
            assertEquals(3, configurationStore.size)
            configurationService.insertOrUpdateConfiguration(PRIVAT_CODE, "REVENUE", UNKNOWN_TYPE, "PRICE", ConfigurationObject())
            assertEquals(4, configurationStore.size)
            configurationService.insertOrUpdateConfiguration(PRIVAT_CODE, "REVENUE", "CALIBRATION", UNKNOWN_KEY, ConfigurationObject())
            assertEquals(5, configurationStore.size)
        }

        @Test
        fun `existing configuration is updated`() {
            val configurationStore = (configurationService.configurationProvider as DummyConfigurationProvider).configurationStore
            assertEquals(2, configurationStore.size)
            configurationService.insertOrUpdateConfiguration(PRIVAT_CODE, "REVENUE", "CALIBRATION", "PRICE", ConfigurationObject())
            assertEquals(2, configurationStore.size)
        }
    }

    @Test
    fun deleteConfiguration() {
        val configurationStore = (configurationService.configurationProvider as DummyConfigurationProvider).configurationStore
        assertEquals(2, configurationStore.size)
        configurationService.deleteConfiguration(PRIVAT_CODE, "REVENUE", "CALIBRATION", "PRICE")
        assertEquals(1, configurationStore.size)
    }

    @Test
    fun listPaths() {
        val paths = configurationService.listPaths()
        assertEquals(1, paths.size)
        assertEquals("revenue/calibration/price", paths[0])
    }
}