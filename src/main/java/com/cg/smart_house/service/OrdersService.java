package com.cg.smart_house.service;

import com.cg.smart_house.model.Order;

import java.util.Date;

public interface OrdersService {
    ServiceResult findALl();
    ServiceResult updateStatusOrders(Order orders);
    ServiceResult createOrders(Order orders);
    ServiceResult findAllOrdersByApartment(Long id);

    ServiceResult findAllOrderByStartTimeAndEndTime(Date minTime, Date maxTime);
//    ServiceResult findAllOrderByStartTimeAndEndTime(Date minTime, Date maxTime);
    //Tim kiem nha da cho thue
    ServiceResult findAllApartmentRanting();
}