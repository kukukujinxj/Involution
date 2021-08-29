package com.lagou.edu.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author zy
 * @version 1.0
 * @date 2021/3/16 12:22
 */
@Data
@Entity
@Table(name = "lagou_token")
public class Token {

    /**id*/
    @Id
    private Long id;
    /**邮箱*/
    private String email;
    /**令牌*/
    private String token;


}
