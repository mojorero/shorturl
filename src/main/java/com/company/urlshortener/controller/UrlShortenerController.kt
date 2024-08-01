package com.company.urlshortener.controller


import com.company.urlshortener.services.UrlShortenerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
class UrlShortenerController(private val urlShortenerService: UrlShortenerService) {
    @PostMapping("/url")
    fun generateShortUrl(@RequestBody longUrl: String?): ResponseEntity<Any> {
        val shortUrl = urlShortenerService.generateShortUrl(longUrl)
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{shortUrl}")
            .buildAndExpand(shortUrl)
            .toUri()
        return ResponseEntity.created(location).build()
    }

    @GetMapping("/url/{shortUrl}")
    fun getOriginalUrl(@PathVariable shortUrl: String?): String {
        return urlShortenerService.getLongUrlForShortUrl(shortUrl)
    }
}
