package project.neki.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class FuncionarioSkillId implements Serializable {

    @Column(name = "funcionario_id")
    private Long funcionarioId;

    @Column(name = "skill_id")
    private Long skillId;
}
