package dz.sbenkhaoua.app.scrumit.app.project.ux.controller;

import dz.sbenkhaoua.app.scrumit.app.project.domain.model.Project;
import dz.sbenkhaoua.app.scrumit.app.project.repository.ProjectRepository;
import dz.sbenkhaoua.app.scrumit.core.controller.ScrumItController;
import dz.sbenkhaoua.app.scrumit.core.repository.ScrumItRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Path;

/**
 * Created by dev on 19/08/15.
 */
@RequestScoped
@Path("/projectCtrl")
public class ProjectController extends ScrumItController<Project> {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProjectRepository expertiseCategoryRepository;

    @Override
    public ScrumItRepository<Project> getSeaRepository() {
        return expertiseCategoryRepository;
    }
}
