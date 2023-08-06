package project.neki.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import exceptions.UnmatchingIdsException;
import project.neki.dtos.SkillInfoDTO;
import project.neki.model.FuncionarioModel;
import project.neki.model.SkillModel;
import project.neki.repository.FuncionarioRepository;
import project.neki.repository.SkillRepository;

@Service
public class FuncionarioService {

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	PasswordEncoder enconder;

	@Autowired
	SkillRepository skillRepository;

	public FuncionarioService(FuncionarioRepository funcionarioRepository, PasswordEncoder enconder) {
		this.funcionarioRepository = funcionarioRepository;
		this.enconder = enconder;
	}

	public List<FuncionarioModel> getAllFuncionarioModels() {
		return funcionarioRepository.findAll();
	}

	public FuncionarioModel getFuncionarioModelById(Long id) {
		return funcionarioRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Id de funcionario Invalido " + id));
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

	public FuncionarioModel validarSenha(String login, String password) {
		Optional<FuncionarioModel> optUsuario = funcionarioRepository.findByLogin(login);
		System.out.println("Entrei");
		if (optUsuario.isEmpty()) {
			return null;
		}
		FuncionarioModel usuario = optUsuario.get();

		// 4. Verificar se a senha fornecida corresponde à senha armazenada após a
		// codificação
		if (enconder.matches(password, usuario.getPassword())) {
			return usuario; // Senha correta, retorna o objeto FuncionarioModel
		}

		return null;
	}
}
