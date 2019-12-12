package com.perso.dummyconfiguration.db

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.persistence.AttributeConverter

class ConfigurationObjectConverter : AttributeConverter<ConfigurationObject, String> {

    private val mapper = jacksonObjectMapper() // TODO use global mapper?

    override fun convertToDatabaseColumn(configuration: ConfigurationObject): String? {
        return mapper.writeValueAsString(configuration)
    }

    override fun convertToEntityAttribute(dbConfiguration: String?): ConfigurationObject {
       return mapper.readValue<ConfigurationObject>(dbConfiguration, ConfigurationObject::class.java)
    }
}