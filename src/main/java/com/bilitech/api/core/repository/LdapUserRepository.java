package com.bilitech.api.core.repository;

import com.bilitech.api.core.entity.LdapUser;
import org.springframework.data.ldap.repository.LdapRepository;

public interface LdapUserRepository extends LdapRepository<LdapUser> {
 LdapUser findLdapUserByUsername(String username);

}
