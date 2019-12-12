package com.perso.dummyconfiguration.model

import java.lang.IllegalArgumentException
import java.lang.reflect.Field

interface ConfigurationRow {

    companion object {
        const val ANY_VALUE = "*"
    }

    fun getIndexFields(clazz: Class<*>): List<Field> {
        return clazz.declaredFields.filter { it.isAnnotationPresent(Index::class.java) }
    }

    fun getFieldValue(any: Any, field: Field): Any? =
            field.apply { isAccessible = true }.get(any)

    fun fieldIsBlank(field: Field) = getFieldValue(this, field) == ANY_VALUE // TODO or empty or null?

    private fun fieldMatch(it: Field, other: ConfigurationRow): Boolean {
        val thisFieldValue = getFieldValue(this, it)
        val otherFieldValue = getFieldValue(other, it)
        return thisFieldValue == otherFieldValue
                || fieldIsBlank(it)
                || other.fieldIsBlank(it)
    }

    fun match(other: ConfigurationRow): Boolean {
        if (this::class != other::class) {
            throw IllegalArgumentException("Passed object of invalid type")
        }
        getIndexFields(javaClass).forEach {
            if (!(fieldMatch(it, other)))
                return false
        }
        return true
    }
}