package com.it.vh.user.domain.repository;

import com.it.vh.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {

}
