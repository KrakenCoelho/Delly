package ao.dely.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ao.dely.model.Visitas;

public interface VisitasRepository extends JpaRepository<Visitas, Long> {
	   @Query(
	      value = "SELECT COUNT(*) FROM visitas WHERE DATE(data_v)=CURRENT_DATE AND ip LIKE ? AND vcli_id = ?",
	      nativeQuery = true
	   )
	   int seexistehoje(String ip, Long id_cliente);

	   @Query(
	      value = "SELECT COUNT(*) FROM visitas WHERE DATE(data_v)=CURRENT_DATE AND vcli_id = ?",
	      nativeQuery = true
	   )
	   int visitashoje(Long id_cliente);
	}
