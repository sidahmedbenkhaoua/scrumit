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
@Table(name = "task", schema = "scrum_it", catalog = "scrum_it")
public class Task extends ScrumItEntity implements Serializable {
    private String id;
    private String security;
    private int version;
    private String designation;
    private String tag;
    private String desciption;

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
    @Column(name = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Basic
    @Column(name = "desciption")
    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (version != task.version) return false;
        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (security != null ? !security.equals(task.security) : task.security != null) return false;
        if (designation != null ? !designation.equals(task.designation) : task.designation != null) return false;
        if (tag != null ? !tag.equals(task.tag) : task.tag != null) return false;
        if (desciption != null ? !desciption.equals(task.desciption) : task.desciption != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (security != null ? security.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (designation != null ? designation.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (desciption != null ? desciption.hashCode() : 0);
        return result;
    }
}
