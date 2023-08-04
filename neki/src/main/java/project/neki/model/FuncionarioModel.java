package project.neki.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "funcionario")
public class FuncionarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(unique = true)
    private String login;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuncionarioSkill> funcionarioSkills = new ArrayList<>();

    public void addFuncionarioSkill(FuncionarioSkill funcionarioSkill) {
        funcionarioSkills.add(funcionarioSkill);
        funcionarioSkill.setFuncionario(this);
    }

    public void removeFuncionarioSkill(FuncionarioSkill funcionarioSkill) {
        funcionarioSkills.remove(funcionarioSkill);
        funcionarioSkill.setFuncionario(null);
    }
}
