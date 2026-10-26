package br.autorent.sessionbeans.interfaces;

import br.autorent.model.Accessory;
import jakarta.ejb.Remote;

/**
 * Interface remota EJB para operações CRUD de acessórios.
 */
@Remote
public interface IAccessory extends ICrudService<Accessory> {

}