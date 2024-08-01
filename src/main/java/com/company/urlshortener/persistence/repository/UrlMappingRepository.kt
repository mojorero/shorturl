package com.company.urlshortener.persistence.repository

import com.company.urlshortener.persistence.model.UrlMapping
import org.springframework.data.jpa.repository.JpaRepository

interface UrlMappingRepository : JpaRepository<UrlMapping?, Long?> {
    fun findByShortUrl(shortUrl: String?): UrlMapping?
    fun findByOriginalUrl(originalUrl: String?): UrlMapping?
}
