package com.fiuba.asrekaf.service

import com.fiuba.asrekaf.model.User
import com.fiuba.asrekaf.api.UserCreation
import com.fiuba.asrekaf.api.UserLogin
import com.fiuba.asrekaf.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.concurrent.ThreadLocalRandom
import kotlin.streams.asSequence

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

    fun createUser(userData: UserCreation) =
        userRepository.save(userData.toUserEntity())

    private fun UserCreation.toUserEntity() = User(
        username = userName,
        password = password,
        tokenKey = generateTokenKey()
    )

    fun login(userId: Long, userData: UserLogin): ResponseEntity<User> =
        userRepository.findById(userId)
            .filter { it.password == userData.password && generateToken(it.tokenKey) == userData.token }
            .map { ResponseEntity.ok().body(it) }
            .orElse(ResponseEntity.notFound().build())

    companion object {
        private fun generateTokenKey(): String = ThreadLocalRandom.current()
                .ints(STRING_LENGTH, 0, charPool.size)
                .asSequence()
                .map(charPool::get)
                .joinToString("")

        private fun generateToken(tokenKey: String): String = ""

        private const val STRING_LENGTH = 40L
        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    }

}