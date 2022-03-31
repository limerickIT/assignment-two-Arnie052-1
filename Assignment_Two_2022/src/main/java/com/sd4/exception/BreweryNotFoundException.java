/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.exception;

/**
 *
 * @author arnie
 */
public class BreweryNotFoundException extends RuntimeException {
    
    public BreweryNotFoundException(String message) {
		super(message);
	}
}
