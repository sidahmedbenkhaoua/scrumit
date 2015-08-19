package dz.sbenkhaoua.app.scrumit.app.task.domain.model;

import dz.sbenkhaoua.app.scrumit.core.persistence.EntityPersistListener;
import dz.sbenkhaoua.app.scrumit.core.persistence.ScrumItEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by dev on 19/08/15.
 */
@Entity
@EntityListeners({EntityPersistListener.class})
@XmlRootElement
@Table(name = "task_status", schema = "scrum_it", catalog = "scrum_it")
public class TaskStatus extends ScrumItEntity implements Serializable {
    private String id;
    private String security;
    private int version;
    private String designation;
    private String description;

    @Id
    @Column(name = "_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "_security")
    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    @Basic
    @Column(name = "_version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Basic
    @Column(name = "designation")
    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskStatus that = (TaskStatus) o;

        if (version != that.version) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (security != null ? !security.equals(that.security) : that.security != null) return false;
        if (designation != null ? !designation.equals(that.designation) : that.designation != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (security != null ? security.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (designation != null ? designation.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
