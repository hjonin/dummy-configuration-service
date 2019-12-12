package com.perso.dummyconfiguration.model

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Index(val weight: Int = 0)