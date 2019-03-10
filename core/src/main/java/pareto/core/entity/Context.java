package pareto.core.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "context")
public class Context {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @OneToMany(mappedBy = "contextId", cascade = CascadeType.ALL)
    private List<ContextParam> params;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ContextParam> getParams() {
        return params;
    }

    public void setParams(List<ContextParam> params) {
        this.params = params;
    }

    public LocalDateTime getFromTime() {
        return null;
    }

    public LocalDateTime getToTime() {
        return null;
    }

    public List<String> getProducts() {
        return null;
    }
}
