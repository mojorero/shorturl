package com.company.urlshortener


import com.company.urlshortener.exception.ConflictException
import com.company.urlshortener.exception.UrlNotFoundException
import com.company.urlshortener.persistence.model.UrlMapping

import com.company.urlshortener.persistence.repository.UrlMappingRepository
import com.company.urlshortener.services.UrlShortenerService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

internal class UrlShortenerServiceTest {
    @Mock
    private val urlMappingRepository: UrlMappingRepository? = null

    @InjectMocks
    private val urlShortenerService: UrlShortenerService? = null

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun generateShortUrl_throwsConflictException_whenUrlAlreadyExists() {
        val originalUrl = "https://dein-antrag.dkb.de/girokonto-start/" + Math.random()

        val urlMapping = UrlMapping("short.ly/123456", "https://www.example.com/long-url-path")

        Mockito.`when`(urlMappingRepository!!.findByOriginalUrl(originalUrl))
            .thenReturn(urlMapping)

        Assertions.assertThrows(ConflictException::class.java) {
            urlShortenerService!!.generateShortUrl(originalUrl)
        }
    }

    @Test
    fun generateShortUrl_returnsShortUrl_whenUrlDoesNotExist() {
        val originalUrl = "https://dein-antrag.dkb.de/girokonto-start/" + Math.random()
        Mockito.`when`(urlMappingRepository!!.findByOriginalUrl(originalUrl))
            .thenReturn(null)

        val shortUrl = urlShortenerService!!.generateShortUrl(originalUrl)

        Assertions.assertEquals(6, shortUrl.length)
    }

    @Test
    fun longUrlForShortUrl_returnsOriginalUrl_whenShortUrlExists() {

        val shortUrl = "shortUrl"
        val originalUrl = "http://example.com"
        val urlMapping = UrlMapping()
        urlMapping.originalUrl = originalUrl
        Mockito.`when`(urlMappingRepository!!.findByShortUrl(shortUrl))
            .thenReturn(urlMapping)
        val foundUrl = urlMappingRepository.findByShortUrl(shortUrl)

        Assertions.assertEquals(originalUrl, foundUrl?.originalUrl)
    }


    @Test
    fun longUrlForShortUrl_throwsUrlNotFoundException_whenShortUrlDoesNotExist() {
        val shortUrl = "shortUrl"
        Mockito.`when`(urlMappingRepository!!.findByShortUrl(shortUrl))
            .thenReturn(null)

        Assertions.assertThrows(UrlNotFoundException::class.java) {
            urlShortenerService!!.getLongUrlForShortUrl(shortUrl)
        }
    }
}