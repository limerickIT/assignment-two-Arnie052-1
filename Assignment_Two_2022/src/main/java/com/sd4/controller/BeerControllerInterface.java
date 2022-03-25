/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author arnie
 */
public interface BeerControllerInterface<T> {
    
    @GetMapping
    ResponseEntity<Collection<T>> findAll();
    
    @GetMapping("{id}")
    ResponseEntity<T> findById(@PathVariable long id);

}
