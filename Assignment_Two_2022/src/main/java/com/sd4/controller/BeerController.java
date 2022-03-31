/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.controller;

import com.sd4.exception.ApplicationException;
import com.sd4.exception.BeerNotFoundException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sd4.model.Beer;
import com.sd4.service.IService;
import com.sd4.utils.MethodUtils;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import static org.hibernate.annotations.common.util.impl.LoggerFactory.logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 *
 * @author arnie
 */
@RestController
@RequestMapping("/beer")
public class BeerController implements BeerRepository<Beer> {

    @Autowired
    private IService<Beer> beerService;

    // Beer API Functions -----------------------------------------------
    @GetMapping(path = "/downloads/large-files/{sampleId}")
    public ResponseEntity<StreamingResponseBody> downloadZip(HttpServletResponse response,
            @PathVariable(name = "sampleId") String sampleId) {

        //logger.info("download request for sampleId = {}", sampleId);
        // list of file paths for download
        List<String> paths = Arrays.asList("/home/Videos/part1.mp4",
                "/home/Videos/part2.mp4",
                "/home/Videos/part3.mp4",
                "/home/Videos/part4.pp4");

        int BUFFER_SIZE = 1024;

        StreamingResponseBody streamResponseBody = out -> {

            final ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
            ZipEntry zipEntry = null;
            InputStream inputStream = null;

            try {
                for (String path : paths) {
                    File file = new File(path);
                    zipEntry = new ZipEntry(file.getName());

                    inputStream = new FileInputStream(file);

                    zipOutputStream.putNextEntry(zipEntry);
                    byte[] bytes = new byte[BUFFER_SIZE];
                    int length;
                    while ((length = inputStream.read(bytes)) >= 0) {
                        zipOutputStream.write(bytes, 0, length);
                    }

                }
                // set zip size in response
                response.setContentLength((int) (zipEntry != null ? zipEntry.getSize() : 0));
            } catch (IOException e) {
                //logger.error("Exception while reading and streaming data {} ", e);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (zipOutputStream != null) {
                    zipOutputStream.close();
                }
            }

        };

        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=example.zip");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");

        return ResponseEntity.ok(streamResponseBody);
    }

    @Override
    public ResponseEntity<Collection<Beer>> findAll() {
        Collection<Beer> beers = beerService.findAll();
        List<Beer> response = new ArrayList<>();
        beers.forEach(beer -> {
            beer.add(linkTo(methodOn(BeerController.class).findById(beer.getId())).withSelfRel());
            response.add(beer);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Beer> findById(long id) {

        Beer beerObject = null;
        Optional<Beer> beer = beerService.findById(id);
        if (!beer.isPresent()) {
            throw new BeerNotFoundException("Beer not found");
        } else {
            beerObject = beer.get();
            beerObject.add(linkTo(methodOn(BeerController.class).findAll()).withSelfRel());
        }
        return new ResponseEntity<>(beerObject, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Beer> save(Beer beer) {

        Optional<Beer> beerTemp = beerService.findById(beer.getId());
        //log.info("BookResourceImpl - save");
        if (beerTemp.isPresent()) {
            throw new ApplicationException("Beer ID found, ID is not required for save the data");
        } else {
            Beer savedBeer = beerService.saveOrUpdate(beer);
            savedBeer.add(linkTo(methodOn(BeerController.class).findById(savedBeer.getId())).withSelfRel());
            savedBeer.add(linkTo(methodOn(BeerController.class).findAll()).withSelfRel());
            return new ResponseEntity<>(savedBeer, HttpStatus.CREATED);
        }
    }

    @Override
    public ResponseEntity<Beer> update(Beer beer) {
        Optional<Beer> beerTemp = beerService.findById(beer.getId());
        //log.info("BookResourceImpl - save");
        if (beerTemp.isPresent()) {
            throw new ApplicationException("Book ID not found, ID is required for update the data");
        } else {
            Beer updatedBook = beerService.saveOrUpdate(beer);
            updatedBook.add(linkTo(methodOn(BeerController.class).findById(updatedBook.getId())).withSelfRel());
            updatedBook.add(linkTo(methodOn(BeerController.class).findAll()).withSelfRel());
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<String> deleteById(long id) {
        Optional<Beer> book = beerService.findById(id);
        if (!book.isPresent()) {
            throw new BeerNotFoundException("Book not found");
        }
        return new ResponseEntity<>(beerService.deleteById(id), HttpStatus.OK);
    }

}
