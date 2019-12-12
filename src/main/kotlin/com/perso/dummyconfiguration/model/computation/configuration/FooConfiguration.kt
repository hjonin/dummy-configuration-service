package com.perso.dummyconfiguration.model.computation.configuration

import com.perso.dummyconfiguration.db.ConfigurationObject
import com.perso.dummyconfiguration.model.ConfigurationDictionary
import com.perso.dummyconfiguration.model.SearchRow

class FooConfiguration(
        vararg values: FooConfigurationValue
) : ConfigurationObject(), ConfigurationDictionary<FooConfigurationValue> {
    override val values = values.toSortedSet()

    override fun filter(searchRow: SearchRow) {
        values.retainAll { it.match(FooConfigurationValue(searchRow.objectTypeOrDefault, searchRow.objectSubTypeOrDefault)) }
    }
}