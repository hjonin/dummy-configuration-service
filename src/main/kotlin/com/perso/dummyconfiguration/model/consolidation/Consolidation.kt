package com.perso.dummyconfiguration.model.consolidation

import com.perso.dummyconfiguration.db.ConfigurationObject

data class Consolidation(
        val consolidatedValue: String,
        val sources: List<ConsolidationSource>,
        val conditions: List<String>
) : ConfigurationObject()