package dz.sbenkhaoua.app.scrumit.core.persistence;

import dz.sbenkhaoua.app.scrumit.utils.PKeyGenerator;

import javax.inject.Inject;
import javax.persistence.PrePersist;

public class EntityPersistListener {


    @PrePersist
    public void prePresist(ScrumItEntity seaEntity) {
        seaEntity.setId(PKeyGenerator.get());
    }

}
