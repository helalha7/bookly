package com.dev.bookly.user.repositories;

import com.dev.bookly.user.domains.Account;

public interface AccountRepository {
    Account createAccount(Account account);
    int updateAccountInfo(Long userId, Account account);
}
