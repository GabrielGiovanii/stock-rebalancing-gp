package br.com.gabrielgiovani.stock_rebalancing_gp.enums;

public enum StockType {

    ORDINARY(1),
    PREFERRED(2);

    private final int code;

    private StockType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static StockType valueOf(int code) {
        for(StockType status : StockType.values()) {
            if(status.getCode() == code)
                return status;
        }

        return null;
    }
}