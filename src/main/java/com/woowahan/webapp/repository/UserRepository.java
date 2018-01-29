package com.woowahan.webapp.repository;

import com.woowahan.webapp.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUserId(String userId);
}
