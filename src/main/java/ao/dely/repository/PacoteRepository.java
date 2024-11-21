package ao.dely.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ao.dely.model.Pacotes;

public interface PacoteRepository extends JpaRepository<Pacotes, Long> {
	  @Query(
		      value = "SELECT id_pac, duracao, preco_pac FROM pacotes",
		      nativeQuery = true
		   )
		   List<List> pacotes();

		   @Query(
		      value = "SELECT preco_pac FROM pacotes WHERE id_pac=?",
		      nativeQuery = true
		   )
		   Long valor(Long Id);
		}
