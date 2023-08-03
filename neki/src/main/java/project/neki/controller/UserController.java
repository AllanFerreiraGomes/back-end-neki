package project.neki.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import project.neki.model.UserModel;
import project.neki.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<List<UserModel>> getAllUserModels() {
		return new ResponseEntity<>(userService.getAllUserModels(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserModel> getUserModelById(@PathVariable Long id) {
		return new ResponseEntity<>(userService.getUserModelById(id), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<UserModel> saveUserModel(@Valid @RequestBody UserModel UserModel) {
		UserModel UserModelResponse = userService.saveUserModel(UserModel);
		if (UserModelResponse == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(UserModelResponse, HttpStatus.OK);
		}
	}

	@PutMapping
	public ResponseEntity<UserModel> updateUserModel(@RequestBody UserModel UserModel) {

		UserModel UserModelResponse = userService.updateUserModel(UserModel);
		if (UserModelResponse == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_MODIFIED);
		} else {
			return new ResponseEntity<>(UserModelResponse, HttpStatus.OK);
		}
	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteUserModel(@PathVariable String id) {
//		Boolean response = userService(id);
//		if (response) {
//			return ResponseEntity.ok("UserModel deletado com Sucesso!");
//
//		} else {
//			return ResponseEntity.badRequest().body("Deu ruim");
//		}
//	}

	@PostMapping("/validarSenha")
	public ResponseEntity<Boolean> validarSenha(@RequestBody Map<String, String> requestBody) {
	    String login = requestBody.get("login");
	    String password = requestBody.get("password");

	boolean valid = userService.validarSenha(login, password);
		HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
		return ResponseEntity.status(status).body(valid);
	}

}
