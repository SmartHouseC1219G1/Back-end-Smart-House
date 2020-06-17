package com.cg.smart_house.models;

import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int bathroom;
    private int bedroom;
    private int priceByDate;
    private String description;

    @OneToMany(mappedBy = "apartment")
    private Set<Picture> pictures;

    @OneToMany(mappedBy = "apartment")
    private Set<Status> statuses;

    @OneToMany(mappedBy = "apartment")
    private Set<Category> categories;

    @OneToMany(mappedBy = "apartment")
    private Set<Orders> orders;

    @ManyToOne
    @JoinColumn
    private Host hosts;

    @OneToOne(mappedBy = "apartment")
    private Address address;

    @ManyToMany
    @JoinTable(
            name = "apartment_room_type",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "room_type_id"))
    Set<RoomType> roomTypes;

    public Apartment() {
    }
}
