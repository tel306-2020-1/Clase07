package com.example.clase07.entity;

import com.google.gson.annotations.SerializedName;

public class Parametros {

    @SerializedName(value = "3st4d0")
    private String estado;

    private int cuota;

    @SerializedName(value = "api-key")
    private String apiKey;



    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
