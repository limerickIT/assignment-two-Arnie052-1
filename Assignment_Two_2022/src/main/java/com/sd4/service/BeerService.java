/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.service;

import com.sd4.model.Beer;
import com.sd4.repository.BeerRepository;
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
public class BeerService implements IService<Beer> {

    @Autowired
    private BeerRepository beerRepository;

    @Override
    public Collection<Beer> findAll() {
        return beerRepository.findAll();
    }

    @Override
    public Optional<Beer> findById(long id) {
        return beerRepository.findById(id);
    }

    @Override
    public Beer saveOrUpdate(Beer book) {
        return beerRepository.saveAndFlush(book);
    }

    @Override
    public String deleteById(long id) {
        JSONObject jsonObject = new JSONObject();
        try {
            beerRepository.deleteById(id);
            jsonObject.put("message", "Beer deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

}
