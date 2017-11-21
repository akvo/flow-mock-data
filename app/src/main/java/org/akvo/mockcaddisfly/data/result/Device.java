package org.akvo.mockcaddisfly.data.result;

import android.os.Build;

import java.util.Locale;

public class Device {

    private String product;
    private String os;
    private String model;
    private String language;
    private String manufacturer;
    private String country;

    public Device(String product, String os, String model, String language,
            String manufacturer, String country) {
        this.product = product;
        this.os = os;
        this.model = model;
        this.language = language;
        this.manufacturer = manufacturer;
        this.country = country;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
