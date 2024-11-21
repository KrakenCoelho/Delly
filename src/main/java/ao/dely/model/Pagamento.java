package ao.dely.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Pagamento {
	
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id_pag;
	
	 @Column
	 private Long cliente_id;
	 
	 @Column
	 private String cliente_numero;
	 
	 @Column
	 private String entidade;
	 
	 @Column
	 private String referencia;
	 
	 @Column
	 private String tipo;
	 
	 @Column
	 private String estatuto;
	 
	 @Temporal(TemporalType.DATE)
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	 private Date data_exp;
	 
	 @Temporal(TemporalType.DATE)
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	 private Date data_criacaoo;
	 
	 @JoinColumn(name="pacote_id",foreignKey=@ForeignKey(name="pagpac",foreignKeyDefinition = "FOREIGN KEY (pacote_id) REFERENCES pacotes(id_pac) ON DELETE CASCADE ON UPDATE CASCADE") )
	 private Long pacote_id;
	 
	public Pagamento() {
		super();
	}

	

	public Pagamento(Long id_pag, Long cliente_id, String cliente_numero, String entidade, String referencia,
			String tipo, String estatuto, Date data_exp, Date data_criacaoo, Long pacote_id) {
		super();
		this.id_pag = id_pag;
		this.cliente_id = cliente_id;
		this.cliente_numero = cliente_numero;
		this.entidade = entidade;
		this.referencia = referencia;
		this.tipo = tipo;
		this.estatuto = estatuto;
		this.data_exp = data_exp;
		this.data_criacaoo = data_criacaoo;
		this.pacote_id = pacote_id;
	}



	public Long getId_pag() {
		return id_pag;
	}

	public void setId_pag(Long id_pag) {
		this.id_pag = id_pag;
	}

	public Long getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Long cliente_id) {
		this.cliente_id = cliente_id;
	}

	public String getCliente_numero() {
		return cliente_numero;
	}

	public void setCliente_numero(String cliente_numero) {
		this.cliente_numero = cliente_numero;
	}

	public String getEntidade() {
		return entidade;
	}

	public void setEntidade(String entidade) {
		this.entidade = entidade;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstatuto() {
		return estatuto;
	}

	public void setEstatuto(String estatuto) {
		this.estatuto = estatuto;
	}

	public Date getData_exp() {
		return data_exp;
	}

	public void setData_exp(Date data_exp) {
		this.data_exp = data_exp;
	}

	public Date getData_criacaoo() {
		return data_criacaoo;
	}

	public void setData_criacaoo(Date data_criacaoo) {
		this.data_criacaoo = data_criacaoo;
	}

		
	public Long getPacote_id() {
		return pacote_id;
	}

	public void setPacote_id(Long pacote_id) {
		this.pacote_id = pacote_id;
	}



	@Override
	public String toString() {
		return "Pagamento [id_pag=" + id_pag + ", cliente_id=" + cliente_id + ", cliente_numero=" + cliente_numero
				+ ", entidade=" + entidade + ", referencia=" + referencia + ", tipo=" + tipo + ", estatuto=" + estatuto
				+ ", data_exp=" + data_exp + ", data_criacaoo=" + data_criacaoo + ", pacote_id=" + pacote_id + "]";
	}



	
	 
	
	
	
	

}
