package ao.dely.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ao.dely.model.Artigo;

public interface ArtigoRepository extends JpaRepository<Artigo,Long> {

	  @Query(
		      value = "SELECT id_ar, nome_ar, preco, nome_scat \nFROM artigo \nLEFT JOIN subcategoria  ON asubcategoria_id = id_scat\nJOIN categoria  ON acategoria_id = id_cat\nJOIN cliente  ON cliente_id = id AND id=?",
		      nativeQuery = true
		   )
		   List<List> artigonomeId(Long cliente_id);

		   @Query(
		      value = "SELECT id_ar, nome_ar, preco, nome_scat \nFROM artigo \n LEFT JOIN subcategoria  ON asubcategoria_id = id_scat\nJOIN categoria  ON acategoria_id = id_cat AND id_cat=?",
		      nativeQuery = true
		   )
		   List<List> catartigonomeId(Long categoria_id);

		   @Query(
		      value = "SELECT id_ar, nome_ar, preco,descricao_ar, nome_scat, asubcategoria_id \nFROM artigo \n LEFT JOIN subcategoria  ON asubcategoria_id = id_scat\nJOIN categoria  ON acategoria_id = id_cat AND id_cat=?",
		      nativeQuery = true
		   )
		   List<List> pr(Long categoria_id);

		   @Query(
		      value = "SELECT id_ar,nome_ar,preco,descricao_ar,acategoria_id,nome_cat,asubcategoria_id,nome_scat FROM artigo JOIN categoria ON acategoria_id=id_cat LEFT JOIN subcategoria ON asubcategoria_id=id_scat WHERE id_ar=?",
		      nativeQuery = true
		   )
		   List<List> artfinbId(Long artigo_id);

		   @Query(
		      value = "SELECT id_ar, nome_ar, preco, nome_scat \nFROM artigo \nLEFT JOIN subcategoria  ON asubcategoria_id = id_scat\nJOIN categoria  ON acategoria_id = id_cat\nJOIN cliente  ON cliente_id = id AND id=:id AND nome_ar LIKE %:search% AND id_cat=:categoria AND id_scat=:subcategoria ",
		      nativeQuery = true
		   )
		   List<List> proctodos(@Param("id") Long id, @Param("search") String search, @Param("categoria") Long categoria, @Param("subcategoria") Long subcategoria);

		   @Query(
		      value = "SELECT id_ar, nome_ar, preco, nome_scat \nFROM artigo \nLEFT JOIN subcategoria  ON asubcategoria_id = id_scat\nJOIN categoria  ON acategoria_id = id_cat\nJOIN cliente  ON cliente_id = id AND id=:id AND nome_ar LIKE %:search% ",
		      nativeQuery = true
		   )
		   List<List> procnome(@Param("id") Long id, @Param("search") String search);

		   @Query(
		      value = "SELECT id_ar, nome_ar, preco, nome_scat \nFROM artigo \nLEFT JOIN subcategoria  ON asubcategoria_id = id_scat\nJOIN categoria  ON acategoria_id = id_cat\nJOIN cliente  ON cliente_id = id AND id=:id AND nome_ar LIKE %:search% AND id_cat=:categoria ",
		      nativeQuery = true
		   )
		   List<List> procnc(@Param("id") Long id, @Param("search") String search, @Param("categoria") Long categoria);

		   @Query(
		      value = "SELECT id_ar, nome_ar, preco, nome_scat \nFROM artigo \nLEFT JOIN subcategoria  ON asubcategoria_id = id_scat\nJOIN categoria  ON acategoria_id = id_cat\nJOIN cliente  ON cliente_id = id AND id=:id AND nome_ar LIKE %:search% AND id_scat=:subcategoria ",
		      nativeQuery = true
		   )
		   List<List> procns(@Param("id") Long id, @Param("search") String search, @Param("subcategoria") Long subcategoria);

		   @Query(
		      value = "SELECT id_ar, nome_ar, preco, nome_scat \nFROM artigo \nLEFT JOIN subcategoria  ON asubcategoria_id = id_scat\nJOIN categoria  ON acategoria_id = id_cat\nJOIN cliente  ON cliente_id = id AND id=:id AND id_scat=:subcategoria ",
		      nativeQuery = true
		   )
		   List<List> procs(@Param("id") Long id, @Param("subcategoria") Long subcategoria);

		   @Query(
		      value = "SELECT id_ar, nome_ar, preco, nome_scat \nFROM artigo \nLEFT JOIN subcategoria  ON asubcategoria_id = id_scat\nJOIN categoria  ON acategoria_id = id_cat\nJOIN cliente  ON cliente_id = id AND id=:id AND id_cat=:categoria AND id_scat=:subcategoria ",
		      nativeQuery = true
		   )
		   List<List> procsc(@Param("id") Long id, @Param("categoria") Long categoria, @Param("subcategoria") Long subcategoria);

		   @Query(
		      value = "SELECT id_ar, nome_ar, preco, nome_scat \nFROM artigo \nLEFT JOIN subcategoria  ON asubcategoria_id = id_scat\nJOIN categoria  ON acategoria_id = id_cat\nJOIN cliente  ON cliente_id = id AND id=:id AND id_cat=:categoria ",
		      nativeQuery = true
		   )
		   List<List> proc(@Param("id") Long id, @Param("categoria") Long categoria);
		}
