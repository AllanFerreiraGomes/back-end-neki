package project.neki.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import exceptions.UnmatchingIdsException;
import project.neki.model.FuncionarioModel;
import project.neki.repository.FuncionarioRepository;

@Service
public class FuncionarioService {

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	PasswordEncoder enconder;

	public FuncionarioService(FuncionarioRepository funcionarioRepository, PasswordEncoder enconder) {
		this.funcionarioRepository = funcionarioRepository;
		this.enconder = enconder;
	}

	public List<FuncionarioModel> getAllFuncionarioModels() {
		return funcionarioRepository.findAll();
	}

	public FuncionarioModel getFuncionarioModelById(Long id) {
		return funcionarioRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Id de funcionario Invalido " + id));
	}

	public FuncionarioModel saveFuncionarioModel(FuncionarioModel FuncionarioModel) {
		// if (FuncionarioModel.getId() == null) {
		if (funcionarioRepository.existsById(FuncionarioModel.getId()) == false) {
			FuncionarioModel.setPassword(enconder.encode(FuncionarioModel.getPassword()));
			return funcionarioRepository.save(FuncionarioModel);
		} else {
			throw new UnmatchingIdsException("Id já Registrado " + FuncionarioModel.getId());
		}

	}

	public FuncionarioModel updateFuncionarioModel(FuncionarioModel FuncionarioModel) {

		if (funcionarioRepository.existsById(FuncionarioModel.getId()) == true) {
			return funcionarioRepository.save(FuncionarioModel);
		} else {
			throw new UnmatchingIdsException("id inexistente " + FuncionarioModel.getId());
		}
	}

	public boolean validarSenha(String login, String password) {
		Optional<FuncionarioModel> optUsuario = funcionarioRepository.findByLogin(login);
		if (optUsuario.isEmpty()) {
			return false;
		}

		FuncionarioModel usuario = optUsuario.get();
		return enconder.matches(password, usuario.getPassword());
	}
}
