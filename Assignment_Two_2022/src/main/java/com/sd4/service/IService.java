/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author arnie
 */
public interface IService<T> {

    Collection<T> findAll();

    Optional<T> findById(long id);

    T saveOrUpdate(T t);

    String deleteById(long id);
}
