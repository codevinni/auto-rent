package br.autorent.enums;

/**
 * Categorias de veículos disponíveis na frota.
 */
public enum VehicleCategory {

	HATCH("Hatch"),
    SEDAN("Sedan"),
    SUV("SUV"),
    PICKUP("Picape"),
    VAN("Van"),
    COUPE("Cupê");

    private final String description;

    /**
     * Cria uma categoria com a descrição informada.
     *
     * @param description descrição da categoria
     */
    private VehicleCategory(String description) {
        this.description = description;
    }

    /**
     * Retorna a descrição da categoria.
     *
     * @return descrição da categoria
     */
    public String getDescription() {
        return description;
    }
}