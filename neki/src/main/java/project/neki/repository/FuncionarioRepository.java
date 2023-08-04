package project.neki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.neki.model.FuncionarioModel;

public interface FuncionarioRepository extends JpaRepository<FuncionarioModel , Long>{

	Optional<FuncionarioModel> findByLogin(String login);
	
}
