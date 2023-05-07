package nl.zvnv.producer.utils;

public enum Currency {
    USD(1),
    RUB(77.5);

    public final double unitsPerUSD;

    private Currency(double unitsPerUSD) {
        this.unitsPerUSD = unitsPerUSD;
    }
}
