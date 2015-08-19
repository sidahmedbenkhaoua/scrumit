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
@Table(name = "tiers_type", schema = "scrum_it", catalog = "scrum_it")
public class TiersType extends ScrumItEntity implements Serializable {
    private String id;
    private String security;
    private int version;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TiersType tiersType = (TiersType) o;

        if (version != tiersType.version) return false;
        if (id != null ? !id.equals(tiersType.id) : tiersType.id != null) return false;
        if (security != null ? !security.equals(tiersType.security) : tiersType.security != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (security != null ? security.hashCode() : 0);
        result = 31 * result + version;
        return result;
    }
}
