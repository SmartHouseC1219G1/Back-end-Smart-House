package com.cg.smart_house.controller;

import com.cg.smart_house.enumm.ServiceStatus;
import com.cg.smart_house.enumm.StatusOrders;
import com.cg.smart_house.model.Apartment;
import com.cg.smart_house.model.Order;
import com.cg.smart_house.model.User;
import com.cg.smart_house.repository.UserRepository;
import com.cg.smart_house.service.OrdersService;
import com.cg.smart_house.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/listOrders")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('HOST')")
    public ResponseEntity<ServiceResult> listOrders() {
        return new ResponseEntity<>(ordersService.findALl(), HttpStatus.OK);
    }

    @PostMapping("/createOrders")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ServiceResult> createOrders(@RequestBody Order orders, Principal principal) {
        String username = principal.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()){
            throw new RuntimeException("Not found");
        }
        User user = userOptional.get();
        return new ResponseEntity<>(ordersService.createOrders(orders,user), HttpStatus.OK);
    }

    @DeleteMapping("/deleteOrder/{idOrder}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ServiceResult> deleteOrders(@PathVariable Long idOrder) {
        return new ResponseEntity<>(ordersService.deleteOrder(idOrder), HttpStatus.OK);
    }

    @PutMapping("/updateStatusOrders")
    public ResponseEntity<ServiceResult> updateStatusOrders(@RequestBody Order orders) {
        return new ResponseEntity<>(ordersService.updateStatusOrders(orders), HttpStatus.OK);
    }

    @GetMapping("/searchOrders")
    public ResponseEntity<ServiceResult> listApartmentByDate(@RequestParam("minTime") String minTime,
                                                             @RequestParam("maxTime") String maxTime) throws ParseException{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dfMinTime = df.parse(minTime);
        Date dfMaxTime = df.parse(maxTime);
        return new ResponseEntity<>(ordersService.findAllOrderByStartTimeAndEndTime(dfMinTime, dfMaxTime), HttpStatus.OK);
    }

//    @GetMapping("/listOrders")
    @PreAuthorize("hasRole('HOST')")
    @PostMapping("/block-order")
    public ResponseEntity<ServiceResult> blockOrder(@RequestBody Order order, Principal principal){
        String hostname = principal.getName();
        Optional<User> hostOptional = userRepository.findByUsername(hostname);
        if (!hostOptional.isPresent()){
            throw new RuntimeException("Not found");
        }
        User host = hostOptional.get();
        return new ResponseEntity<>(ordersService.blockOrder(order,host),HttpStatus.OK);
    }

    @GetMapping("findOrderByApartmentAndStatus")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ServiceResult> viewOrderByUser(@RequestParam StatusOrders statusOrders, Principal principal){
        String hostname = principal.getName();
        Optional<User> hostOptional = userRepository.findByUsername(hostname);
        if (!hostOptional.isPresent()){
            throw new RuntimeException("Not found");
        }
        User host = hostOptional.get();
        return new ResponseEntity<>(ordersService.findOrderByApartmentAndStatus(host.getId(),statusOrders),HttpStatus.OK);
    }

    @GetMapping("/findAllByCustomerAndStatus")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ServiceResult> findAllByCustomer(@RequestParam StatusOrders statusOrders, Principal principal) {
        String username = principal.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()){
            throw new RuntimeException("Not found");
        }
        User user = userOptional.get();
        return new ResponseEntity<>(ordersService.findAllByCustomerAndStatus(user.getId(),statusOrders),HttpStatus.OK);
    }

    @PutMapping("/confirmOrderApartment/{idOrder}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ServiceResult> confirmOrderApartment(@PathVariable Long idOrder){
        return new ResponseEntity<>(ordersService.confirmOrderApartment(idOrder),HttpStatus.OK);
    }

    @PutMapping("/checkinOrderApartment/{idOrder}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ServiceResult> checkinOrderApartment(@PathVariable Long idOrder){
        return new ResponseEntity<>(ordersService.checkinOrderApartment(idOrder),HttpStatus.OK);
    }

    @GetMapping("/viewsOrderPendingByCustomer/{idCustomer}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ServiceResult> viewsOrderPendingByCustomer(@PathVariable Long idCustomer, Principal principal){
        String hostname = principal.getName();
        Optional<User> hostOptional = userRepository.findByUsername(hostname);
        if (!hostOptional.isPresent()){
            throw new RuntimeException("Not found");
        }
        User host = hostOptional.get();
        return new ResponseEntity<>(ordersService.viewsOrderPendingByCustomer(host.getId(),idCustomer),HttpStatus.OK);
    }

    @PutMapping("/cancelOrderApartment/{idOrder}")
    @PreAuthorize("hasRole('HOST')")
    public ResponseEntity<ServiceResult> cancelOrderApartment(@PathVariable Long idOrder){
        return new ResponseEntity<>(ordersService.cancelOrderApartment(idOrder),HttpStatus.OK);
    }

    @GetMapping("/findAllOrderOfCustomer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ServiceResult> findAllOrderOfCustomer(Principal principal){
        String customerName = principal.getName();
        return new ResponseEntity<>(ordersService.findAllOrderByCustomer(customerName),HttpStatus.OK);
    }

    @PutMapping("/cancelOrder/{idOrder}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<ServiceResult> cancelOrder(@PathVariable Long idOrder){
        return new ResponseEntity<>(ordersService.cancelOrderByUser(idOrder),HttpStatus.OK);
    }
}
