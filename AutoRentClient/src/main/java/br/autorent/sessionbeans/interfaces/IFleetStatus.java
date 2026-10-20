package br.autorent.sessionbeans.interfaces;

public interface IFleetStatus {

    Long getTotalVehicles();
    Long getTotalAvailable();
    Long getTotalRented();
    Long getTotalReserved();
    Long getTotalInMaintenance();
    Long getAvailableByAgency(Long agencyId);
    void refreshCache();
}