/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.mtc.service;

import com.nus.mtc.entity.Countrys;
import com.nus.mtc.entity.Locations;
import java.util.List;

/**
 *
 * @author Dineli
 */
public interface ILocationService {

    public List<Locations> getAllLocationData();

    public Locations getLocationDataById(int locationId);
    
    public List<Countrys> getAllCountrys();
    
    public List<Locations> getLocationDataByCountryId(int countryId);

}
