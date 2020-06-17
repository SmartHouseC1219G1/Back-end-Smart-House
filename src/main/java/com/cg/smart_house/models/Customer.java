package com.cg.smart_house.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;

    @OneToMany(mappedBy = "customers")
    private Set<Orders> orders;
}