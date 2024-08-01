package com.company.urlshortener

import io.restassured.RestAssured
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class UrlShortenerServiceIntegrationTest {
    @Test
    fun when_generateShortUrlAndShortUrlNonExisting_then_returnsShortUrl() {
        val randomUrl = "https://originalurl.com/" + Math.random()
        RestAssured.with().body("  \"originalUrl\": \"$randomUrl/\"\n")
            .`when`()
            .request(HTTP_METHOD_POST, URL_SHORTENER_ENDPOINT)
            .then()
            .statusCode(EXPECTED_STATUS_CODE_CREATED)
    }

    @Test
    fun when_getLongUrlForShortUrl_then_returnsOriginalUrl() {
        val randomUrl = "https://originalurl.com" + Math.random()
        val responseForPost = RestAssured.with().body("  \"originalUrl\": \"$randomUrl/\"\n")
            .`when`()
            .request(HTTP_METHOD_POST, URL_SHORTENER_ENDPOINT)

        val shortenedUrl = responseForPost.header(LOCATION_HEADER)
            .split("/".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()[POSITION_OF_URL]

        RestAssured.get(URL_SHORTENER_ENDPOINT + "/" + shortenedUrl).then().statusCode(
            EXPECTED_STATUS_CODE_OK
        )
    }

    companion object {
        const val HTTP_METHOD_POST: String = "POST"

        const val URL_SHORTENER_ENDPOINT: String = "/url"

        const val POSITION_OF_URL: Int = 4

        const val EXPECTED_STATUS_CODE_CREATED: Int = 201

        const val LOCATION_HEADER: String = "Location"

        const val EXPECTED_STATUS_CODE_OK: Int = 200
    }
}