package com.bilitech.api.core.repository;

import com.bilitech.api.core.entity.LdapUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LdapUserRepositoryTest {

    @Autowired
    LdapUserRepository ldapUserRepository;
    @Test
    void findLdapUserByUsername() {
        LdapUser ldapUser = ldapUserRepository.findLdapUserByUsername("yili");
        System.out.println(ldapUser.toString());
    }
}