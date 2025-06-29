package com.example.demo.model.domain;

import javax.persistence.*;

@Table(name = "t_user")
@Entity
public class User {
    // 注意属性名要与数据表中的字段名一致
    // 主键自增int(10)对应long
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // 用户名属性varchar对应String
    @Column(name = "username",nullable = false,length = 60)
    private String username;

    // 密码属性varchar对应String
    private String password;
    private String email;
    private String created;
    private int valid;
    private String avatar;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "t_user_authority",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "authority_id")
//    )

//    public long getId() {
//        return id;
//    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public User() {
        this.valid = 1; // 设置默认值为1
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", created='" + created + '\'' +
                ", valid=" + valid +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
