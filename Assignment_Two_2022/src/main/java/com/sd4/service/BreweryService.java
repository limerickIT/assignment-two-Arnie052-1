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
public class BreweryService implements IService<Brewery> {

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

    @Override
    public Brewery saveOrUpdate(Brewery brewery) {
        return breweryRepository.saveAndFlush(brewery);
    }

    @Override
    public String deleteById(long id) {
        JSONObject jsonObject = new JSONObject();
        try {
            breweryRepository.deleteById(id);
            jsonObject.put("message", "Brewery deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
