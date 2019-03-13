package pareto.core.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "robot")
public class Robot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;
    @OneToMany(mappedBy = "robotId", cascade = CascadeType.ALL)
    private List<RobotParam> params;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public List<RobotParam> getParams() {
        return params;
    }

    public void setParams(List<RobotParam> params) {
        this.params = params;
    }
}
