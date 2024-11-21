package ao.dely.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Cliente {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String nome;
	@Column
	private String mensagem;
	@Column
	private String descricao;
	@Column
	private String email;
	@Column
	private String palavrapasse;
	@Column
	private String logomarca;
	@Column
	private String telefone;
	@Column
	private String rep_code;
	
	@Column
	private String fundo;
	
	@Column
	private String estado;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data_sub;
	
	 
	   @Column
	   private int prim;

	   public Cliente() {
	   }

	   public Cliente(Long id, String nome, String mensagem, String descricao, String email, String palavrapasse, String logomarca, String telefone, String rep_code, String fundo, String estado, Date data_sub, int prim) {
	      this.id = id;
	      this.nome = nome;
	      this.mensagem = mensagem;
	      this.descricao = descricao;
	      this.email = email;
	      this.palavrapasse = palavrapasse;
	      this.logomarca = logomarca;
	      this.telefone = telefone;
	      this.rep_code = rep_code;
	      this.fundo = fundo;
	      this.estado = estado;
	      this.data_sub = data_sub;
	      this.prim = prim;
	   }

	   public Long getId() {
	      return this.id;
	   }

	   public void setId(Long id) {
	      this.id = id;
	   }

	   public String getNome() {
	      return this.nome;
	   }

	   public void setNome(String nome) {
	      this.nome = nome;
	   }

	   public String getMensagem() {
	      return this.mensagem;
	   }

	   public void setMensagem(String mensagem) {
	      this.mensagem = mensagem;
	   }

	   public String getDescricao() {
	      return this.descricao;
	   }

	   public void setDescricao(String descricao) {
	      this.descricao = descricao;
	   }

	   public String getEmail() {
	      return this.email;
	   }

	   public void setEmail(String email) {
	      this.email = email;
	   }

	   public String getPalavrapasse() {
	      return this.palavrapasse;
	   }

	   public void setPalavrapasse(String palavrapasse) {
	      this.palavrapasse = palavrapasse;
	   }

	   public String getLogomarca() {
	      return this.logomarca;
	   }

	   public void setLogomarca(String logomarca) {
	      this.logomarca = logomarca;
	   }

	   public String getTelefone() {
	      return this.telefone;
	   }

	   public void setTelefone(String telefone) {
	      this.telefone = telefone;
	   }

	   public String getRep_code() {
	      return this.rep_code;
	   }

	   public void setRep_code(String rep_code) {
	      this.rep_code = rep_code;
	   }

	   public String getFundo() {
	      return this.fundo;
	   }

	   public void setFundo(String fundo) {
	      this.fundo = fundo;
	   }

	   public String getEstado() {
	      return this.estado;
	   }

	   public void setEstado(String estado) {
	      this.estado = estado;
	   }

	   public Date getData_sub() {
	      return this.data_sub;
	   }

	   public void setData_sub(Date data_sub) {
	      this.data_sub = data_sub;
	   }

	   public int getPrim() {
	      return this.prim;
	   }

	   public void setPrim(int prim) {
	      this.prim = prim;
	   }

	   public String toString() {
	      return "Cliente [id=" + this.id + ", nome=" + this.nome + ", mensagem=" + this.mensagem + ", descricao=" + this.descricao + ", email=" + this.email + ", palavrapasse=" + this.palavrapasse + ", logomarca=" + this.logomarca + ", telefone=" + this.telefone + ", rep_code=" + this.rep_code + ", fundo=" + this.fundo + ", estado=" + this.estado + ", data_sub=" + this.data_sub + ", prim=" + this.prim + "]";
	   }
	}
