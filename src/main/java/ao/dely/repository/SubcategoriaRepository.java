package ao.dely.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ao.dely.model.Subcategoria;

public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
	
	   @Query(
			      value = "SELECT id_scat,nome_scat, (SELECT COUNT(*) FROM artigo WHERE asubcategoria_id=id_scat) FROM `subcategoria` WHERE categoria_id=?",
			      nativeQuery = true
			   )
			   List<List> subcategorianomeId(Long categoria_id);

			   @Query(
			      value = "SELECT id_scat,nome_scat,categoria_id,nome_cat FROM subcategoria JOIN categoria ON categoria_id=id_cat WHERE id_scat=?",
			      nativeQuery = true
			   )
			   List<List> subfindbyid(Long subcategoria_id);

			   @Query(
			      value = "SELECT id_scat,nome_scat FROM subcategoria,categoria,cliente WHERE categoria_id=id_cat AND cliente_id=id AND id=?",
			      nativeQuery = true
			   )
			   List<List> subes(Long cliente_id);
			}
