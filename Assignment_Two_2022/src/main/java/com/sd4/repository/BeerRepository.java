/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.repository;

import com.sd4.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author arnie
 */
public interface BeerRepository extends JpaRepository<Beer,Long>{
    
}
