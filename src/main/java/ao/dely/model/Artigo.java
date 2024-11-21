package ao.dely.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Artigo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_ar;
	@Column
	private String nome_ar;
	@Column
	private String preco;
	@Column
	private String descricao_ar;
	
	//@ManyToOne(optional=true)
	@JoinColumn(name="acategoria_id",foreignKey=@ForeignKey(name="acat",foreignKeyDefinition = "FOREIGN KEY (acategoria_id) REFERENCES Categoria(id_cat) ON DELETE CASCADE ON UPDATE CASCADE") )
	private Long acategoria_id;
	
	//@ManyToOne(optional=true)
	@JoinColumn(name="asubcategoria_id",foreignKey=@ForeignKey(name="ascat",foreignKeyDefinition = "FOREIGN KEY (asubcategoria_id) REFERENCES Subcategoria(id_scat) ON DELETE CASCADE ON UPDATE CASCADE") )
	private Long asubcategoria_id;
	
	@Column
	private String disponivel;
	
	
	

	public Artigo() {
		super();
	}
	
	
	
	
	public Artigo(Long id_ar, String nome_ar, String preco, String descricao_ar, Long acategoria_id,
			Long asubcategoria_id, String disponivel) {
		super();
		this.id_ar = id_ar;
		this.nome_ar = nome_ar;
		this.preco = preco;
		this.descricao_ar = descricao_ar;
		this.acategoria_id = acategoria_id;
		this.asubcategoria_id = asubcategoria_id;
		this.disponivel = disponivel;
	}




	public Long getId_ar() {
		return id_ar;
	}

	public void setId_ar(Long id_ar) {
		this.id_ar = id_ar;
	}

	public String getNome_ar() {
		return nome_ar;
	}

	public void setNome_ar(String nome_ar) {
		this.nome_ar = nome_ar;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public String getDescricao_ar() {
		return descricao_ar;
	}

	public void setDescricao_ar(String descricao_ar) {
		this.descricao_ar = descricao_ar;
	}

	public Long getAcategoria_id() {
		return acategoria_id;
	}

	public void setAcategoria_id(Long acategoria_id) {
		this.acategoria_id = acategoria_id;
	}

	public Long getAsubcategoria_id() {
		return asubcategoria_id;
	}

	public void setAsubcategoria_id(Long asubcategoria_id) {
		this.asubcategoria_id = asubcategoria_id;
	}

	public String getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(String disponivel) {
		this.disponivel = disponivel;
	}




	@Override
	public String toString() {
		return "Artigo [id_ar=" + id_ar + ", nome_ar=" + nome_ar + ", preco=" + preco + ", descricao_ar=" + descricao_ar
				+ ", acategoria_id=" + acategoria_id + ", asubcategoria_id=" + asubcategoria_id + ", disponivel="
				+ disponivel + "]";
	}
	
	
	
	
	
}
