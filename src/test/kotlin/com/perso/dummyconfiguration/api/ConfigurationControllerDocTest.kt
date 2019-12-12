package com.perso.dummyconfiguration.api

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.perso.dummyconfiguration.model.computation.configuration.FooConfiguration
import com.perso.dummyconfiguration.model.computation.configuration.FooConfigurationValue
import com.perso.dummyconfiguration.service.ConfigurationService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.request.RequestDocumentation.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ConfigurationController::class)
@AutoConfigureRestDocs
internal class ConfigurationControllerDocTest {

    private val priceConfiguration = FooConfiguration(
            FooConfigurationValue("*", "*", "red")
    )

    private val priceConfigurationDescription: List<FieldDescriptor> = listOf(
            fieldWithPath("objectType").description("The comic book publisher"),
            fieldWithPath("objectSubType").description("The comic book genre"),
            fieldWithPath("someConfigurationValue").description("The book cover color")
    )

    private val customerCodeDescription = parameterWithName("customerCode").description("The customer code")

    private val mapper = jacksonObjectMapper()

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var configurationService: ConfigurationService

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun getPriceConfiguration() {
        given(configurationService.getConfiguration("PVT", "REVENUE", "CONFIGURATION", "PRICE"))
                .willReturn(priceConfiguration)

        mockMvc.perform(get("/configuration/{configurationCategory}/{configurationType}/{configurationKey}?customerCode=PVT", "revenue", "configuration", "price"))
                .andExpect(status().isOk)
                .andDo(document("get-price-configuration",
                        pathParameters(
                                parameterWithName("configurationCategory").description("The configuration category (currently 'revenue'). Possible values are: TODO"),
                                parameterWithName("configurationType").description("The configuration type (currently 'configuration'). Possible values are: TODO"),
                                parameterWithName("configurationKey").description("The configuration key (currently 'price'). Possible values are: TODO")
                        ),
                        requestParameters(
                                customerCodeDescription,
                                parameterWithName("publisher").description("The comic book publisher to search configuration for").optional(),
                                parameterWithName("genre").description("The comic book genre to search configuration for").optional()
                        ),
                        responseFields(
                                fieldWithPath("data.values.[]").description("PRICE configuration"))
                                .andWithPrefix("data.values.[].", priceConfigurationDescription)
                                .and(fieldWithPath("status").ignored())
                ))
    }

    @Test
    fun postPriceConfiguration() {
        val priceConfiguration = FooConfiguration(
                FooConfigurationValue("*", "*", "red")
        )

        mockMvc.perform(post("/configuration/{configurationCategory}/{configurationType}/{configurationKey}?customerCode=PVT", "revenue", "configuration", "price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(priceConfiguration)))
                .andExpect(status().isOk)
                .andDo(document("post-price-configuration",
                        pathParameters(
                                parameterWithName("configurationCategory").description("The configuration category (currently 'revenue'). Possible values are: TODO"),
                                parameterWithName("configurationType").description("The configuration type (currently 'configuration'). Possible values are: TODO"),
                                parameterWithName("configurationKey").description("The configuration key (currently 'price'). Possible values are: TODO")
                        ),
                        requestParameters(
                                customerCodeDescription
                        ),
                        requestFields(
                                fieldWithPath("values.[]").description("PRICE configuration"))
                                .andWithPrefix("values.[].", priceConfigurationDescription)
                                .and(fieldWithPath("type").ignored())
                ))
    }

    @Test
    fun deletePriceConfiguration() {
        mockMvc.perform(delete("/configuration/{configurationCategory}/{configurationType}/{configurationKey}?customerCode=OPA", "revenue", "configuration", "price"))
                .andExpect(status().isOk)
                .andDo(document("delete-price-configuration",
                        pathParameters(
                                parameterWithName("configurationCategory").description("The configuration category (currently 'revenue'). Possible values are: TODO"),
                                parameterWithName("configurationType").description("The configuration type (currently 'configuration'). Possible values are: TODO"),
                                parameterWithName("configurationKey").description("The configuration key (currently 'price'). Possible values are: TODO")
                        ),
                        requestParameters(
                                customerCodeDescription
                        )
                ))
    }
}