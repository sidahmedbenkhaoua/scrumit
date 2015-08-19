package dz.sbenkhaoua.app.scrumit.core.persistence;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@MappedSuperclass
public class ScrumItEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "_id", nullable = false, length = 10)
    private String id;

    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "_security", nullable = false, length = 1024)
    private String security;

    @NotNull
    @Column(name = "_version", nullable = false)
    private int version;

    public ScrumItEntity() {
        super();
    }

    public ScrumItEntity(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
