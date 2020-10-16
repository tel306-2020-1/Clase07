package com.example.clase07.entity;

import com.google.gson.annotations.SerializedName;

public class Empleado {

    @SerializedName(value = "employee_id")
    private int employeeId;
    private String first_name;
    private String last_name;
    private String codigo;


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
