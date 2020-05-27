package com.fiuba.asrekaf.controller

import com.fiuba.asrekaf.model.Journal
import com.fiuba.asrekaf.repository.JournalRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class JournalController(@Autowired private val journalRepository : JournalRepository) {
    //gets all journals
    @GetMapping("/journals")
    fun getAllJournals() : List<Journal> = journalRepository.findAll()

    //creates a journal
    @PostMapping("/journals")
    fun createJournal(@RequestBody journal : Journal) : Journal = journalRepository.save(journal)

    //gets a single journal
    @GetMapping("journals/{journalId}")
    fun getJournalById(@PathVariable journalId : Long) : ResponseEntity<Journal> =
        journalRepository.findById(journalId).map {
            ResponseEntity.ok(it)
        }.orElse(ResponseEntity.notFound().build())

    //updates a journal
    @PutMapping("/journals/{journalId}")
    fun updateJournal(@PathVariable journalId : Long, @RequestBody updatedJournal: Journal)
            : ResponseEntity<Journal> =
        journalRepository.findById(journalId).map{
            val newJournal = it.copy(title = updatedJournal.title, content =  updatedJournal.content)
            ResponseEntity.ok().body(journalRepository.save(newJournal))
        }.orElse(ResponseEntity.notFound().build())

    // deletes a journal
    @DeleteMapping("/journals/{journalId}")
    fun deleteJournal(@PathVariable journalId : Long) : ResponseEntity<Void> =
        journalRepository.findById(journalId).map{
            journalRepository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
}