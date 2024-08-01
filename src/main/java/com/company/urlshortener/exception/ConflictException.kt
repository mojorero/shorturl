package com.company.urlshortener.exception

/**
 * Exception to manage HTTP Status 409.
 */
class ConflictException(message: String?) : RuntimeException(message)
