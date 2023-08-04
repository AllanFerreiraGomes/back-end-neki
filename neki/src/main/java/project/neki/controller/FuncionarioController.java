package project.neki.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.neki.dtos.AssociarSkillsDTO;
import project.neki.dtos.SkillInfoDTO;
import project.neki.model.FuncionarioModel;
import project.neki.services.FuncionarioService;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	FuncionarioService funcionarioService;

	@GetMapping
	public ResponseEntity<List<FuncionarioModel>> getAllFuncionarioModels() {
		return new ResponseEntity<>(funcionarioService.getAllFuncionarioModels(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<FuncionarioModel> getFuncionarioModelById(@PathVariable Long id) {
		return new ResponseEntity<>(funcionarioService.getFuncionarioModelById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<FuncionarioModel> saveFuncionarioModel(@RequestBody FuncionarioModel FuncionarioModel) {
		FuncionarioModel FuncionarioModelResponse = funcionarioService.saveFuncionarioModel(FuncionarioModel);
		if (FuncionarioModelResponse == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(FuncionarioModelResponse, HttpStatus.OK);
		}
	}

	@PutMapping
	public ResponseEntity<FuncionarioModel> updateFuncionarioModel(@RequestBody FuncionarioModel FuncionarioModel) {

		FuncionarioModel FuncionarioModelResponse = funcionarioService.updateFuncionarioModel(FuncionarioModel);
		if (FuncionarioModelResponse == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(FuncionarioModelResponse, HttpStatus.OK);
		}
	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteFuncionarioModel(@PathVariable String id) {
//		Boolean response = funcionarioService(id);
//		if (response) {
//			return ResponseEntity.ok("FuncionarioModel deletado com Sucesso!");
//
//		} else {
//			return ResponseEntity.badRequest().body("Deu ruim");
//		}
//	}

	@PostMapping("/validarSenha")
	public ResponseEntity<Boolean> validarSenha(@RequestBody Map<String, String> requestBody) {
		String login = requestBody.get("login");
		String password = requestBody.get("password");

		boolean valid = funcionarioService.validarSenha(login, password);
		HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
		return ResponseEntity.status(status).body(valid);
	}

	@GetMapping("/dto/{id}")
	public ResponseEntity<List<SkillInfoDTO>> listarSkillsFuncionario(@PathVariable Long id) {
		List<SkillInfoDTO> skillInfoList = funcionarioService.listarSkillsFuncionario(id);
		return new ResponseEntity<>(skillInfoList, HttpStatus.OK);
	}

	@PostMapping("/{funcionarioId}/skills")
	public ResponseEntity<Void> associarSkillsAoFuncionario(@PathVariable Long funcionarioId,
	        @RequestBody AssociarSkillsDTO associarSkillsDTO) {

	    funcionarioService.associarSkillsAoFuncionario(funcionarioId,
	            associarSkillsDTO.getSkillIds(), associarSkillsDTO.getLevel());

	    return new ResponseEntity<>(HttpStatus.CREATED);
	}
}

