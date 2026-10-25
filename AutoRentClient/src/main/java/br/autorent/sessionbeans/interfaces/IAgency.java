package br.autorent.sessionbeans.interfaces;

import br.autorent.model.Agency;
import jakarta.ejb.Remote;

@Remote
public interface IAgency extends ICrudService<Agency> {

}