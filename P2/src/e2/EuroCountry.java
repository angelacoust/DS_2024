package e2;

public enum EuroCountry {
    ANDORRA("AD"), AUSTRIA("AT"), BELGIUM("BE"), CYPRUS("CY"), CROATIA("HR"),
    ESTONIA("EE"), FINLAND("FI"), FRANCE("FR"), GERMANY("DE"), GREECE("GR"),
    IRELAND("IE"), ITALY("IT"), LATVIA("LV"), LITHUANIA("LT"), LUXEMBOURG("LU"),
    MALTA("MT"), MONACO("MC"), NETHERLANDS("NL"), PORTUGAL("PT"), SAN_MARINO("SM"),
    SLOVAKIA("SK"), SLOVENIA("SI"), SPAIN("ES"), VATICAN_CITY("VA");

    private final String isoCode;

    EuroCountry(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getIsoCode() {
        return isoCode;
    }
}

