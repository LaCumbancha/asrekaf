package com.fiuba.asrekaf.api

data class UserCreation(val username: String, val password: String)

data class UserApiKey(val apiKey: String)

data class UserLogin(val username: String, val password: String, val token: String)
