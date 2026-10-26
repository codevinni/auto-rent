package br.autorent.enums;

/**
 * Status possíveis de uma locação no sistema.
 */
public enum RentalStatus {

	RESERVED("Reservada"),
    IN_PROGRESS("Em Andamento"),
    COMPLETED("Concluída"),
    CANCELLED("Cancelada"),
    OVERDUE("Atrasada");

    private final String description;

    /**
     * Cria um status de locação com a descrição informada.
     *
     * @param description descrição do status
     */
    private RentalStatus(String description) {
        this.description = description;
    }

    /**
     * Retorna a descrição do status.
     *
     * @return descrição do status
     */
    public String getDescription() {
        return description;
    }
	
}
