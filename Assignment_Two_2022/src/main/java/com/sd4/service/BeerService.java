/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.service;

import com.sd4.model.Beer;
import com.sd4.model.BeerDto;
import com.sd4.model.BeerResponse;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author arnie
 */
public interface BeerService<T> {

    Collection<T> findAll();

    Optional<T> findById(long id);

    T saveOrUpdate(T t);

    String deleteById(long id);
    
    BeerDto createBeer (BeerDto beerDto);
    
    BeerResponse getAllBeer(int pageNo, int pageSize, String sortBy, String sortDir);
}
