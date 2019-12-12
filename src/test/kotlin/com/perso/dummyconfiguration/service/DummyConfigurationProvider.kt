package com.perso.dummyconfiguration.service

import com.perso.dummyconfiguration.db.Configuration
import com.perso.dummyconfiguration.model.ConfigurationUtils.Companion.DEFAULT_CODE
import com.perso.dummyconfiguration.model.computation.calibration.FooCalibration
import com.perso.dummyconfiguration.model.computation.calibration.FooCalibrationValue

internal class DummyConfigurationProvider : ConfigurationProvider {

    private val defaultPriceCalibration = FooCalibration(
            FooCalibrationValue("*", 42)
    )
    private val pvtPriceCalibration = FooCalibration(
            FooCalibrationValue("DARGAUD", 91),
            FooCalibrationValue("DELCOURT", 55)
    )

    companion object {
        const val PRIVAT_CODE = "PVT"
    }

    val configurationStore = mutableListOf(
            Configuration(DEFAULT_CODE, "REVENUE", "CALIBRATION", "PRICE", defaultPriceCalibration),
            Configuration(PRIVAT_CODE, "REVENUE", "CALIBRATION", "PRICE", pvtPriceCalibration)
    )

    override fun findAll(): List<Configuration> {
        return configurationStore
    }

    override fun findForCustomerAndDefaultByCategoryTypeAndKey(configuration: Configuration): List<Configuration> {
        return configurationStore.filter {
            (it.customerCode == DEFAULT_CODE || it.customerCode == configuration.customerCode)
                    && it.category == configuration.category
                    && it.type == configuration.type
                    && it.key == configuration.key
        }
    }

    override fun findById(configuration: Configuration): Configuration? {
        return configurationStore.firstOrNull {
            it.customerCode == DEFAULT_CODE
                    && it.category == configuration.category
                    && it.type == configuration.type
                    && it.key == configuration.key
        }
    }

    override fun insertOrUpdate(configuration: Configuration) {
        val existingConfigurationRow = findById(configuration)
        if (existingConfigurationRow != null) {
            val existingConfigurationRowIndex = configurationStore.indexOf(existingConfigurationRow)
            configurationStore[existingConfigurationRowIndex] = configuration
        } else {
            configurationStore.add(configuration)
        }
    }

    override fun delete(configuration: Configuration) {
        val existingConfigurationRow = findById(configuration)
        if (existingConfigurationRow != null) {
            val existingConfigurationRowIndex = configurationStore.indexOf(existingConfigurationRow)
            configurationStore.removeAt(existingConfigurationRowIndex)
        }
    }
}