package com.rohitrk.shaktigold.util;

public enum OrderStatusEnum {
    NEW("NEW"),
    PROCESSING("PROCESSING"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private String status;

    OrderStatusEnum(String status) {
        this.status = status;
    }

    public String status() {
        return status;
    }

    public boolean isValidOrderStatusUser() {
        if (this.status().equalsIgnoreCase("CANCELLED"))
            return true;
        return false;
    }

    public boolean isValidOrderStatusAdmin() {
        if (this.status().equalsIgnoreCase("PROCESSING")
                || this.status().equalsIgnoreCase("DELIVERED")
                || this.status().equalsIgnoreCase("CANCELLED"))
            return true;
        return false;
    }
}
