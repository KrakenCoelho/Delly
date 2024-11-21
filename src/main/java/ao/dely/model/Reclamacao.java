package ao.dely.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Reclamacao {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_rec;
	
	//@ManyToOne(optional=true)
	@JoinColumn(name="rcliente_id",foreignKey=@ForeignKey(name="cre",foreignKeyDefinition = "FOREIGN KEY (rcliente_id) REFERENCES Cliente(id) ON DELETE CASCADE ON UPDATE CASCADE") )
	private Long rcliente_id;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date data;
	
	@Column
	private String mensagem;
	
	@Column
	private String nota;

	public Reclamacao() {
		super();
	}


	public Reclamacao(Long id_rec, Long rcliente_id, Date data, String mensagem, String nota) {
		super();
		this.id_rec = id_rec;
		this.rcliente_id = rcliente_id;
		this.data = data;
		this.mensagem = mensagem;
		this.nota = nota;
	}







	public Long getId_rec() {
		return id_rec;
	}



	public void setId_rec(Long id_rec) {
		this.id_rec = id_rec;
	}



	public Date getData() {
		return data;
	}



	public void setData(Date data) {
		this.data = data;
	}



	public String getMensagem() {
		return mensagem;
	}



	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	

	public String getNota() {
		return nota;
	}


	public void setNota(String nota) {
		this.nota = nota;
	}
	

	public Long getRcliente_id() {
		return rcliente_id;
	}


	public void setRcliente_id(Long rcliente_id) {
		this.rcliente_id = rcliente_id;
	}


	@Override
	public String toString() {
		return "Reclamacao [id_rec=" + id_rec + ", rcliente_id=" + rcliente_id + ", data=" + data + ", mensagem="
				+ mensagem + ", nota=" + nota + "]";
	}

	
	
}
