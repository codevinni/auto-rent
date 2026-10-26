package br.autorent.sessionbeans.interfaces;

import br.autorent.model.Agency;
import jakarta.ejb.Remote;

/**
 * Interface remota EJB para operações CRUD de agências.
 */
@Remote
public interface IAgency extends ICrudService<Agency> {

}