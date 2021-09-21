package com.lagou.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "c_order")
public class Order implements Serializable {

    @Id
    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "is_del")
    private Boolean is_del;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "company_id")
    private Integer company_id;
    @Column(name = "publish_user_id")
    private Integer publish_user_id;
    @Column(name = "position_id")
    private Integer position_id;
    @Column(name = "resume_type")
    private Integer resume_type;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private Date create_time;
    @Column(name = "update_time")
    private Date update_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIs_del() {
        return is_del;
    }

    public void setIs_del(Boolean is_del) {
        this.is_del = is_del;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public Integer getPublish_user_id() {
        return publish_user_id;
    }

    public void setPublish_user_id(Integer publish_user_id) {
        this.publish_user_id = publish_user_id;
    }

    public Integer getPosition_id() {
        return position_id;
    }

    public void setPosition_id(Integer position_id) {
        this.position_id = position_id;
    }

    public Integer getResume_type() {
        return resume_type;
    }

    public void setResume_type(Integer resume_type) {
        this.resume_type = resume_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
