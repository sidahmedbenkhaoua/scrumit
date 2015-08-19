package dz.sbenkhaoua.app.scrumit.core.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class GenericDTO<T> {

    private int total;

    @XmlElement
    private List<T> list;

    public GenericDTO() {
        super();
    }

    public GenericDTO(List<T> list, int total) {
        super();
        this.total = total;
        this.list = list;
    }

    public GenericDTO(List<T> list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
