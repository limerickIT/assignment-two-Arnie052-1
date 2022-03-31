/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.service;

import com.sd4.model.Beer;
import com.sd4.model.BeerDto;
import com.sd4.model.BeerResponse;
import com.sd4.repository.BeerRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *
 * @author arnie
 */
@Service
public class BeerServiceImpl implements BeerService<Beer> {

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
    public Beer saveOrUpdate(Beer beer) {
        return beerRepository.saveAndFlush(beer);
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

    @Override
    public BeerDto createBeer(BeerDto beerDto) {

        // convert DTO to entity
        Beer beer = mapToEntity(beerDto);
        Beer newBeer = beerRepository.save(beer);

        // convert entity to DTO
        BeerDto postResponse = mapToDTO(newBeer);
        return postResponse;
    }

    @Override
    public BeerResponse getAllBeer(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Beer> beers = beerRepository.findAll(pageable);

        // get content for page object
        List<Beer> listOfBeers = beers.getContent();

        List<BeerDto> content = listOfBeers.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        BeerResponse beerResponse = new BeerResponse();
        beerResponse.setContent(content);
        beerResponse.setPageNo(beers.getNumber());
        beerResponse.setPageSize(beers.getSize());
        beerResponse.setTotalElements(beers.getTotalElements());
        beerResponse.setTotalPages(beers.getTotalPages());
        beerResponse.setLast(beers.isLast());

        return beerResponse;
    }

    // convert Entity into DTO
    private BeerDto mapToDTO(Beer beer) {
        BeerDto beerDto = new BeerDto();
        beerDto.setId(beer.getId());
        beerDto.setBrewery_id(beer.getBrewery_id());
        beerDto.setName(beer.getName());
        beerDto.setCat_id(beer.getCat_id());
        beerDto.setStyle_id(beer.getStyle_id());
        beerDto.setAbv(beer.getAbv());
        beerDto.setIbu(beer.getIbu());
        beerDto.setSrm(beer.getSrm());
        beerDto.setDescription(beer.getDescription());
        beerDto.setLast_mod(beer.getLast_mod());
        beerDto.setImage(beer.getImage());
        beerDto.setBuy_price(beer.getBuy_price());
        beerDto.setSell_price(beer.getSell_price());

        return beerDto;
    }
    
    
    // convert DTO to entity
    private Beer mapToEntity(BeerDto beerDto){
        Beer beer = new Beer();
        beer.setId(beerDto.getId());
        beer.setBrewery_id(beerDto.getBrewery_id());
        beer.setName(beerDto.getName());
        beer.setCat_id(beerDto.getCat_id());
        beer.setStyle_id(beerDto.getStyle_id());
        beer.setAbv(beerDto.getAbv());
        beer.setIbu(beerDto.getIbu());
        beer.setSrm(beerDto.getSrm());
        beer.setDescription(beerDto.getDescription());
        beer.setLast_mod(beerDto.getLast_mod());
        beer.setImage(beerDto.getImage());
        beer.setBuy_price(beerDto.getBuy_price());
        beer.setSell_price(beerDto.getSell_price());
        
        return beer;
    }

}
