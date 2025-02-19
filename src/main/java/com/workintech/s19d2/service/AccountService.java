package com.workintech.s19d2.service;

import com.workintech.s19d2.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account save(Account account);
    // You can add other CRUD methods as needed.
}
