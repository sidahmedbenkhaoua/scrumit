package dz.sbenkhaoua.app.scrumit.app.tiers.domain.model;

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
@Table(name = "tiers", schema = "scrum_it", catalog = "scrum_it")
public class Tiers extends ScrumItEntity implements Serializable {
    private String id;
    private String security;
    private int version;
    private String firstName;
    private String lastName;
    private String email;

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
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tiers tiers = (Tiers) o;

        if (version != tiers.version) return false;
        if (id != null ? !id.equals(tiers.id) : tiers.id != null) return false;
        if (security != null ? !security.equals(tiers.security) : tiers.security != null) return false;
        if (firstName != null ? !firstName.equals(tiers.firstName) : tiers.firstName != null) return false;
        if (lastName != null ? !lastName.equals(tiers.lastName) : tiers.lastName != null) return false;
        if (email != null ? !email.equals(tiers.email) : tiers.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (security != null ? security.hashCode() : 0);
        result = 31 * result + version;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
