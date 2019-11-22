package com.softwaregroup.digiwave.eip.components.communication.connectors.custom.kicb.notifier.helpers;

import javax.persistence.*;

public class Partner {

    private String url;

    private String name;

    private String password;

    private String msisdn;

    public Partner(String url, String name, String password, String msisdn) {
        this.url = url;
        this.name = name;
        this.password = password;
        this.msisdn = msisdn;
    }

    @Override
    public String toString() {
        return name + " " + msisdn + " " + password + " " + url;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getMsisdn() {
        return msisdn;
    }

}
