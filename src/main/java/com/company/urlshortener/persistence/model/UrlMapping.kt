package com.company.urlshortener.persistence.model

import jakarta.persistence.*

@Entity
class UrlMapping(

    @Column(nullable = false, unique = true)
    var originalUrl: String? = null,

    @Column(nullable = false, unique = true)
    var shortUrl: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)
