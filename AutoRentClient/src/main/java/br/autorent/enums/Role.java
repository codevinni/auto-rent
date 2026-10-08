package br.autorent.enums;

public enum Role {

	CLIENT("Client"),
	EMPLOYEE("Employee");
	
	private String description;

	/**
	 * 
	 * @param description
	 */
	private Role(String description) {
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