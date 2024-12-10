package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
    List<Message> findAll();
    Message findByMessageId(int messageId);

    @Modifying
    @Query(value="DELETE FROM message WHERE message.messageID = :messageId", nativeQuery = true)
    void deleteMessage(@Param("messageId")int messageId);

    @Modifying
    @Query(value="UPDATE message SET message.messageText = :messageText WHERE message.messageId = :messageId", nativeQuery = true)
    void updateMessageTextByMessageId(@Param("messageId")int messageId, @Param("messageText")String messageText);

    List<Message> findAllMessagesByPostedBy(int accountId);
}
