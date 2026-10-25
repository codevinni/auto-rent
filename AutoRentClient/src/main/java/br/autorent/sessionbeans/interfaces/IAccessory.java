package br.autorent.sessionbeans.interfaces;

import br.autorent.model.Accessory;
import jakarta.ejb.Remote;

@Remote
public interface IAccessory extends ICrudService<Accessory> {

}