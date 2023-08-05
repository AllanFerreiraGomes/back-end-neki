package project.neki.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.neki.dtos.FuncionarioSkillDTO;
import project.neki.dtos.FuncionarioSkillListDTO;
import project.neki.dtos.SkillIdDTO;
import project.neki.dtos.SkillInfoDTO;
import project.neki.model.FuncionarioModel;
import project.neki.model.FuncionarioSkill;
import project.neki.model.SkillModel;
import project.neki.repository.FuncionarioRepository;
import project.neki.repository.FuncionarioSkillRepository;
import project.neki.repository.SkillRepository;

@Service
public class FuncionarioSkillService {

	private final FuncionarioSkillRepository funcionarioSkillRepository;
	private final FuncionarioRepository funcionarioRepository;
	private final SkillRepository skillRepository;

	@Autowired
	public FuncionarioSkillService(FuncionarioSkillRepository funcionarioSkillRepository,
			FuncionarioRepository funcionarioRepository, SkillRepository skillRepository) {
		this.funcionarioSkillRepository = funcionarioSkillRepository;
		this.funcionarioRepository = funcionarioRepository;
		this.skillRepository = skillRepository;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Transactional
	public void associarSkillAoFuncionario(FuncionarioModel funcionario, FuncionarioSkillDTO skillDTO) {
		SkillModel skill = skillRepository.findById(skillDTO.getSkillId())
				.orElseThrow(() -> new NoSuchElementException("Id da skill não válido"));

		FuncionarioSkill funcionarioSkill = new FuncionarioSkill(funcionario, skill, skillDTO.getLevel());
		funcionarioSkillRepository.save(funcionarioSkill);
	}



	public Boolean associarSkillFuncionario(Long funcionarioId, FuncionarioSkillListDTO funcionarioSkillListDTO) {
	    FuncionarioModel funcionarioModel = funcionarioRepository.findById(funcionarioId)
	            .orElseThrow(() -> new NoSuchElementException("Id do funcionario não válido"));

	    System.out.println("NOME DO FUNCIONARIO " + funcionarioModel.getName());

	    for (Long skillId : funcionarioSkillListDTO.getSkillIds()) {
	        SkillModel skill = skillRepository.findById(skillId)
	                .orElseThrow(() -> new NoSuchElementException("Id da skill não válido"));

	        System.out.println("NOME DA SKILL " + skillId);

	        // Verifica se a associação já existe
	        boolean associationExists = funcionarioSkillRepository.existsByFuncionarioAndSkill(funcionarioModel, skill);
	        if (!associationExists) {
	            System.out.println("ENTREIII");
	            FuncionarioSkill funcionarioSkill = new FuncionarioSkill(funcionarioModel, skill,
	                    funcionarioSkillListDTO.getLevel());

	            System.out.println("2");
	            funcionarioSkillRepository.save(funcionarioSkill);

				System.out.println("3");
	        }
	    }

	    return true;
	}
 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Transactional
	public void atualizarNivelSkillDoFuncionario(Long funcionarioId, FuncionarioSkillDTO skillDTO) {
		FuncionarioModel funcionario = getFuncionarioById(funcionarioId);
		SkillModel skill = getSkillById(skillDTO.getSkillId());

		FuncionarioSkill funcionarioSkill = funcionarioSkillRepository.findByFuncionarioAndSkill(funcionario, skill)
				.orElseThrow(() -> new NoSuchElementException("Associação entre funcionário e skill não encontrada."));

		funcionarioSkill.setLevel(skillDTO.getLevel());
		funcionarioSkillRepository.save(funcionarioSkill);
	}

	@Transactional
	public void excluirAssociacaoSkillDoFuncionario(Long funcionarioId, SkillIdDTO skillIdDTO) {
		FuncionarioModel funcionario = getFuncionarioById(funcionarioId);
		SkillModel skill = getSkillById(skillIdDTO.getSkillId());

		FuncionarioSkill funcionarioSkill = funcionarioSkillRepository.findByFuncionarioAndSkill(funcionario, skill)
				.orElseThrow(() -> new NoSuchElementException("Associação entre funcionário e skill não encontrada."));

		funcionario.getFuncionarioSkills().remove(funcionarioSkill);
		skill.getFuncionarioSkills().remove(funcionarioSkill);
		funcionarioSkillRepository.delete(funcionarioSkill);
	}

	private FuncionarioModel getFuncionarioById(Long funcionarioId) {
		return funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new NoSuchElementException("Funcionario with ID " + funcionarioId + " not found"));
	}

	private SkillModel getSkillById(Long skillId) {
		return skillRepository.findById(skillId)
				.orElseThrow(() -> new NoSuchElementException("Skill with ID " + skillId + " not found"));
	}

	@Transactional(readOnly = true)
	public List<SkillInfoDTO> listarSkillsFuncionario(Long funcionarioId) {
		FuncionarioModel funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new NoSuchElementException("Id: [" + funcionarioId + "] do funcionario não válido"));

		return funcionario.getFuncionarioSkills().stream()
				.map(funcionarioSkill -> new SkillInfoDTO(funcionarioSkill.getSkill().getId(),
						funcionarioSkill.getSkill().getName(), funcionarioSkill.getLevel()))
				.collect(Collectors.toList());
	}

}
