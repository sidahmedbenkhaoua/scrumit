package dz.sbenkhaoua.app.scrumit.app.project.repository;

import dz.sbenkhaoua.app.scrumit.app.project.domain.model.Project;
import dz.sbenkhaoua.app.scrumit.core.repository.ScrumItRepository;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

/**
 * Created by dev on 19/08/15.
 */
@Named
@Transactional
public class ProjectRepository extends ScrumItRepository<Project> {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager entityManager;

    public ProjectRepository() {
        super(Project.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
