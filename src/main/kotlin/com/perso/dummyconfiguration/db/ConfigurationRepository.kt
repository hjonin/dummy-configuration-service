package com.perso.dummyconfiguration.db

import org.springframework.data.repository.CrudRepository

interface ConfigurationRepository: CrudRepository<Configuration, ConfigurationId> {
    fun findByCustomerCodeInAndCategoryAndTypeAndKey(customerCodes: List<String>, category: String, type: String, key: String): List<Configuration>
}