package ao.dely.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pacotes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_pac;
	@Column
	private String preco_pac;
	@Column
	private String duracao;

}
