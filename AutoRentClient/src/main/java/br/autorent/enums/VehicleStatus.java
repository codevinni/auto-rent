package br.autorent.enums;

public enum VehicleStatus {

	AVAILABLE("Disponível"),
	UNAVAILABLE("Indisponível"),
    RESERVED("Reservado"),
    RENTED("Alugado"),
    MAINTENANCE("Em Manutenção");

    private final String description;

    /**
     * 
     * @param description
     */
    private VehicleStatus(String description) {
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