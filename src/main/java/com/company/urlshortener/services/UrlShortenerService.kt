package com.company.urlshortener.services


import com.company.urlshortener.exception.ConflictException
import com.company.urlshortener.exception.UrlNotFoundException
import com.company.urlshortener.persistence.model.UrlMapping
import com.company.urlshortener.persistence.repository.UrlMappingRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*

@Service
class UrlShortenerService(private val urlMappingRepository: UrlMappingRepository) {
    @Value("\${base.url}")
    private val baseUrl: String? = null

    fun generateShortUrl(originalUrl: String?): String {
        if (originalUrl == null) {
            throw UrlNotFoundException("Original URL not given")
        }

        if (urlMappingRepository.findByOriginalUrl(originalUrl) != null) {
            throw ConflictException(
                "The URL " + originalUrl +
                        " could not be created because it is already present in the system."
            )
        }

        val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val shortUrl = StringBuilder()

        val random: Random = SecureRandom()
        for (i in 0..5) {
            shortUrl.append(characters[random.nextInt(characters.length)])
        }

        val urlMapping = UrlMapping()
        urlMapping.shortUrl = shortUrl.toString()
        urlMapping.originalUrl = originalUrl
        urlMappingRepository.save(urlMapping)
        return shortUrl.toString()
    }

    fun getLongUrlForShortUrl(shortUrl: String?): String {
        val foundUrl = urlMappingRepository.findByShortUrl(shortUrl)
        if (foundUrl == null || foundUrl.originalUrl == null) {
            throw UrlNotFoundException("Short URL not found")
        }
        return foundUrl.originalUrl!!
    }
}
