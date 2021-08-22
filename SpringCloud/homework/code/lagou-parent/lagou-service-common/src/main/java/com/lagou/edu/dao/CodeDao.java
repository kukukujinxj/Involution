package com.lagou.edu.dao;

import com.lagou.edu.pojo.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeDao extends JpaRepository<Code, Long> {
}
