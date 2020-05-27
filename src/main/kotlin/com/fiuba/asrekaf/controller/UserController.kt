package com.fiuba.asrekaf.controller

import com.fiuba.asrekaf.api.UserApiKey
import com.fiuba.asrekaf.model.User
import com.fiuba.asrekaf.api.UserCreation
import com.fiuba.asrekaf.api.UserLogin
import com.fiuba.asrekaf.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/asrekaf")
class UserController(@Autowired private val userService: UserService) {

    // Endpoint for creating a user.
    @PostMapping("/users")
    fun createUser(@Valid @RequestBody userData: UserCreation): UserApiKey =
        userService.createUser(userData)

    // User login
    @PutMapping("/users/{userId}")
    fun updateJournal(@PathVariable userId: Long, @Valid @RequestBody userData: UserLogin): ResponseEntity<User> =
        userService.login(userId, userData)

}