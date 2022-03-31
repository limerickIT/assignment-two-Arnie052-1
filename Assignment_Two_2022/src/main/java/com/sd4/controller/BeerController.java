/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.exception.ApplicationException;
import com.sd4.exception.BeerNotFoundException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sd4.model.Beer;
import com.sd4.model.BeerDto;
import com.sd4.model.BeerResponse;
import com.sd4.service.DownloadZipServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import com.sd4.service.BeerService;
import com.sd4.utils.AppConstants;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author arnie
 */
@RestController
@RequestMapping("/beer")
public class BeerController implements BeerRepository<Beer> {

    
    
    @Autowired
    private BeerService<Beer> beerService;
    
    
     // create blog post rest api
    @PostMapping
    public ResponseEntity<BeerDto> createBeer(@RequestBody BeerDto beerDto){
        return new ResponseEntity<>(beerService.createBeer(beerDto), HttpStatus.CREATED);
    }

    // get all posts rest api
    @GetMapping("/pagination")
    public BeerResponse getAllBeer(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return beerService.getAllBeer(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/zip")
    public void DownloadZipFile(HttpServletResponse response) {
        // list of file paths for download
        List<String> listOfFileNames = new ArrayList<>();
        
        listOfFileNames.add("/src/main/resources/static.assets.images.large/1.jpg");
        listOfFileNames.add("/src/main/resources/static.assets.images.large/2.jpg");
        listOfFileNames.add("/src/main/resources/static.assets.images.large/3.jpg");
        listOfFileNames.add("/src/main/resources/static.assets.images.large/4.jpg");
        listOfFileNames.add("/src/main/resources/static.assets.images.large/noimage.jpg");
   
        
        DownloadZipServiceImpl.downloadZipFile(response, listOfFileNames);
    }

// Beer API Functions -----------------------------------------------
    @Override
    public ResponseEntity<Collection<Beer>> findAll() {
        Collection<Beer> beers = beerService.findAll();
        List<Beer> response = new ArrayList<>();
        beers.forEach(beer -> {
            beer.add(linkTo(methodOn(BeerController.class
            ).findById(beer.getId())).withSelfRel());
            response.add(beer);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Beer> findById(long id) {

        Beer beerObject = null;
        Optional<Beer> beer = beerService.findById(id);
        if (!beer.isPresent()) {
            throw new BeerNotFoundException("Beer not found");
        } else {
            beerObject = beer.get();
            beerObject.add(linkTo(methodOn(BeerController.class
            ).findAll()).withSelfRel());
        }
        return new ResponseEntity<>(beerObject, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Beer> save(Beer beer) {

        Optional<Beer> beerTemp = beerService.findById(beer.getId());
        //log.info("BookResourceImpl - save");
        if (beerTemp.isPresent()) {
            throw new ApplicationException("Beer ID found, ID is not required for save the data");
        } else {
            Beer savedBeer = beerService.saveOrUpdate(beer);
            savedBeer.add(linkTo(methodOn(BeerController.class
            ).findById(savedBeer.getId())).withSelfRel());
            savedBeer.add(linkTo(methodOn(BeerController.class
            ).findAll()).withSelfRel());
            return new ResponseEntity<>(savedBeer, HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<Beer> update(Beer beer) {
        Optional<Beer> beerTemp = beerService.findById(beer.getId());
        //log.info("BookResourceImpl - save");
        if (beerTemp.isPresent()) {
            throw new ApplicationException("Book ID not found, ID is required for update the data");
        } else {
            Beer updatedBeer = beerService.saveOrUpdate(beer);
            updatedBeer.add(linkTo(methodOn(BeerController.class
            ).findById(updatedBeer.getId())).withSelfRel());
            updatedBeer.add(linkTo(methodOn(BeerController.class
            ).findAll()).withSelfRel());
            return new ResponseEntity<>(updatedBeer, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> deleteById(long id) {
        Optional<Beer> book = beerService.findById(id);
        if (!book.isPresent()) {
            throw new BeerNotFoundException("Book not found");
        }
        return new ResponseEntity<>(beerService.deleteById(id), HttpStatus.OK);
    }

}
