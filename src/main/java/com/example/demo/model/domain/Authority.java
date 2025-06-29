package com.example.demo.model.domain;


import javax.persistence.*;

@Table(name = "t_authority")
@Entity
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String authority;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}
