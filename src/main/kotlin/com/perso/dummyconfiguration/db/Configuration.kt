package com.perso.dummyconfiguration.db

import javax.persistence.*

@Entity
@IdClass(ConfigurationId::class)
class Configuration(
        @Column(name = "customer_code") @Id val customerCode: String,
        @Column(name = "configuration_category") @Id val category: String,
        @Column(name = "configuration_type") @Id val type: String,
        @Column(name = "configuration_key") @Id val key: String,
        @Column(name = "configuration_value") @Convert(converter = ConfigurationObjectConverter::class) val value: ConfigurationObject = ConfigurationObject()
) {
    internal constructor() : this("", "", "", "")
}