package com.perso.dummyconfiguration.db

import com.perso.dummyconfiguration.model.ConfigurationUtils.Companion.DEFAULT_CODE
import com.perso.dummyconfiguration.service.ConfigurationProvider
import org.springframework.stereotype.Component

@Component(value = "configurationProvider")
class ConfigurationDbProvider(
        private val configurationRepository: ConfigurationRepository
) : ConfigurationProvider {

    override fun findAll(): List<Configuration> {
        return configurationRepository.findAll().toList()
    }

    override fun findForCustomerAndDefaultByCategoryTypeAndKey(configuration: Configuration): List<Configuration> {
        return configurationRepository.findByCustomerCodeInAndCategoryAndTypeAndKey(listOf(DEFAULT_CODE, configuration.customerCode), configuration.category, configuration.type, configuration.key)
    }

    override fun findById(configuration: Configuration): Configuration? {
        return configurationRepository.findById(ConfigurationId(configuration.customerCode, configuration.category, configuration.type, configuration.key)).orElse(null)
    }

    override fun insertOrUpdate(configuration: Configuration) {
        configurationRepository.save(configuration)
    }

    override fun delete(configuration: Configuration) {
        configurationRepository.delete(configuration)
    }
}