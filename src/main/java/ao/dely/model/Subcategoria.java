package ao.dely.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
 
@Entity
public class Subcategoria {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_scat;
	
	//@ManyToOne(optional=true)
	@JoinColumn(name="Categoria_id",foreignKey=@ForeignKey(name="scat",foreignKeyDefinition = "FOREIGN KEY (categoria_id) REFERENCES Categoria(id_cat) ON DELETE CASCADE ON UPDATE CASCADE") )
	private Long categoria_id;
	
	@Column
	private String nome_scat;

	public Subcategoria() {
		super();
	}

	public Subcategoria(Long id_scat, Long categoria_id, String nome_scat) {
		super();
		this.id_scat = id_scat;
		this.categoria_id = categoria_id;
		this.nome_scat = nome_scat;
	}

	public Long getId_scat() {
		return id_scat;
	}

	public void setId_scat(Long id_scat) {
		this.id_scat = id_scat;
	}

	public Long getCategoria_id() {
		return categoria_id;
	}

	public void setCategoria_id(Long categoria_id) {
		this.categoria_id = categoria_id;
	}

	public String getNome_scat() {
		return nome_scat;
	}

	public void setNome_scat(String nome_scat) {
		this.nome_scat = nome_scat;
	}

	@Override
	public String toString() {
		return "Subcategoria [id_scat=" + id_scat + ", categoria_id=" + categoria_id + ", nome_scat=" + nome_scat + "]";
	}
	
	

}
