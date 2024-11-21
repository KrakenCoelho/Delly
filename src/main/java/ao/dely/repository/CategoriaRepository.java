package ao.dely.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ao.dely.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria,Long>  {
	
	  @Query(
		      value = "SELECT id_cat,nome_cat,(SELECT COUNT(*) FROM artigo WHERE acategoria_id=id_cat) FROM `categoria` WHERE cliente_id=?;",
		      nativeQuery = true
		   )
		   List<List> categoriaesp(Long cliente_id);

		   @Query(
		      value = "SELECT id_cat,nome_cat FROM `categoria` WHERE id_cat=?",
		      nativeQuery = true
		   )
		   List<List> categorianomeId(Long id_cat);

		   @Query(
		      value = "SELECT id_cat,nome_cat,imagem_cat FROM categoria,cliente WHERE id=cliente_id  AND cliente_id=?",
		      nativeQuery = true
		   )
		   List<List> categorianomeId2(Long cliente_id);

		   @Query(
		      value = "SELECT id_cat,nome_cat,cliente_id,imagem_cat,logomarca FROM categoria,cliente WHERE id=cliente_id AND id_cat=?",
		      nativeQuery = true
		   )
		   List<List> ccj(Long id_cat);

		   @Query(
		      value = "SELECT * FROM categoria WHERE id_cat=?",
		      nativeQuery = true
		   )
		   Iterable<Categoria> findid(Long cliente_id);
		}

