package com.fiuba.asrekaf.service

import com.fiuba.asrekaf.api.UserApiKey
import com.fiuba.asrekaf.model.User
import com.fiuba.asrekaf.api.UserCreation
import com.fiuba.asrekaf.api.UserLogin
import com.fiuba.asrekaf.model.exception.DatabaseException
import com.fiuba.asrekaf.repository.UserRepository
import com.fiuba.asrekaf.utils.HashType.*
import com.fiuba.asrekaf.utils.Hasher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

    fun createUser(userData: UserCreation) =
        try {
            UserApiKey(userRepository.save(userData.toUserEntity()).apiKey)
        } catch (exc: Exception ) {
            throw DatabaseException("Coulnd't create user.", exc)
        }

    private fun UserCreation.toUserEntity() = User(
        username = username,
        password = Hasher.hash(password, SHA256),
        apiKey = generateApiKey(),
        code = code
    )

    fun login(userId: Long, userData: UserLogin): ResponseEntity<User> =
        userRepository.findById(userId)
            .filter { Hasher.verify(userData.password, it.password, SHA256) && generateToken(it.apiKey, it.code) == userData.token }
            .map { ResponseEntity.ok().body(it) }
            .orElse(ResponseEntity.notFound().build())

    companion object {
        private fun generateToken(apiKey: String, code: String): String {
            val dateTime = LocalDateTime.now()
            val timeKey = dateTime.format(DateTimeFormatter.ofPattern(tokenTimePattern))
            return Hasher.hash(code + apiKey + timeKey, MD5).toUpperCase().substring(0, 8)
        }

        private fun generateApiKey(): String = ThreadLocalRandom.current()
            .ints(STRING_LENGTH, 0, charPool.size)
            .asSequence()
            .map(charPool::get)
            .joinToString("")

        private const val STRING_LENGTH = 40L
        private const val tokenTimePattern = "YYYYMMddHHmm"
        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    }

}
