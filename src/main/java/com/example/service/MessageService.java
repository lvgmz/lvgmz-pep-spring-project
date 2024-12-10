package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;


    // #3 Create Message
    public void addMessage(Message message){
        messageRepository.save(message);
    }

    public boolean checkMessage(Message message){
        if(message.getMessageText() == ""){
            return false;
        }
        if(message.getMessageText().length() > 255){
            return false;
        }
        
        return true;
    }

    // #4 get all messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    // #5 get message by Id
    public Message getMessageById(int messageId){
        return messageRepository.findByMessageId(messageId);
    }

    // #6 delete message by id
    @Transactional
    public void deleteByMessageId(int messageId){
        messageRepository.deleteMessage(messageId);
    }

    // #7 update message by id
    @Transactional
    public void updateMessageById(int messageId, String messageText){
        messageRepository.updateMessageTextByMessageId(messageId, messageText);
    }

    // #8 get messages by account id
    public List<Message> getMessagesByAccountId(int accountId){
        return messageRepository.findAllMessagesByPostedBy(accountId);
    }

}
