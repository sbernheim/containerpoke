package org.bernheims.containerpoke;
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

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ThingModelAssembler implements RepresentationModelAssembler<Thing, EntityModel<Thing>>{
    @Override
    public EntityModel<Thing> toModel(Thing thing) {
        return EntityModel.of(thing,
            linkTo(methodOn(ContainerPokeController.class).getThingById(thing.getID())).withSelfRel(),
            linkTo(methodOn(ContainerPokeController.class).all()).withRel("things")
        );
    }

    public ResponseEntity<EntityModel<Thing>> toResponseEntity(Thing thing) {
        EntityModel<Thing> model = this.toModel(thing);
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

}
