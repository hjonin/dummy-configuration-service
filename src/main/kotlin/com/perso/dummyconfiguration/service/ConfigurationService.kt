package com.perso.dummyconfiguration.service

import com.perso.dummyconfiguration.db.Configuration
import com.perso.dummyconfiguration.db.ConfigurationObject
import com.perso.dummyconfiguration.model.ConfigurationUtils.Companion.DEFAULT_CODE
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class ConfigurationService(
        @Qualifier("configurationProvider") val configurationProvider: ConfigurationProvider
) {

    private fun getAllConfiguration(customerCode: String, category: String, type: String, key: String): List<Configuration> {
        return configurationProvider.findForCustomerAndDefaultByCategoryTypeAndKey(Configuration(customerCode, category, type, key))
    }

    private fun getCustomerOrDefaultConfiguration(customerCode: String, category: String, type: String, key: String): Configuration? {
        val allConfiguration = getAllConfiguration(customerCode, category, type, key)
        val customerConfiguration = allConfiguration
                .firstOrNull { it.customerCode == customerCode }
        return customerConfiguration ?: allConfiguration.firstOrNull { it.customerCode == DEFAULT_CODE }
    }

    fun getConfiguration(customerCode: String, category: String, type: String, key: String): ConfigurationObject? {
        return getCustomerOrDefaultConfiguration(customerCode, category, type, key)?.value
    }

    fun insertOrUpdateConfiguration(customerCode: String, category: String, type: String, key: String, configurationObject: ConfigurationObject) {
        configurationProvider.insertOrUpdate(Configuration(customerCode, category, type, key, configurationObject))
    }

    fun deleteConfiguration(customerCode: String, category: String, type: String, key: String) {
        configurationProvider.delete(Configuration(customerCode, category, type, key))
    }

    fun listPaths(): List<String> {
        return configurationProvider.findAll()
                .distinctBy { Triple(it.category, it.type, it.key) }
                .map { "${it.category}/${it.type}/${it.key}".toLowerCase() }
    }
}