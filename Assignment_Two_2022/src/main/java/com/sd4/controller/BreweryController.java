/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.google.zxing.WriterException;
import com.sd4.exception.BeerNotFoundException;
import com.sd4.exception.BreweryNotFoundException;
import com.sd4.model.Beer;
import com.sd4.model.Brewery;
import com.sd4.service.IService;
import com.sd4.utils.MethodUtils;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author arnie
 */
@RestController
@RequestMapping("/brewery")
public class BreweryController implements BreweryRepository<Brewery> {

    @Autowired
    private IService<Brewery> breweryService;

    //private final String imagePath = "./src/main/resources/qrcodes/QRCode3.png";
    @RequestMapping(value = "/generateImageQRCode/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] generateImageQRCode(@PathVariable("id") long id) throws WriterException {
        //log.info("BookResourceImpl - generateImageQRCode");
        Brewery breweryObject = null;
        Optional<Brewery> brewery = breweryService.findById(id);
        if (!brewery.isPresent()) {

            throw new BreweryNotFoundException("Brewery not found");

        } else {
            breweryObject = brewery.get();

            String MeCard = "MECARD:N" + breweryObject.getName() + ";"
                    + "ADR:" + breweryObject.getAddress1() + " " + breweryObject.getAddress2() + " " + breweryObject.getCity() + " " + breweryObject.getCode() + " " + breweryObject.getCountry() + ";"
                    + "EMAIL:" + breweryObject.getEmail() + ";"
                    + "TEL:" + breweryObject.getPhone() + ";" + "URL" + breweryObject.getWebsite() + ";";

            byte[] vCardInBytes = MethodUtils.generateImageQRCode(MeCard, 500, 500);

            breweryObject.add(linkTo(methodOn(BreweryController.class).findAll()).withSelfRel());
            return vCardInBytes;

        }

    }

    @GetMapping("/map/{id}")
    public ResponseEntity<String> mapByAddress(@PathVariable long id) {
        Brewery breweryObject = null;
        Optional<Brewery> brewery = breweryService.findById(id);
        if (!brewery.isPresent()) {
            throw new BreweryNotFoundException("Brewery not found");

        } else {
            breweryObject = brewery.get();

            String name = breweryObject.getName();
            String address = breweryObject.getAddress1();
            String address2 = breweryObject.getAddress2();
            String city = breweryObject.getCity();
            String country = breweryObject.getCountry();
            String code = breweryObject.getCode();

            String breweryMap = name + address + address2 + city + country + code;
            return ResponseEntity.ok(
                    "<html><body>"
                    + "<h2>" + "[" + name + "]" + "[" + address + address2 + "]" + "[" + city + "," + country + "]" + "</h2>"
                    + "<iframe width=\"1000\" height=\"1000\" id=\"gmap_canvas\" src=\"https://maps.google.com/maps?width=100%25&amp;height=600&amp;hl=en&amp;q=" + URLEncoder.encode(breweryMap, StandardCharsets.UTF_8)
                    + "=&output=embed\"  frameborder=\"0\" scrolling=\"no\" </iframe>");
        }
    }

    @Override
    public ResponseEntity<Collection<Brewery>> findAll() {
        Collection<Brewery> breweries = breweryService.findAll();
        List<Brewery> response = new ArrayList<>();
        breweries.forEach(brewery -> {
            brewery.add(linkTo(methodOn(BreweryController.class).findById(brewery.getId())).withSelfRel());
            response.add(brewery);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Brewery> findById(long id) {

        Brewery breweryObject = null;
        Optional<Brewery> brewery = breweryService.findById(id);
        if (!brewery.isPresent()) {
            throw new BeerNotFoundException("brewery not found");
        } else {
            breweryObject = brewery.get();
            breweryObject.add(linkTo(methodOn(BeerController.class).findAll()).withSelfRel());
        }
        return new ResponseEntity<>(breweryObject, HttpStatus.OK);
    }
}
