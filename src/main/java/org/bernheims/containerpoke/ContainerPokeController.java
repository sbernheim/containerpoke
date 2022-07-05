package org.bernheims.containerpoke;
/**
 * ContainerPoke is a Java SpringBoot API example for testing a cloud-native containerized Java workload.
 *
 * The UI was modified from Stefan Prodan's very useful Podinfo project.
 * 
 */

/*
   Copyright 2022 Sebastian Bernheim

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContainerPokeController {
    
    private final ThingRepository thingRepo;

    private final ThingModelAssembler thingAsmblr;

    private boolean positiveLogo;

    ContainerPokeController(ThingRepository thingRepository, ThingModelAssembler thingAssembler) {
        this.thingRepo = thingRepository;
        this.thingAsmblr = thingAssembler;
        this.positiveLogo = true;
    }

    @GetMapping("/api/info")
    ServerInfo getInfo() {
        return new ServerInfo(positiveLogo);
    }

    @PostMapping("/api/echo")
    ServerInfo handlePing() {
        this.positiveLogo = !this.positiveLogo;
        return new ServerInfo(positiveLogo);
    }

    // Aggegate root
    // tag::get-aggregate-root[]
    @GetMapping("/things")
    CollectionModel<EntityModel<Thing>> all() {
        List<EntityModel<Thing>> things = thingRepo.findAll().stream()
                .map(thingAsmblr::toModel)
                .collect(Collectors.toList()
            );
        return CollectionModel.of(things, linkTo(methodOn(ContainerPokeController.class).all()).withSelfRel());
    }
    // end::get-aggregate-root[]

    @PostMapping("/things")
    ResponseEntity<EntityModel<Thing>> createThing(@RequestBody Thing newThing) {
        return thingAsmblr.toResponseEntity(thingRepo.save(newThing));
    }

    @GetMapping("/things/{id}")
    EntityModel<Thing> getThingById(@PathVariable Long id) {
        Thing thing = thingRepo.findById(id).orElseThrow(() -> new ThingNotFoundException(id));
        return thingAsmblr.toModel(thing);
    }

    @PutMapping("/things/{id}")
    ResponseEntity<EntityModel<Thing>> saveThingById(@RequestBody Thing thing, @PathVariable Long id) {
        Thing savedThing = thingRepo.findById(id)
                .map(foundThing -> {
                    if (!StringUtils.isBlank(thing.getName())) {
                        foundThing.setName(thing.getName());
                    }
                    if (!StringUtils.isBlank(thing.getDescription())) {
                        foundThing.setDescription(thing.getDescription());
                    }
                    return thingRepo.save(foundThing);
                }).orElseGet(() -> {
                    thing.setId(id);
                    return thingRepo.save(thing);
                });
        
        return thingAsmblr.toResponseEntity(savedThing);
    }

    @DeleteMapping("/things/{id}")
    ResponseEntity<?> deleteThingById(@PathVariable Long id) {
        thingRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
