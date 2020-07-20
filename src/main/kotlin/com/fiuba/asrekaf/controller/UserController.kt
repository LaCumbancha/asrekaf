package com.fiuba.asrekaf.controller

import com.fiuba.asrekaf.api.CommonMessage
import com.fiuba.asrekaf.api.UserApiKey
import com.fiuba.asrekaf.api.UserCreation
import com.fiuba.asrekaf.api.UserLogin
import com.fiuba.asrekaf.model.User
import com.fiuba.asrekaf.model.exception.DatabaseException
import com.fiuba.asrekaf.model.exception.UserException
import com.fiuba.asrekaf.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@Controller
@RestController
@RequestMapping("/asrekaf")
class UserController(@Autowired private val userService: UserService) {

    @ExceptionHandler(value = [(DatabaseException::class)])
    fun dbExceptionHandler(exc: DatabaseException): ResponseEntity<CommonMessage> =
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonMessage(exc.message))

    @ExceptionHandler(value = [(UserException::class)])
    fun userExceptionHandler(exc: UserException): ResponseEntity<CommonMessage> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(CommonMessage(exc.message))

    // Endpoint for creating a user.
    @PostMapping("/users")
    fun createUser(@Valid @RequestBody userData: UserCreation): UserApiKey =
        userService.createUser(userData)

    // User login
    @PutMapping("/users/{userId}")
    fun updateJournal(@PathVariable userId: Long, @Valid @RequestBody userData: UserLogin): ResponseEntity<CommonMessage> =
        userService.login(userId, userData)

}
