package com.fiuba.asrekaf.model.exception

class UserException(override val message: String, override val cause: Throwable? = null)
    : RuntimeException(message, cause)
