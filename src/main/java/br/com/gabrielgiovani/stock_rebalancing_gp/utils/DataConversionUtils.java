package br.com.gabrielgiovani.stock_rebalancing_gp.utils;

public class DataConversionUtils {

    public static Integer convertToInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value format for Integer: " + "\"" + value + "\"");
        }
    }
}