/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.hateoas.RepresentationModel;

/**
 *
 * @author Alan.Ryan
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Brewery extends RepresentationModel<Brewery> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String code;
    private String country;
    private String phone;
    private String website;
    private String image;

    @Lob
    private String description;

    private Integer add_user;

    @Temporal(TemporalType.TIMESTAMP)
    private Date last_mod;

    private Double credit_limit;
    private String email;

    @JsonCreator
    public Brewery(@JsonProperty("id") long id, @JsonProperty("name") String name,
            @JsonProperty("address1") String address1, @JsonProperty("adress2") String address2, @JsonProperty("city") String city,
            @JsonProperty("state") String state, @JsonProperty("code") String code, @JsonProperty("country") String country,
            @JsonProperty("phone") String phone, @JsonProperty("website") String website, @JsonProperty("image") String image,
            @JsonProperty("description") String description, @JsonProperty("add_user") Integer add_user,
            @JsonProperty("last_mod") Date last_mod, @JsonProperty("credit_limit") Double credit_limit, @JsonProperty("email") String email) {
        this.id = id;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.code = code;
        this.country = country;
        this.phone = phone;
        this.website = website;
        this.image = image;
        this.description = description;
        this.add_user = add_user;
        this.last_mod = last_mod;
        this.credit_limit = credit_limit;
        this.email = email;
    }

    public Brewery(String name, String address1, String address2, String city, String state, String code, String country, String phone, String website, String image, String description, Integer add_user, Date last_mod, Double credit_limit, String email) {
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.code = code;
        this.country = country;
        this.phone = phone;
        this.website = website;
        this.image = image;
        this.description = description;
        this.add_user = add_user;
        this.last_mod = last_mod;
        this.credit_limit = credit_limit;
        this.email = email;
    }

}
