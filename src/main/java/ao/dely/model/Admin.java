package ao.dely.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Admin {
		
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String nome;
	@Column
	private String email;
	@Column
	private String palavrapasse;
	@Column
	private String imagem;
	@Column
	private String nivel;
	@Column
	private String telefone;
	
	
	public Admin() {

	}

	
	
	
	public Admin(Long id, String nome, String email, String palavrapasse, String imagem, String nivel,
			String telefone) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.palavrapasse = palavrapasse;
		this.imagem = imagem;
		this.nivel = nivel;
		this.telefone = telefone;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPalavrapasse() {
		return palavrapasse;
	}

	public void setPalavrapasse(String palavrapasse) {
		this.palavrapasse = palavrapasse;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	@Override
	public String toString() {
		return "Admin [id=" + id + ", nome=" + nome + ", email=" + email + ", palavrapasse=" + palavrapasse
				+ ", imagem=" + imagem + ", nivel=" + nivel + ", telefone=" + telefone + "]";
	}

}
