package br.autorent.enums;

public enum VehicleCategory {

	HATCH("Hatch"),
    SEDAN("Sedan"),
    SUV("SUV"),
    PICKUP("Picape"),
    VAN("Van"),
    COUPE("Cupê");

    private final String description;

    /**
     * 
     * @param description
     */
    private VehicleCategory(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     */
    public String getDescription() {
        return description;
    }
}