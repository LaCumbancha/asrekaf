package com.fiuba.asrekaf.repository

import com.fiuba.asrekaf.model.Journal
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JournalRepository : JpaRepository<Journal, Long> {
}