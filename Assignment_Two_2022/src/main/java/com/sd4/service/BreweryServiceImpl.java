/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.service;

import com.sd4.model.Brewery;
import com.sd4.repository.BreweryRepository;

import java.util.Collection;
import java.util.Optional;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author arnie
 */
@Service
public class BreweryServiceImpl implements BreweryService<Brewery> {

    @Autowired
    private BreweryRepository breweryRepository;

    @Override
    public Collection<Brewery> findAll() {
        return breweryRepository.findAll();
    }

    @Override
    public Optional<Brewery> findById(long id) {
        return breweryRepository.findById(id);
    }



}
