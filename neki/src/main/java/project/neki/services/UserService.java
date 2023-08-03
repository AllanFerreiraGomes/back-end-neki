package project.neki.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import exceptions.UnmatchingIdsException;
import project.neki.model.UserModel;
import project.neki.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder enconder;

	public UserService(UserRepository userRepository, PasswordEncoder enconder) {
		this.userRepository = userRepository;
		this.enconder = enconder;
	}

	public List<UserModel> getAllUserModels() {
		return userRepository.findAll();
	}

	public UserModel getUserModelById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Id de user Invalido " + id));
	}

	public UserModel saveUserModel(UserModel UserModel) {
		// if (UserModel.getId() == null) {
		if (userRepository.existsById(UserModel.getId()) == false) {
			UserModel.setPassword(enconder.encode(UserModel.getPassword()));
			return userRepository.save(UserModel);
		} else {
			throw new UnmatchingIdsException("Id já Registrado " + UserModel.getId());
		}

	}

	public UserModel updateUserModel(UserModel UserModel) {

		if (userRepository.existsById(UserModel.getId()) == true) {
			return userRepository.save(UserModel);
		} else {
			throw new UnmatchingIdsException("id inexistente " + UserModel.getId());
		}
	}

	public boolean validarSenha(String login, String password) {
		Optional<UserModel> optUsuario = userRepository.findByLogin(login);
		if (optUsuario.isEmpty()) {
			return false;
		}

		UserModel usuario = optUsuario.get();
		return enconder.matches(password, usuario.getPassword());
	}
}
