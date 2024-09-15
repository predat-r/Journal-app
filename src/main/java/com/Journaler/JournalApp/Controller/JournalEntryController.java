package com.Journaler.JournalApp.Controller;

import com.Journaler.JournalApp.entity.JournalEntity;
import com.Journaler.JournalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;


    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntity newEntry) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            newEntry.setDate(LocalDateTime.now());
            journalEntryService.createEntry(newEntry, username);
            return new ResponseEntity<>(newEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity<?> getEntriesOfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return new ResponseEntity<>(journalEntryService.getEntriesOfUser(username), HttpStatus.OK);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return new ResponseEntity<>(journalEntryService.getEntryById(myId, username), HttpStatus.OK);

    }


    @DeleteMapping("/id/{myId}")
    public ResponseEntity<JournalEntity> deleteEntry(@PathVariable ObjectId myId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (journalEntryService.deleteEntrybyId(myId, username)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myId, @RequestBody JournalEntity updatedEntry) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        if (
                journalEntryService.updateJournalEntry(myId, username, updatedEntry)) {
            return new ResponseEntity<>(journalEntryService.getEntryById(myId,username), HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }

}
