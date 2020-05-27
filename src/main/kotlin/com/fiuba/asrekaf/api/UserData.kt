package com.fiuba.asrekaf.api

data class UserCreation(val userName: String, val password: String)

data class UserLogin(val userName: String, val password: String, val token: String)
