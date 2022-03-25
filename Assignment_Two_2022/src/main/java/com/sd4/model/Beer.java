/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Id;
import javax.persistence.Lob;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Beer extends RepresentationModel<Beer> implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long brewery_id;
    private String name;
    private Integer cat_id;
    private Integer style_id;
    private Double abv;
    private Double ibu;
    private Double srm;
    
   
    @Lob 
    private String description;
    private Integer add_user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_mod;

    private String image;
    private Double buy_price;
    private Double sell_price;

    
    @JsonCreator
    public Beer(@JsonProperty("id") long id, @JsonProperty("brewery_id")long brewery_id, 
            @JsonProperty("name") String name,@JsonProperty("cat_id") Integer cat_id, @JsonProperty("style_id ") Integer style_id, 
            @JsonProperty("abv") Double abv,@JsonProperty("ibu") Double ibu,@JsonProperty("srm") Double srm, @JsonProperty("description") String description, 
            @JsonProperty("add_user") Integer add_user,@JsonProperty("last_mod") Date last_mod,@JsonProperty("image") String image, 
            @JsonProperty("buy_price") Double buy_price, @JsonProperty("sell_price") Double sell_price) {
        this.id = id;
        this.brewery_id = brewery_id;
        this.name = name;
        this.cat_id = cat_id;
        this.style_id = style_id;
        this.abv = abv;
        this.ibu = ibu;
        this.srm = srm;
        this.description = description;
        this.add_user = add_user;
        this.last_mod = last_mod;
        this.image = image;
        this.buy_price = buy_price;
        this.sell_price = sell_price;
    }
    
    public Beer(long brewery_id, String name, Integer cat_id, Integer style_id, Double abv, Double ibu, Double srm, String description, Integer add_user, Date last_mod, String image, Double buy_price, Double sell_price) {
        this.brewery_id = brewery_id;
        this.name = name;
        this.cat_id = cat_id;
        this.style_id = style_id;
        this.abv = abv;
        this.ibu = ibu;
        this.srm = srm;
        this.description = description;
        this.add_user = add_user;
        this.last_mod = last_mod;
        this.image = image;
        this.buy_price = buy_price;
        this.sell_price = sell_price;
    }
    
    

    
    
    
    
    
    
    
    

    
    
}
