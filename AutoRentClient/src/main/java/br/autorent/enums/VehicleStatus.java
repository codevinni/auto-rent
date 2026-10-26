package br.autorent.enums;

/**
 * Status possíveis de um veículo na frota.
 */
public enum VehicleStatus {

	AVAILABLE("Disponível"),
	UNAVAILABLE("Indisponível"),
    RESERVED("Reservado"),
    RENTED("Alugado"),
    MAINTENANCE("Em Manutenção");

    private final String description;

    /**
     * Cria um status de veículo com a descrição informada.
     *
     * @param description descrição do status
     */
    private VehicleStatus(String description) {
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