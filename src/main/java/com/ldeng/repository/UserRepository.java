package com.ldeng.repository;

import org.springframework.data.repository.CrudRepository;

import com.ldeng.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername (String username);
}
