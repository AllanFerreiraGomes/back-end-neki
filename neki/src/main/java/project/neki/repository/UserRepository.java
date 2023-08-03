package project.neki.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import project.neki.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel , Long>{

	Optional<UserModel> findByLogin(String login);
	
}
