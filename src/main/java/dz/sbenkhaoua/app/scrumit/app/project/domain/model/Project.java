package dz.sbenkhaoua.app.scrumit.app.project.domain.model;

import dz.sbenkhaoua.app.scrumit.core.persistence.EntityPersistListener;
import dz.sbenkhaoua.app.scrumit.core.persistence.ScrumItEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by dev on 19/08/15.
 */
@Entity
@EntityListeners({EntityPersistListener.class})
@XmlRootElement
@Table(name = "project", schema = "scrum_it", catalog = "scrum_it")
public class Project extends ScrumItEntity implements Serializable {
    private String name;
    private String description;
    private String estimation;
    private Timestamp startDate;
    private Timestamp endDate;


    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "estimation")
    public String getEstimation() {
        return estimation;
    }

    public void setEstimation(String estimation) {
        this.estimation = estimation;
    }

    @Basic
    @Column(name = "start_date")
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

}
