package project.neki.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "funcionario_skill")
public class FuncionarioSkill {

    @EmbeddedId
    private FuncionarioSkillId id = new FuncionarioSkillId();

    @ManyToOne
    @MapsId("funcionarioId")
    private FuncionarioModel funcionario;

    @ManyToOne
    @MapsId("skillId")
    private SkillModel skill;

    @Column(name = "level")
    private Integer level;

    public FuncionarioSkill(FuncionarioModel funcionario, SkillModel skill, Integer level) {
        this.funcionario = funcionario;
        this.skill = skill;
        this.level = level;
    }
}
