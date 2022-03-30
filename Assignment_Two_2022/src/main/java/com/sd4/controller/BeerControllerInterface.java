/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import java.util.Collection;
import java.util.UUID;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author arnie
 */
public interface BeerControllerInterface<T> {

    @GetMapping
    ResponseEntity<Collection<T>> findAll();

    @GetMapping("{id}")
    ResponseEntity<T> findById(@PathVariable long id);

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<T> save(@RequestBody T t);

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<T> update(@RequestBody T t);
      @DeleteMapping("{id}")
    ResponseEntity<String> deleteById(@PathVariable long id);

}
