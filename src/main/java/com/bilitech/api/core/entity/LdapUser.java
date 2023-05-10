package com.bilitech.api.core.entity;


import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(objectClasses = { "inetOrgPerson" })
@Data
public class LdapUser {
    @Id
    private Name dn;

    @Attribute(name = "cn")
    private String fullName;

    @Attribute(name="uid")
    private String username;
}
