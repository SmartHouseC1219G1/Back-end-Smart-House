package com.cg.smart_house.controller;

import com.cg.smart_house.model.Apartment;
import com.cg.smart_house.model.Picture;
import com.cg.smart_house.service.ApartmentService;
import com.cg.smart_house.service.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    /* ---------------- CREATE Apartment ------------------------ */
    @PreAuthorize("hasRole('HOST')")
    @PostMapping("/createApartment")
    public ResponseEntity<ServiceResult> createApartment(@RequestBody Apartment apartment, Principal principal){
        String username = principal.getName();
        return new ResponseEntity<>(apartmentService.createApartment(apartment,username), HttpStatus.OK);
    }

    /* ---------------- UPDATE Apartment ------------------------ */
    @PutMapping("/updateApartment/{id}")
    public ResponseEntity<ServiceResult> updateApartment(@PathVariable Long id,@RequestBody Apartment apartment){
        return new ResponseEntity<>(apartmentService.updateApartment(id,apartment),HttpStatus.OK);
    }

    /* ---------------- VIEWS Apartment ------------------------ */
    @GetMapping("/listApartment/{id}")
    public ResponseEntity<ServiceResult> getApartmentById(@PathVariable Long id){
        return new ResponseEntity<>(apartmentService.findById(id), HttpStatus.OK);
    }

    /* ---------------- LIST Apartment ------------------------ */
    @GetMapping("/listApartment")
    public ResponseEntity<ServiceResult> listApartment(){
        return new ResponseEntity<>(apartmentService.findAll(),HttpStatus.OK);
    }

    // Update pictures only
    @PreAuthorize("hasRole('HOST')")
    @PutMapping("/update-apartment-pictures/{id}")
    public ResponseEntity<ServiceResult> updateApartmentPictures(@PathVariable Long id,@RequestBody List<Picture> pictureList){
        return new ResponseEntity<>(apartmentService.updateApartmentPicture(id,pictureList),HttpStatus.OK);
    }

    @GetMapping("/search-apartment")
    public ResponseEntity<ServiceResult> searchApartment(@RequestParam int bedroom, int bathroom, Long province_id, int startPrice, int endPrice, String startTime, String endTime) throws ParseException {
         System.out.println(startTime);
        Date startTimeDate = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
        Date endTimeDate = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
        return new ResponseEntity<>(apartmentService.searchApartment(bedroom,bathroom,province_id,startPrice,endPrice,startTimeDate,endTimeDate),HttpStatus.OK);
    }
}
