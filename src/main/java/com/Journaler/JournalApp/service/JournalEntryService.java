package com.Journaler.JournalApp.service;

import com.Journaler.JournalApp.entity.JournalEntity;
import com.Journaler.JournalApp.entity.UserEntity;
import com.Journaler.JournalApp.repo.JournalRepo;
import com.Journaler.JournalApp.repo.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalRepo journalRepo;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;


    public void createEntry(JournalEntity newEntry, String username) {
        UserEntity user = userService.findByusername(username);
        if (user != null) {
            newEntry.setDate(LocalDateTime.now());
            journalRepo.save(newEntry);
            user.getJournals().add(newEntry);
            userService.saveUser(user);
        }

    }

    public List<JournalEntity> getAllEntries() {
        return journalRepo.findAll();
    }

    public List<JournalEntity> getEntriesOfUser(String username) {
        UserEntity user = userService.findByusername(username);
        return user.getJournals();
    }

    public Optional<JournalEntity> getEntryById(ObjectId id, String username) {

        List<JournalEntity> userEntries = getEntriesOfUser(username);
        if (!userEntries.isEmpty()) {
            return userEntries.stream().filter(x -> x.getId().equals(id)).findFirst();
        }
        return null;
    }

    public boolean deleteEntrybyId(ObjectId id, String username) {

        try {
            UserEntity user = userService.findByusername(username);
            List<JournalEntity> entries = user.getJournals();
            if (entries != null) {
                boolean isRemoved = entries.removeIf(x -> x.getId().equals(id));
                if (isRemoved) {
                    journalRepo.deleteById(id);
                    userService.saveUser(user);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateJournalEntry(ObjectId id, String username, JournalEntity updatedEntity) {
        UserEntity user = userService.findByusername(username);
        List<JournalEntity> entries = user.getJournals();
        if (entries != null) {
            JournalEntity old = entries.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
            if (old != null) {
                old.setTitle(updatedEntity.getTitle() != null && !updatedEntity.getTitle().equals(old.getTitle()) ? updatedEntity.getTitle() : old.getTitle());
                old.setContent(updatedEntity.getContent() != null && !updatedEntity.getContent().equals(old.getContent()) ? updatedEntity.getContent() : old.getContent());
            }
            journalRepo.save(old);
            userService.saveUser(user);
            return true;
        }
        return false;

    }
}
