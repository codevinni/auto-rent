package br.autorent.sessionbeans.interfaces;

import jakarta.ejb.Remote;

/**
 * Interface remota EJB para consulta de estatísticas da frota de veículos.
 * Os dados são mantidos em cache por um bean singleton.
 */
@Remote
public interface IFleetStatus {

    /** @return total de veículos cadastrados na frota */
    Long getTotalVehicles();

    /** @return total de veículos disponíveis */
    Long getTotalAvailable();

    /** @return total de veículos alugados */
    Long getTotalRented();

    /** @return total de veículos reservados */
    Long getTotalReserved();

    /** @return total de veículos em manutenção */
    Long getTotalInMaintenance();

    /**
     * Retorna o total de veículos disponíveis em uma agência específica.
     *
     * @param agencyId identificador da agência
     * @return quantidade de veículos disponíveis na agência
     */
    Long getAvailableByAgency(Long agencyId);

    /** Atualiza o cache de estatísticas da frota. */
    void refreshCache();
}