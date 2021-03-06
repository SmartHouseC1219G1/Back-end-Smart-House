package com.cg.smart_house.repository;

import com.cg.smart_house.model.Apartment;
import com.cg.smart_house.model.Order;
import com.cg.smart_house.enumm.StatusOrders;
import com.cg.smart_house.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByApartment_Id(Long id);
    List<Order> findAllByApartment(Apartment apartment);

    @Query(value = "select a from Order a where :minTime <= a.startTime and  a.endTime <= :maxTime")
    List<Order> getAllByStartTimeAndEndTime(@Param("minTime") Date minTime,
                                            @Param("maxTime") Date maxTime);

    @Query(value = "select a from Order a where :minTime <= a.startTime and  a.endTime <= :maxTime")
    List<Order> getAllByStartTimeAndEndTimeNoParam( Date minTime, Date maxTime);

    List<Order> findAllByApartmentAndStatusOrders(Apartment apartment, StatusOrders statusOrders);

    List<Order> findAllByUserAndApartmentAndStatusOrders(Optional<User> user, Optional<Apartment> apartment, StatusOrders statusOrders);

    List<Order> findAllByUserAndStatusOrders(User user, StatusOrders statusOrders);

    List<Order> findAllByUser(User user);
}
