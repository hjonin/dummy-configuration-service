package com.perso.dummyconfiguration.model.computation.calibration

import com.perso.dummyconfiguration.model.ConfigurationRow
import java.io.Serializable

data class FooCalibrationValue(
        val objectType: String,
        val someCalibrationValue: Int? = null
) : ConfigurationRow, Serializable
