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
@Table(name = "lagou_auth_code")
public class Code {

    /**id*/
    @Id
    private Long id;
    /**邮箱*/
    private String email;
    /**验证码*/
    private String code;
    /**创建时间*/
    private String createtime;
    /**过期时间*/
    private String expiretime;


}
