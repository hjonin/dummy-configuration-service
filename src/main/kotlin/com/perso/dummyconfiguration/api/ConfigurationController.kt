package com.perso.dummyconfiguration.api

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.perso.dummyconfiguration.api.response.FailResponse
import com.perso.dummyconfiguration.api.response.Response
import com.perso.dummyconfiguration.api.response.SuccessResponse
import com.perso.dummyconfiguration.db.ConfigurationObject
import com.perso.dummyconfiguration.model.ConfigurationDictionary
import com.perso.dummyconfiguration.model.ConfigurationUtils.Companion.DEFAULT_CODE
import com.perso.dummyconfiguration.model.SearchRow
import com.perso.dummyconfiguration.service.ConfigurationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/configuration")
class ConfigurationController(
        private val configurationService: ConfigurationService,
        private val objectMapper: ObjectMapper
) {

    @GetMapping("/{configurationCategory}/{configurationType}/{configurationKey}")
    fun getConfiguration(
            @PathVariable configurationCategory: String,
            @PathVariable configurationType: String,
            @PathVariable configurationKey: String,
            @RequestParam customerCode: String,
            @RequestParam objectType: String?,
            @RequestParam objectSubType: String?
    ): Response {
        val configuration = configurationService.getConfiguration(customerCode, configurationCategory.toUpperCase(), configurationType.toUpperCase(), configurationKey.toUpperCase())
        if (configuration is ConfigurationDictionary<*>) {
            configuration.filter(SearchRow(
                    objectType = objectType,
                    objectSubType = objectSubType
            ))
        }
        return SuccessResponse(configuration)
    }

    @PostMapping("/{configurationCategory}/{configurationType}/{configurationKey}")
    fun postConfiguration(
            @PathVariable configurationCategory: String,
            @PathVariable configurationType: String,
            @PathVariable configurationKey: String,
            @RequestParam customerCode: String,
            @RequestBody configurationObjectNode: JsonNode
    ): Response {
        if (customerCode == DEFAULT_CODE) {
            return FailResponse(mapOf("customerCode" to "Not allowed to update default configuration."))
        }
        (configurationObjectNode as ObjectNode).put("type", "${configurationKey}_${configurationType}".toUpperCase())
        val configurationObject = objectMapper.treeToValue(configurationObjectNode, ConfigurationObject::class.java)
        configurationService.insertOrUpdateConfiguration(customerCode, configurationCategory.toUpperCase(), configurationType.toUpperCase(), configurationKey.toUpperCase(), configurationObject)
        return SuccessResponse(configurationObject)
    }

    @DeleteMapping("/{configurationCategory}/{configurationType}/{configurationKey}")
    fun deleteConfiguration(
            @PathVariable configurationCategory: String,
            @PathVariable configurationType: String,
            @PathVariable configurationKey: String,
            @RequestParam customerCode: String
    ): Response {
        if (customerCode == DEFAULT_CODE) {
            return FailResponse(mapOf("customerCode" to "Not allowed to update default configuration."))
        }
        configurationService.deleteConfiguration(customerCode, configurationCategory.toUpperCase(), configurationType.toUpperCase(), configurationKey.toUpperCase())
        return SuccessResponse()
    }

    @GetMapping("/paths")
    fun getConfigurationPaths(
    ): Response {
        return SuccessResponse(configurationService.listPaths())
    }
}