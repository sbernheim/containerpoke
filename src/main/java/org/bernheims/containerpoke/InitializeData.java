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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeData {

    private static final Logger log = LoggerFactory.getLogger(InitializeData.class);

    @Bean
    CommandLineRunner initDatabase(ThingRepository thingRepo) {
        return args -> {
            thingRepo.save(new Thing("Strange", "A strange thing!"));
            thingRepo.save(new Thing("Stranger", "A stranger thing!"));
            thingRepo.save(new Thing("Strangest", "The strangest thing!"));
            thingRepo.findAll().forEach(thing -> log.info("Preloaded " + thing));
        };
    }
    
}
