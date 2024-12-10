package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // #1 register user
    public boolean doesAccountExist(Account account){
        if (accountRepository.findByUsername(account.getUsername()) == null){
            return false;
        }
        return true;
    }

    public void addAccount(Account account){
        accountRepository.save(account);
    }

    // #2 login check
    public boolean checkLogin(Account account){
        Account check = accountRepository.findByUsername(account.getUsername()); 
        if (check == null){
            return false;
        }

        if(!check.getPassword().equals(account.getPassword())){
            return false;
        }
        return true;
    }
    public Account getAccountByUsername(Account account){
        return accountRepository.findByUsername(account.getUsername());
    }

    // #3
    public Account getAccountById(int id){
        return accountRepository.findByAccountId(id);
    }

}
