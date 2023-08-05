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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.neki.dtos.FuncionarioSkillDTO;
import project.neki.dtos.FuncionarioSkillListDTO;
import project.neki.dtos.SkillIdDTO;
import project.neki.dtos.SkillInfoDTO;
import project.neki.services.FuncionarioSkillService;

@RestController
@RequestMapping("/funcionarios/{funcionarioId}/skills")
public class FuncionarioSkillController {
	
	@Autowired
	 FuncionarioSkillService funcionarioSkillService;
	

	@PostMapping("/associar-skills")
	public ResponseEntity<Void> associarSkillsAoFuncionario(@PathVariable Long funcionarioId,
			@RequestBody FuncionarioSkillListDTO funcionarioSkillListDTO) {
		funcionarioSkillService.associarSkillFuncionario(funcionarioId, funcionarioSkillListDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	

	@PutMapping("/atualizar")
	public ResponseEntity<Void> atualizarNivelSkillDoFuncionario(@PathVariable Long funcionarioId,
			@RequestBody FuncionarioSkillDTO skillDTO) {
		funcionarioSkillService.atualizarNivelSkillDoFuncionario(funcionarioId, skillDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/excluir")
	public ResponseEntity<Void> excluirAssociacaoSkillDoFuncionario(@PathVariable Long funcionarioId,
			@RequestBody SkillIdDTO skillIdDTO) {
		funcionarioSkillService.excluirAssociacaoSkillDoFuncionario(funcionarioId, skillIdDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/listar")
	public ResponseEntity<List<SkillInfoDTO>> listarSkillsFuncionario(@PathVariable Long funcionarioId) {
		List<SkillInfoDTO> skills = funcionarioSkillService.listarSkillsFuncionario(funcionarioId);
		return new ResponseEntity<>(skills, HttpStatus.OK);
	}
}
