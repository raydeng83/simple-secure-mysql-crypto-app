package com.ldeng.service;

import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ldeng.domain.User;
import com.ldeng.domain.UserRole;
import com.ldeng.repository.RoleRepository;
import com.ldeng.repository.UserRepository;

@Service
@Transactional
public class UserService {

	 @Autowired
	    private RoleRepository roleRepository;

	    @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private BCryptPasswordEncoder passwordEncoder;


	    /** The application logger */
	    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	    @Transactional
	    public User createUser(User user, Set<UserRole> userRoles) {

	        User localUser = userRepository.findByUsername(user.getUsername());

	        if (localUser != null) {
	            LOG.info("User with username {} already exist. Nothing will be done. ",
	                    user.getUsername());
	        } else {

	            String encryptedPassword = passwordEncoder.encode(user.getPassword());
	            user.setPassword(encryptedPassword);

	            for (UserRole ur : userRoles) {
	                roleRepository.save(ur.getRole());
	            }

	            user.getUserRoles().addAll(userRoles);

	            localUser = userRepository.save(user);

	        }

	        return localUser;

	    }
}
