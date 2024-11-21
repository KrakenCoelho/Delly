package ao.dely.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ao.dely.model.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
	
	  @Query(
		      value = "SELECT estatuto FROM pagamento WHERE cliente_id=? AND referencia=?",
		      nativeQuery = true
		   )
		   String re(Long id, String Referencia);

		   @Transactional
		   @Modifying
		   @Query(
		      value = "UPDATE pagamento SET estatuto=? WHERE referencia=?",
		      nativeQuery = true
		   )
		   int actuapag(String estatuto, String referencia);

		   @Query(
		      value = " SELECT cliente_id FROM pagamento WHERE referencia=?",
		      nativeQuery = true
		   )
		   Long idcliente(String referencia);
		}

