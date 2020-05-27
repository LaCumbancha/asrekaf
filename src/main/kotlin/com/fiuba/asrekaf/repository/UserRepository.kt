package com.fiuba.asrekaf.repository

import com.fiuba.asrekaf.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>
