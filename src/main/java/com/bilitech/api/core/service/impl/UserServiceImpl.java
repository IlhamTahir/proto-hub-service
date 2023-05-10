package com.bilitech.api.core.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bilitech.api.core.config.SecurityConfig;
import com.bilitech.api.core.dto.*;
import com.bilitech.api.core.entity.LdapUser;
import com.bilitech.api.core.entity.Role;
import com.bilitech.api.core.entity.User;
import com.bilitech.api.core.exception.BizException;
import com.bilitech.api.core.exception.ExceptionType;
import com.bilitech.api.core.mapper.UserMapper;
import com.bilitech.api.core.repository.LdapUserRepository;
import com.bilitech.api.core.repository.RoleRepository;
import com.bilitech.api.core.repository.UserRepository;
import com.bilitech.api.core.repository.specs.SearchCriteria;
import com.bilitech.api.core.repository.specs.SearchOperation;
import com.bilitech.api.core.repository.specs.UserSpecification;
import com.bilitech.api.core.service.RoleService;
import com.bilitech.api.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ldap.core.DirContextAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    UserRepository repository;

    UserMapper mapper;

    PasswordEncoder passwordEncoder;


    RoleService roleService;

    LdapTemplate ldapTemplate;

    LdapUserRepository ldapUserRepository;

    @Value("${LDAP_ENABLED}")
    Boolean LdapEnabled;

    @Value("${LDAP_BASE}")
    String ldapBase;

    @Override
    public UserDto create(UserCreateRequest userCreateRequest) {
        checkUserName(userCreateRequest.getUsername());
        User user = mapper.createEntity(userCreateRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapper.toDto(repository.save(user));
    }


    @Override
    public UserDto get(String id) {
        return mapper.toDto(getById(id));
    }

    @Override
    public UserDto update(String id, UserUpdateRequest userUpdateRequest) {
        User user = mapper.updateEntity(getById(id), userUpdateRequest);

        return mapper.toDto(repository.save(user));
    }

    private User getById(String id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            throw new BizException(ExceptionType.USER_NOT_FOUND);
        }
        return user.get();
    }

    @Override
    public void delete(String id) {
        repository.delete(getById(id));
    }

    @Override
    public Page<UserDto> search(UserSearchFilter userSearchFilter) {
        UserSpecification specs = new UserSpecification();
//        // ToDo: 需要重构
        if (!Objects.equals(userSearchFilter.getUsername(), "")) {
            specs.add(new SearchCriteria("username", userSearchFilter.getUsername(), SearchOperation.MATCH));
        }
        return repository.findAll(specs, userSearchFilter.toPageable()).map(mapper::toDto);
    }

    @Override
    public User loadUserByUsername(String username) {
        return super.loadUserByUsername(username);
    }


    protected void checkLdapUserAuth(String username, String password) {
        try {
            boolean flag = ldapTemplate.authenticate("", "(uid=" + username + ")", password);
            if (!flag) {
                throw new BizException(ExceptionType.USER_PASSWORD_NOT_MATCH);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BizException(ExceptionType.USER_PASSWORD_NOT_MATCH);
        }
    }

    protected User loadFromLdap(String username) {
        Optional<User> optionalUser = repository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        LdapUser ldapUser = ldapUserRepository.findLdapUserByUsername(username);
        User user = new User();
        user.setUsername(ldapUser.getUsername());
        user.setNickname(ldapUser.getFullName());
        user.setPassword(ldapUser.getDn().toString());
        return repository.saveAndFlush(user);
    }

    @Override
    public String createToken(TokenCreateRequest tokenCreateRequest) {
        User user;

        if (LdapEnabled) {
            checkLdapUserAuth(tokenCreateRequest.getUsername(), tokenCreateRequest.getPassword());
            user = loadFromLdap(tokenCreateRequest.getUsername());
        } else {
            user = loadUserByUsername(tokenCreateRequest.getUsername());
            if (!passwordEncoder.matches(tokenCreateRequest.getPassword(), user.getPassword())) {
                throw new BizException(ExceptionType.USER_PASSWORD_NOT_MATCH);
            }
        }

        if (!user.isEnabled()) {
            throw new BizException(ExceptionType.USER_NOT_ENABLED);
        }

        if (!user.isAccountNonLocked()) {
            throw new BizException(ExceptionType.USER_LOCKED);
        }

        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConfig.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConfig.SECRET.getBytes()));
    }

    @Override
    public UserDto getCurrentUser() {
        return mapper.toDto(super.getCurrentUserEntity());
    }


    private void checkUserName(String username) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()) {
            throw new BizException(ExceptionType.USER_NAME_DUPLICATE);
        }
    }

    @Autowired
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Autowired
    public void setLdapUserRepository(LdapUserRepository ldapUserRepository) {
        this.ldapUserRepository = ldapUserRepository;
    }

    @Autowired
    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    private void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
