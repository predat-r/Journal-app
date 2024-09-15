package com.Journaler.JournalApp.repo;
import com.Journaler.JournalApp.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUsername(String username);

    void deleteByUsername(String name);
}
