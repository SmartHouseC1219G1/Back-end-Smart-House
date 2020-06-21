package com.cg.smart_house.service;

import com.cg.smart_house.model.Apartment;
import com.cg.smart_house.model.Picture;

import java.util.Date;
import java.util.List;

public interface ApartmentService {
    ServiceResult createApartment(Apartment apartment);
    ServiceResult updateApartment(Apartment apartment);
    ServiceResult findAll();
    ServiceResult findById(Long id);
    ServiceResult deleteApartment(Long id);
//    ServiceResult findAllByAddressAndOrderStartTimeAndEndTime(Long idProvince, Date minTime, Date maxTime);
//
//    ServiceResult saveApartment(Apartment apartment);
//
//    List<Picture> savePictures(Apartment apartment);


}
