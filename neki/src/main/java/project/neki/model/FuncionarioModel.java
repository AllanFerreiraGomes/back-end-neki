package project.neki.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = FuncionarioModel.class)
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "funcionario")
public class FuncionarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(unique = true)
	private String login;

	@Column(name = "password")
	private String password; 
	
	@ManyToMany
	private List<SkillModel> skillList;

	public FuncionarioModel(String name, String login, String password, List<SkillModel> skillList) {
		this.name = name;
		this.login = login;
		this.password = password;
		this.skillList = skillList;
	}
	
}
