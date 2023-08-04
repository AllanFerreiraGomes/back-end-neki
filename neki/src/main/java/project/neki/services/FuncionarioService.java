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

	public boolean validarSenha(String login, String password) {
		Optional<FuncionarioModel> optUsuario = funcionarioRepository.findByLogin(login);
		if (optUsuario.isEmpty()) {
			return false;
		}

		FuncionarioModel usuario = optUsuario.get();
		return enconder.matches(password, usuario.getPassword());
	}

	@Transactional(readOnly = true)
	public List<SkillInfoDTO> listarSkillsFuncionario(Long id) {
		FuncionarioModel funcionario = funcionarioRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Id: [" + id + "] do funcionario não valido"));

		return funcionario.getSkillList().stream()
				.map(skill -> new SkillInfoDTO(skill.getId(), skill.getName(), skill.getLevel()))
				.collect(Collectors.toList());
	}

	@Transactional
	public void associarSkillsAoFuncionario(Long funcionarioId, List<Long> skillIds, Integer level) {
	    FuncionarioModel funcionario = funcionarioRepository.findById(funcionarioId)
	            .orElseThrow(() -> new NoSuchElementException("Id do funcionario não valido"));

	    List<SkillModel> skills = skillRepository.findAllById(skillIds);

	    for (SkillModel skill : skills) {
	        funcionario.getSkillList().add(skill);
	        skill.getFuncionariosLista().add(funcionario);
	        skill.setLevel(level);
	    }

	    funcionarioRepository.save(funcionario);
	}

}
