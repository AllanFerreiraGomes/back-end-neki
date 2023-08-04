package project.neki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.neki.model.FuncionarioModel;
import project.neki.model.FuncionarioSkill;
import project.neki.model.SkillModel;

import java.util.Optional;

@Repository
public interface FuncionarioSkillRepository extends JpaRepository<FuncionarioSkill, Long> {

    Optional<FuncionarioSkill> findByFuncionarioAndSkill(FuncionarioModel funcionario, SkillModel skill);

    boolean existsByFuncionarioAndSkill(FuncionarioModel funcionario, SkillModel skill);
}
