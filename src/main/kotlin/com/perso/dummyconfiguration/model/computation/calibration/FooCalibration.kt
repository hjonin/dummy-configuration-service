package com.perso.dummyconfiguration.model.computation.calibration

import com.perso.dummyconfiguration.db.ConfigurationObject
import com.perso.dummyconfiguration.model.ConfigurationDictionary
import com.perso.dummyconfiguration.model.SearchRow

class FooCalibration(
        vararg values: FooCalibrationValue
) : ConfigurationObject(), ConfigurationDictionary<FooCalibrationValue> {
    override val values = values.toSortedSet(compareBy { it.objectType })

    override fun filter(searchRow: SearchRow) {
        values.retainAll { it.match(FooCalibrationValue(searchRow.objectTypeOrDefault)) }
    }
}