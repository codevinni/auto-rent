package br.autorent.enums;

/**
 * Papéis de usuário no sistema.
 */
public enum Role {

	CUSTOMER("Client"),
	EMPLOYEE("Employee");
	
	private String description;

	/**
	 * Cria um papel com a descrição informada.
	 *
	 * @param description descrição do papel
	 */
	private Role(String description) {
		this.description = description;
	}

	/**
	 * Retorna a descrição do papel.
	 *
	 * @return descrição do papel
	 */
	public String getDescription() {
		return description;
	}
}