package com.cg.smart_house.model;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private List<Picture> pictures;

//    @OneToMany(mappedBy = "apartment")
//    private List<Status> statuses;

    @OneToMany(mappedBy = "apartment")
    private List<Category> categories;

    @OneToMany(mappedBy = "apartment")
    private List<Order> orders;

    @OneToOne(mappedBy = "apartment")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private Host hosts;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "apartment_room_type",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "room_type_id"))
    private List<RoomType> roomTypes = new ArrayList<>();

    public Apartment() {
    }

    public Apartment(String name, int bathroom, int bedroom, int priceByDate, String description) {
        this.name = name;
        this.bathroom = bathroom;
        this.priceByDate = priceByDate;
        this.description = description;
    }
}
