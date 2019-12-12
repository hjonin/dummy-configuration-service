package com.perso.dummyconfiguration.service

import com.perso.dummyconfiguration.db.Configuration

interface ConfigurationProvider {
    fun findAll(): List<Configuration>
    fun findForCustomerAndDefaultByCategoryTypeAndKey(configuration: Configuration): List<Configuration>
    fun findById(configuration: Configuration): Configuration?
    fun insertOrUpdate(configuration: Configuration)
    fun delete(configuration: Configuration)
}