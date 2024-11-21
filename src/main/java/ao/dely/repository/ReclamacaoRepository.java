package ao.dely.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ao.dely.model.Reclamacao;

public interface ReclamacaoRepository extends JpaRepository<Reclamacao, Long> {
	
	
	   @Query(
			      value = "SELECT id_rec,mensagem,nota,reclamacao.data FROM reclamacao WHERE rcliente_id=?;",
			      nativeQuery = true
			   )
			   List<List> aval(Long cliente_id);

			   @Query(
			      value = "SELECT id_rec,mensagem,nota,reclamacao.data FROM reclamacao WHERE id_rec=?;",
			      nativeQuery = true
			   )
			   List<List> ind(Long cliente_id);

			   @Query(
			      value = "SELECT id_rec,mensagem,nota,reclamacao.data FROM reclamacao WHERE rcliente_id=:id AND mensagem LIKE %:search%;",
			      nativeQuery = true
			   )
			   List<List> procn(@Param("id") Long id, @Param("search") String search);

			   @Query(
			      value = "SELECT id_rec,mensagem,nota,reclamacao.data FROM reclamacao WHERE rcliente_id=:id AND reclamacao.data BETWEEN :inicio AND :fim",
			      nativeQuery = true
			   )
			   List<List> procif(@Param("id") Long id, @Param("inicio") String inicio, @Param("fim") String fim);

			   @Query(
			      value = "SELECT id_rec,mensagem,nota,reclamacao.data FROM reclamacao WHERE rcliente_id=:id AND mensagem LIKE %:search% AND reclamacao.data BETWEEN :inicio AND :fim",
			      nativeQuery = true
			   )
			   List<List> pr(@Param("id") Long id, @Param("search") String search, @Param("inicio") String inicio, @Param("fim") String fim);

			   @Query(
			      value = "SELECT id_rec,mensagem,nota,reclamacao.data FROM reclamacao WHERE rcliente_id=? LIMIT 5;",
			      nativeQuery = true
			   )
			   List<List> avalr(Long cliente_id);

			   @Query(
			      value = "SELECT COUNT(*) FROM `reclamacao` WHERE DATE(reclamacao.data)= CURRENT_DATE AND rcliente_id=?",
			      nativeQuery = true
			   )
			   int countrecla(Long cliente_id);
			}
