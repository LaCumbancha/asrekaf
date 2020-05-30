package com.fiuba.asrekaf.model.exception

class DatabaseException(override val message: String, override val cause: Throwable? = null)
    : RuntimeException(message, cause)
