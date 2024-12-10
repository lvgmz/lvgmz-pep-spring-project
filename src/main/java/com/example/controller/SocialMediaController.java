package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;


    // #1 post new user registration
    @PostMapping("/register")
    public @ResponseBody ResponseEntity<Account> createAccount(@RequestBody Account account){
        if(accountService.doesAccountExist(account)){
            return ResponseEntity.status(409).body(null);
        }

        if(account.getPassword().length() <= 4){
            return ResponseEntity.status(409).body(null);
        }

        if(account.getUsername() == ""){
            return ResponseEntity.status(409).body(null);
        }

        accountService.addAccount(account);
        return ResponseEntity.status(200).body(account);       
    }


    // #2 process user logins
    @PostMapping("/login")
    public @ResponseBody ResponseEntity<Account> checkLogin(@RequestBody Account account){
        if(!accountService.doesAccountExist(account)){
            return ResponseEntity.status(401).body(null);
        }

        if(!accountService.checkLogin(account)){
            return ResponseEntity.status(401).body(null);
        }

        return ResponseEntity.status(200).body(accountService.getAccountByUsername(account));
    }


    // #3 create new messages
    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message){
        if(!messageService.checkMessage(message)){
            return ResponseEntity.status(400).body(null);
        }
        if(accountService.getAccountById(message.getPostedBy()) == null){
            return ResponseEntity.status(400).body(null);
        }
        
        messageService.addMessage(message);
        return ResponseEntity.status(200).body(message);
    }


    // #4 get all messages
    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }


    // #5 retrieve message by id
    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
    }


    // #6 delete message by id
    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<String> deleteMessageById(@PathVariable int messageId){
        
        if(messageService.getMessageById(messageId) == null){
            return ResponseEntity.status(200).body("");
        }

        messageService.deleteByMessageId(messageId);
        return ResponseEntity.status(200).body("1");
    }


    // #7 update message by id
    @PatchMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<String> updateMessageById(@RequestBody String messageText, @PathVariable int messageId){
        if(messageService.getMessageById(messageId) == null){
            return ResponseEntity.status(400).body("");
        }
        if(messageText.length() <= 19){ // size of '{"messageText": ""}' is 19 if messageText contents is empty
            return ResponseEntity.status(400).body("");
        }
        if(messageText.length() > 255){
            return ResponseEntity.status(400).body("");
        }

        messageService.updateMessageById(messageId, messageText);
        return ResponseEntity.status(200).body("1");
    }


    // #8 get all messages by user id
    @GetMapping("/accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int accountId){

        List<Message> msgs = messageService.getMessagesByAccountId(accountId);

        return ResponseEntity.status(200).body(msgs);
    }

}
