package de.dkb.dkbly

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DkblyApplication

fun main(args: Array<String>) {
    runApplication<DkblyApplication>(*args)
}

