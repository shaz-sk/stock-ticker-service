package com.organization.stockTicker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator

@SpringBootApplication
@ComponentScan(nameGenerator = FullyQualifiedAnnotationBeanNameGenerator::class)
class StockTickerApplication

fun main(args: Array<String>) {
    SpringApplication.run(StockTickerApplication::class.java, *args)
//	runApplication<StockTickerApplication>(*args)
}
