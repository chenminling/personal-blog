package com.example.demo.model.domain;

import javax.persistence.*;

@Table(name = "t_user_authority")
@Entity
public class user_authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")

    private long userid;
    @Column(name = "authority_id")
    private long authorityid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getAuthorityid() {
        return authorityid;
    }

    public void setAuthorityid(long authorityid) {
        this.authorityid = authorityid;
    }

    @Override
    public String toString() {
        return "user_authority{" +
                "id=" + id +
                ", userid=" + userid +
                ", authorityid=" + authorityid +
                '}';
    }
}
