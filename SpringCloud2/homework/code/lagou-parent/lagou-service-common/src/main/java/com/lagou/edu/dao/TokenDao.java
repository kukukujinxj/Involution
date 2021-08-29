package com.lagou.edu.dao;

import com.lagou.edu.pojo.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenDao extends JpaRepository<Token, Long> {
}
