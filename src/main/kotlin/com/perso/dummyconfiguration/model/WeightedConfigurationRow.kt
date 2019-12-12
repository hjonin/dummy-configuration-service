package com.perso.dummyconfiguration.model

import com.fasterxml.jackson.annotation.JsonIgnore
import kotlin.math.pow

interface WeightedConfigurationRow : ConfigurationRow, Comparable<WeightedConfigurationRow> {

    @JsonIgnore
    fun getWeight(): Int {
        var weight = 0.0
        getIndexFields(javaClass).forEach {
            if (!fieldIsBlank(it)) weight += 2.0.pow(it.getAnnotation(Index::class.java).weight)
        }
        return weight.toInt()
    }

    private fun getIndexFieldComparisonSelectors(clazz: Class<out WeightedConfigurationRow>): Array<(WeightedConfigurationRow) -> Comparable<*>> {
        return getIndexFields(clazz)
                .map { field ->
                    { row: WeightedConfigurationRow -> getFieldValue(row, field) as Comparable<*> }
                }.toTypedArray()
    }

    override fun compareTo(other: WeightedConfigurationRow): Int {
        val indexFieldComparisonSelectors = getIndexFieldComparisonSelectors(javaClass)
        return if (indexFieldComparisonSelectors.isNotEmpty()) {
            compareBy(
                    { configuration -> 1 - configuration.getWeight() },
                    *indexFieldComparisonSelectors
            ).compare(this, other)
        } else {
            1 // TODO ?
        }
    }
}