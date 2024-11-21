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
public class Visitas {
   @Id
   @GeneratedValue(
      strategy = GenerationType.AUTO
   )
   private Long id_v;
   @Column
   private String ip;
   @Temporal(TemporalType.DATE)
   @DateTimeFormat(
      pattern = "yyyy-MM-dd"
   )
   private Date data_v;
   @JoinColumn(
      name = "vcli_id",
      foreignKey = @ForeignKey(
   name = "vcli",
   foreignKeyDefinition = "FOREIGN KEY (vcli_id) REFERENCES cliente(id) ON DELETE CASCADE ON UPDATE CASCADE"
)
   )
   private Long vcli_id;

   public Visitas() {
   }

   public void setIp(String ip) {
      this.ip = ip;
   }

   public void setData_v(Date data_v) { 
      this.data_v = data_v;
   }

   public void setVcli_id(Long vcli_id) {
      this.vcli_id = vcli_id;
   }
}
