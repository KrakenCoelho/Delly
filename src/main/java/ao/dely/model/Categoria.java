package ao.dely.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_cat;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="cliente_id",foreignKey=@ForeignKey(name="ccat",foreignKeyDefinition = "FOREIGN KEY (cliente_id) REFERENCES Cliente(id) ON DELETE CASCADE ON UPDATE CASCADE") )
	private Cliente cliente_id;
	
	@Column
	private String nome_cat;
	
	@Column
	private String imagem_cat;

	public Categoria() {
		
	}

	

	public Categoria(Long id_cat, Cliente cliente_id, String nome_cat, String imagem_cat) {
		super();
		this.id_cat = id_cat;
		this.cliente_id = cliente_id;
		this.nome_cat = nome_cat;
		this.imagem_cat = imagem_cat;
	}



	public Long getId_cat() {
		return id_cat;
	}

	public void setId_cat(Long id_cat) {
		this.id_cat = id_cat;
	}

	public Cliente getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Cliente cliente_id) {
		this.cliente_id = cliente_id;
	}
	
	

	public String getImagem_cat() {
		return imagem_cat;
	}

	public void setImagem_cat(String imagem_cat) {
		this.imagem_cat = imagem_cat;
	}

	
	
	public String getNome_cat() {
		return nome_cat;
	}



	public void setNome_cat(String nome_cat) {
		this.nome_cat = nome_cat;
	}



	@Override
	public String toString() {
		return "Categoria [id_cat=" + id_cat + ", cliente_id=" + cliente_id + ", nome_cat=" + nome_cat + ", imagem_cat="
				+ imagem_cat + "]";
	}



	
	
	
	
	
	
}
