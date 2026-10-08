package br.autorent.enums;

public enum RentalStatus {

	RESERVED("Reservada"),
    IN_PROGRESS("Em Andamento"),
    COMPLETED("Concluída"),
    CANCELLED("Cancelada"),
    OVERDUE("Atrasada");

    private final String description;

    /**
     * 
     * @param description
     */
    private RentalStatus(String description) {
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
