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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
public class Thing {

    private @Id @GeneratedValue Long id;
    private String name;
    private String description;

    private static final int HASH_INIT = 31;
    private static final int HASH_MODULUS = 7;
    
    Thing() {
        this("");
    }

    Thing(String name) {
        this(name, "");
    }

    Thing(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASH_INIT, HASH_MODULUS)
                    .append(name)
                    .append(description)
                    .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != obj.getClass()) {
            return false;
        }
        Thing other = (Thing) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj))
                    .append(name, other.name)
                    .append(description, other.description)
                    .isEquals();
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE).toString();
    }

}