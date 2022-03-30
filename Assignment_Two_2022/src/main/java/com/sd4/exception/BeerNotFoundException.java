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
public class BeerNotFoundException  extends RuntimeException
{
    public BeerNotFoundException(String message) {
		super(message);
	}
}
