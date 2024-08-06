package br.com.gabrielgiovani.stock_rebalancing_gp.enums;

public enum SectorNature {

    CYCLICAL(1),
    NON_CYCLICAL(2);

    private final int code;

    private SectorNature(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static SectorNature valueOf(int code) {
        for(SectorNature status : SectorNature.values()) {
            if(status.getCode() == code)
                return status;
        }

        return null;
    }
}