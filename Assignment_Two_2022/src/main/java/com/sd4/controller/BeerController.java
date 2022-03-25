/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sd4.model.Beer;
import com.sd4.service.IService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author arnie
 */
@RestController
@RequestMapping("/beer")
public class BeerController implements BeerControllerInterface<Beer> {

    @Autowired
    private IService<Beer> beerService;

    @Override
    public ResponseEntity<Collection<Beer>> findAll() {
        Collection<Beer> beers = beerService.findAll();
        List<Beer> response = new ArrayList<>();
        beers.forEach(beer -> {
            beer.add(linkTo(methodOn(BeerController.class).findById(beer.getId())).withSelfRel());
            response.add(beer);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Beer> findById(long id) {
    
        Beer beerObject = null;
        Optional<Beer> beer = beerService.findById(id);
//        if (!beer.isPresent()) {
//            throw new BeerkNotFoundException("Beer not found");
//        } else {
            beerObject = beer.get();
            beerObject.add(linkTo(methodOn(BeerController.class).findAll()).withSelfRel());
//        }
        return new ResponseEntity<>(beerObject, HttpStatus.OK);
    }

}
