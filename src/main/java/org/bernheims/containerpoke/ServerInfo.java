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

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ServerInfo {

    private static final int HASH_INIT = 31;
    private static final int HASH_MODULUS = 7;

    String hostname;
    String version;
    String color;
    String message;
    String logo;

    public ServerInfo(boolean positiveLogo) {
        try {
            this.hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            this.hostname = "unknown";
        }
        this.version = "6.3.9";
        this.message = "Hello!";
        //this.color = "#99FF33";
        //this.color = "#33AFFF";
        this.color = positiveLogo ? "#5BBEFF" : "#008002";
        this.logo = positiveLogo ? "containerBlack.png" : "containerWhite.png";
    }

    public String getHostname() {
        return this.hostname;
    }

    public String getVersion() {
        return this.version;
    }

    public String getColor() {
        return this.color;
    }

    public String getMessage() {
        return this.message;
    }

    public String getLogo() {
        return this.logo;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASH_INIT, HASH_MODULUS)
                    .append(hostname)
                    .append(version)
                    .append(color)
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
        ServerInfo other = (ServerInfo) obj;
        return new EqualsBuilder().appendSuper(super.equals(obj))
                    .append(hostname, other.hostname)
                    .append(version , other.version)
                    .append(color , other.color)
                    .isEquals();
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE).toString();
    }
    
}
