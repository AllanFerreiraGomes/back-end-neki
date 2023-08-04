package project.neki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import project.neki.dtos.SkillInfoDTO;
import project.neki.services.FuncionarioSkillService;

@RestController
@RequestMapping("/api/funcionarios/{funcionarioId}/skills")
public class FuncionarioSkillController {

	@Autowired
	FuncionarioSkillService funcionarioSkillService;

	@Autowired
	public FuncionarioSkillController(FuncionarioSkillService funcionarioSkillService) {
		this.funcionarioSkillService = funcionarioSkillService;
	}

	@PostMapping("/{skillId}")
	public ResponseEntity<Void> associarSkillAoFuncionario(@PathVariable Long funcionarioId, @PathVariable Long skillId,
			@RequestParam Integer level) {
		funcionarioSkillService.associarSkillAoFuncionario(funcionarioId, skillId, level);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PutMapping("/{skillId}")
	public ResponseEntity<Void> atualizarNivelSkillDoFuncionario(@PathVariable Long funcionarioId,
			@PathVariable Long skillId, @RequestParam Integer level) {
		funcionarioSkillService.atualizarNivelSkillDoFuncionario(funcionarioId, skillId, level);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/{skillId}")
	public ResponseEntity<Void> excluirAssociacaoSkillDoFuncionario(@PathVariable Long funcionarioId,
			@PathVariable Long skillId) {
		funcionarioSkillService.excluirAssociacaoSkillDoFuncionario(funcionarioId, skillId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<List<SkillInfoDTO>> listarSkillsFuncionario(@PathVariable Long funcionarioId) {
		List<SkillInfoDTO> skills = funcionarioSkillService.listarSkillsFuncionario(funcionarioId);
		return new ResponseEntity<>(skills, HttpStatus.OK);
	}
}
