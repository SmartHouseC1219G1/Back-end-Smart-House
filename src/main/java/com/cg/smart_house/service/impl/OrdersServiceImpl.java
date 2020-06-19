package com.cg.smart_house.service.impl;

import com.cg.smart_house.model.Apartment;
import com.cg.smart_house.model.Order;
import com.cg.smart_house.repository.ApartmentRepository;
import com.cg.smart_house.repository.OrdersRepository;
import com.cg.smart_house.service.OrdersService;
import com.cg.smart_house.service.ServiceResult;
import com.cg.smart_house.service.ServiceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public ServiceResult findALl() {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setData(ordersRepository.findAll());
        return serviceResult;
    }

    @Override
    public ServiceResult updateStatusOrders(Order orders) {
        ServiceResult serviceResult = new ServiceResult();
        Optional<Order> orders1 = ordersRepository.findById(orders.getId());
        if (!orders1.isPresent()){
            serviceResult.setStatus(ServiceStatus.FAILED);
            serviceResult.setMessage("Orders not found");
            return serviceResult;
        } else {
            orders.setStatusOrders(orders.getStatusOrders());
            ordersRepository.save(orders);
            serviceResult.setMessage("Update status orders done");
        }
        return serviceResult;
    }

    @Override
    public ServiceResult findAllOrdersByApartment(Long id) {
        ServiceResult serviceResult = new ServiceResult();
        Optional<Apartment> apartment = apartmentRepository.findById(id);
        if (!apartment.isPresent()) {
            serviceResult.setMessage("Apartment no found");
            return serviceResult;
        } else {
            serviceResult.setData(ordersRepository.findAllByApartment(apartment.get()));
        }
        return serviceResult;
    }

//    @Override
//    public ServiceResult findAllOrderByStartTimeAndEndTime(Date minTime, Date maxTime) {
//        ServiceResult serviceResult = new ServiceResult();
//        List<Orders> ordersList = ordersRepository.findAll();
//        if (ordersList.isEmpty()){
//            serviceResult.setMessage("No found orders");
//            return serviceResult;
//        } else {
//            serviceResult.setData(ordersRepository.findAllByStartTimeAfterAndEndTimeBefore(minTime,maxTime));
//        }
//        return serviceResult;
//    }

    @Override
    public ServiceResult createOrders(Order orders) {
        ServiceResult serviceResult = new ServiceResult();
        serviceResult.setStatus(ServiceStatus.FAILED);

        Long idApartment = orders.getApartment().getId();
        Optional<Apartment> apartment = apartmentRepository.findById(idApartment);

        // Không có nhà để cho thuê
        if (!apartment.isPresent()) {
            serviceResult.setMessage("No apartment have been orders");
            return serviceResult;
        }

        Date startTimeOrders = orders.getStartTime();
        Date endTimeOrders = orders.getEndTime();
        Date nowDate = new Date();

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(startTimeOrders);
        c2.setTime(endTimeOrders);
        long countDayOrders = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);
        Long priceApartment = apartment.get().getPriceByDate() * countDayOrders;


        // Nhà cho thuê chưa ai thuê
        List<Order> listOrders = ordersRepository.findAllByApartment(apartment.get());
        if (listOrders.isEmpty()) {
            serviceResult.setMessage("No apartment orders by customer, order success");
            orders.setTotalMoney(priceApartment);
            serviceResult.setData(ordersRepository.save(orders));
            serviceResult.setStatus(ServiceStatus.SUCCESS);
            return serviceResult;
        }

        //Nhà đã có thời gian thuê
        Collections.sort(listOrders);
        int sizeList = listOrders.size() - 1;
        for (int i = 0; i <= sizeList; i++) {
            if (startTimeOrders.after(nowDate)
                    || endTimeOrders.before(listOrders.get(0).getStartTime()) || startTimeOrders.after(listOrders.get(sizeList).getEndTime())
                    || (startTimeOrders.after(listOrders.get(i).getEndTime()) && endTimeOrders.before(listOrders.get(i + 1).getStartTime()))){
                serviceResult.setMessage("Success orders apartment");
                orders.setTotalMoney(priceApartment);
                serviceResult.setData(ordersRepository.save(orders));
                serviceResult.setStatus(ServiceStatus.SUCCESS);
                return serviceResult;
            }
        }
        return serviceResult;
    }
}

