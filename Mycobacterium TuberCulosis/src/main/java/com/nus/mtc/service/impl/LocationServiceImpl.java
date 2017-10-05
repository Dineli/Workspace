/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.service.impl;

import com.nus.mtc.dao.ILocationDAO;
import com.nus.mtc.dao.impl.LocationDAOImpl;
import com.nus.mtc.entity.Countrys;
import com.nus.mtc.entity.Locations;
import com.nus.mtc.service.ILocationService;
import java.util.List;

/**
 *
 * @author Dineli
 */
public class LocationServiceImpl implements ILocationService {

    private ILocationDAO iLocationDAO = new LocationDAOImpl();

    @Override
    public List<Locations> getAllLocationData() {
        return iLocationDAO.fetchAllLocationData();
    }

    @Override
    public Locations getLocationDataById(int locationId) {
        return iLocationDAO.fetchLocationDataById(locationId);
    }

    @Override
    public List<Countrys> getAllCountrys() {
        return iLocationDAO.fetchAllCountrys();
    }

    @Override
    public List<Locations> getLocationDataByCountryId(int countryId) {
        return iLocationDAO.fetchLocationDataByCountryId(countryId);
    }

}
