package com.perso.dummyconfiguration.db

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.perso.dummyconfiguration.model.computation.calibration.FooCalibration
import com.perso.dummyconfiguration.model.computation.configuration.FooConfiguration
import com.perso.dummyconfiguration.model.computation.properties.FooProperties
import com.perso.dummyconfiguration.model.consolidation.Consolidation
import java.io.Serializable

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes( // TODO constants?
        JsonSubTypes.Type(value = FooCalibration::class, name = "PRICE_CALIBRATION"),
        JsonSubTypes.Type(value = FooConfiguration::class, name = "PRICE_CONFIGURATION"),
        JsonSubTypes.Type(value = FooProperties::class, name = "PRICE_PROPERTIES"),
        JsonSubTypes.Type(value = Consolidation::class, name = "CONSOLIDATION")
)
open class ConfigurationObject : Serializable