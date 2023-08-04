package project.neki.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.neki.dtos.SkillInfoDTO;
import project.neki.model.FuncionarioModel;
import project.neki.model.FuncionarioSkill;
import project.neki.model.SkillModel;
import project.neki.repository.FuncionarioRepository;
import project.neki.repository.FuncionarioSkillRepository;
import project.neki.repository.SkillRepository;

@Service
public class FuncionarioSkillService {

	@Autowired
	FuncionarioSkillRepository funcionarioSkillRepository;

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	SkillRepository skillRepository;

	@Autowired
	public FuncionarioSkillService(FuncionarioSkillRepository funcionarioSkillRepository,
			FuncionarioRepository funcionarioRepository, SkillRepository skillRepository) {
		this.funcionarioSkillRepository = funcionarioSkillRepository;
		this.funcionarioRepository = funcionarioRepository;
		this.skillRepository = skillRepository;
	}

	@Transactional
	public void associarSkillAoFuncionario(Long funcionarioId, Long skillId, Integer level) {
		FuncionarioModel funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new NoSuchElementException("Id do funcionario não válido"));

		SkillModel skill = skillRepository.findById(skillId)
				.orElseThrow(() -> new NoSuchElementException("Id da skill não válido"));

		// Verifica se já existe uma associação entre o funcionário e a skill
		if (funcionarioSkillRepository.existsByFuncionarioAndSkill(funcionario, skill)) {
			throw new IllegalArgumentException("O funcionário já possui essa skill associada.");
		}

		FuncionarioSkill funcionarioSkill = new FuncionarioSkill(funcionario, skill, level);
		funcionarioSkillRepository.save(funcionarioSkill);
	}

	@Transactional
	public void atualizarNivelSkillDoFuncionario(Long funcionarioId, Long skillId, Integer level) {
		FuncionarioModel funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new NoSuchElementException("Id do funcionario não válido"));

		SkillModel skill = skillRepository.findById(skillId)
				.orElseThrow(() -> new NoSuchElementException("Id da skill não válido"));

		FuncionarioSkill funcionarioSkill = funcionarioSkillRepository.findByFuncionarioAndSkill(funcionario, skill)
				.orElseThrow(() -> new NoSuchElementException("Associação entre funcionário e skill não encontrada."));

		funcionarioSkill.setLevel(level);
		funcionarioSkillRepository.save(funcionarioSkill);
	}

	@Transactional
	public void excluirAssociacaoSkillDoFuncionario(Long funcionarioId, Long skillId) {
		FuncionarioModel funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new NoSuchElementException("Id do funcionario não válido"));

		SkillModel skill = skillRepository.findById(skillId)
				.orElseThrow(() -> new NoSuchElementException("Id da skill não válido"));

		FuncionarioSkill funcionarioSkill = funcionarioSkillRepository.findByFuncionarioAndSkill(funcionario, skill)
				.orElseThrow(() -> new NoSuchElementException("Associação entre funcionário e skill não encontrada."));

		funcionario.getFuncionarioSkills().remove(funcionarioSkill);
		skill.getFuncionarioSkills().remove(funcionarioSkill);
		funcionarioSkillRepository.delete(funcionarioSkill);
	}

	public FuncionarioSkillService(FuncionarioRepository funcionarioRepository,
			FuncionarioSkillRepository funcionarioSkillRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.funcionarioSkillRepository = funcionarioSkillRepository;
	}

	public List<SkillInfoDTO> listarSkillsFuncionario(Long funcionarioId) {
		FuncionarioModel funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new NoSuchElementException("Id do funcionario não válido"));

		return funcionario.getFuncionarioSkills().stream()
				.map(funcionarioSkill -> new SkillInfoDTO(funcionarioSkill.getSkill().getId(),
						funcionarioSkill.getSkill().getName(), funcionarioSkill.getLevel()))
				.collect(Collectors.toList());
	}
}
