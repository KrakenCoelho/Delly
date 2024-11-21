package ao.dely.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ao.dely.model.Cliente;




@Repository
public interface ClienteRepository  extends JpaRepository<Cliente,Long>  {
	
	@Query(
		      value = "SELECT * FROM cliente WHERE telefone =:telefone and palavrapasse =:palavrapasse LIMIT 1",
		      nativeQuery = true
		   )
		   Iterable<Cliente> login(String telefone, String palavrapasse);

	@Query(value = "SELECT COUNT(*) FROM cliente ", nativeQuery = true)
	int countt();
	
	@Query(value = "SELECT data_sub  FROM cliente WHERE id=? ", nativeQuery = true)
	LocalDate data_sub(Long id);
	
	@Query(value = "SELECT id,nome,telefone,estado,data_sub FROM cliente ORDER BY nome ASC ", nativeQuery = true)
	List<List> cag(Pageable pageable,String sortField, String sortDirection);
	
	
	@Query(value = "SELECT  CASE MONTH(pagamento.data_criacaoo)\n"
			+ "        WHEN 1 THEN 'Janeiro'\n"
			+ "        WHEN 2 THEN 'Fevereiro'\n"
			+ "        WHEN 3 THEN 'Março'\n"
			+ "        WHEN 4 THEN 'Abril'\n"
			+ "        WHEN 5 THEN 'Maio'\n"
			+ "        WHEN 6 THEN 'Junho'\n"
			+ "        WHEN 7 THEN 'Julho'\n"
			+ "        WHEN 8 THEN 'Agosto'\n"
			+ "        WHEN 9 THEN 'Setembro'\n"
			+ "        WHEN 10 THEN 'Outubro'\n"
			+ "        WHEN 11 THEN 'Novembro'\n"
			+ "        WHEN 12 THEN 'Dezembro'\n"
			+ "    END AS mes,COALESCE(SUM(pacotes.preco_pac), 0) FROM cliente,pagamento,pacotes WHERE id=pagamento.cliente_id AND pacotes.id_pac=pagamento.pacote_id AND pagamento.estatuto='Pago' AND YEAR(pagamento.data_criacaoo)=YEAR(CURDATE()) GROUP BY MONTH(pagamento.data_criacaoo) ", nativeQuery = true)
	List<List> relatorioMensal();
	
	@Query(value = "SELECT YEAR(pagamento.data_criacaoo),COALESCE(SUM(pacotes.preco_pac), 0) FROM cliente,pagamento,pacotes WHERE id=pagamento.cliente_id AND pacotes.id_pac=pagamento.pacote_id AND pagamento.estatuto='Pago' GROUP BY YEAR(pagamento.data_criacaoo) ", nativeQuery = true)
	List<List> relatorioAnual();
	
	 @Query(
		      value = "SELECT id,nome,telefone,estado,data_sub  FROM cliente ORDER BY nome ASC ",
		      nativeQuery = true
		   )
	 List<List> vertodos1();
	
	 @Query(
		      value = "SELECT nome,telefone,email,estado, (SELECT COUNT(pagamento.id_pag) FROM pagamento WHERE id=cliente_id AND pagamento.estatuto='Pago' ) FROM cliente LEFT JOIN pagamento ON id=pagamento.cliente_id  WHERE  (estado=:estado OR :estado IS NULL ) GROUP BY id ORDER BY `cliente`.`estado` DESC",
		      nativeQuery = true
		   )
	 List<List> Foi(@Param("estado") String estado );
	 
	 
		   @Query(
		      value = "SELECT * FROM cliente WHERE telefone =:telefone and palavrapasse = :palavrapasse",
		      nativeQuery = true
		   )
		   Iterable<Cliente> verify(@Param("telefone") String telefone, @Param("palavrapasse") String palavrapasse);

		   @Query(
		      value = "SELECT id,telefone,palavrapasse FROM cliente WHERE telefone =:telefone and palavrapasse = :palavrapasse",
		      nativeQuery = true
		   )
		   List<List> verify1(@Param("telefone") String telefone, @Param("palavrapasse") String palavrapasse);

		   @Query(
		      value = "SELECT * FROM cliente ORDER BY nome ASC LIMIT 25",
		      nativeQuery = true
		   )
		   Iterable<Cliente> vertodos();
		   
		   @Query(
				      value = "SELECT * FROM cliente WHERE nome LIKE %:search%  OR telefone LIKE %:search% LIMIT 15 ",
				      nativeQuery = true
				   )
				   Iterable<Cliente> proc(@Param("search") String search);
		   
		   
		   @Query(
				      value = "SELECT * FROM cliente WHERE estado=:estado AND (nome LIKE %:search%  OR telefone LIKE %:search%) LIMIT 15",
				      nativeQuery = true
				   )
				   Iterable<Cliente> proce(@Param("search") String search,@Param("estado") String estado);
		   
		   @Query(
				      value = "SELECT * FROM cliente WHERE estado=:estado ",
				      nativeQuery = true
				   )
				   Iterable<Cliente> proe(@Param("estado") String estado);
		   
		   
		   
		   @Query(value = "SELECT COUNT(*) FROM cliente",nativeQuery = true)
	       int contagem();

		   @Query(value = "SELECT COUNT(*) FROM cliente WHERE estado='Pago'",nativeQuery = true)
	       int contagempagos();
		   
		   @Query(value = "SELECT COALESCE(SUM(pacotes.preco_pac), 0) FROM cliente,pagamento,pacotes  WHERE id=pagamento.cliente_id AND pacotes.id_pac=pagamento.pacote_id AND pagamento.estatuto='Pago' AND MONTH(pagamento.data_criacaoo) = MONTH(CURDATE()) AND YEAR(pagamento.data_criacaoo) = YEAR(CURDATE())",nativeQuery = true)
	       int contagempagospormes();
		   
		   @Query(value = "SELECT COALESCE(SUM(pacotes.preco_pac), 0) FROM cliente,pagamento,pacotes  WHERE id=pagamento.cliente_id AND pacotes.id_pac=pagamento.pacote_id AND pagamento.estatuto='Pago'",nativeQuery = true)
	       int contagempagostotal();
		   
		   @Query(
		      value = "SELECT * FROM cliente WHERE id=? LIMIT 1",
		      nativeQuery = true
		   )
		   Iterable<Cliente> edit(Long id);

		   @Query(
		      value = "SELECT count(telefone) FROM cliente WHERE telefone=?",
		      nativeQuery = true
		   )
		   int VerifyNumero(String telefone);

		   @Query(
		      value = "SELECT count(rep_code) FROM cliente WHERE rep_code=?",
		      nativeQuery = true
		   )
		   int Verifycode(String rep_code);

		   @Query(
		      value = "SELECT DATEDIFF(data_sub,CURRENT_DATE) as dias FROM cliente WHERE id=?",
		      nativeQuery = true
		   )
		   int diasrestantes(Long id);

		   @Transactional
		   @Modifying
		   @Query(
		      value = "UPDATE cliente SET rep_code=?  WHERE telefone=?",
		      nativeQuery = true
		   )
		   int coderepo(String rep_code, String telefone);

		   @Transactional
		   @Modifying
		   @Query(
		      value = "UPDATE cliente SET palavrapasse=?, rep_code = NULL  WHERE rep_code=?",
		      nativeQuery = true
		   )
		   int actualizarsenha(String palavrapasse, String rep_code);

		   @Transactional
		   @Modifying
		   @Query(
		      value = "UPDATE cliente SET rep_code=''  WHERE palavrapasse=?",
		      nativeQuery = true
		   )
		   int limpacode(String palavrapasse);

		   @Query(
		      value = "SELECT mensagem,logomarca,fundo,nome,descricao,estado FROM cliente WHERE  id=?",
		      nativeQuery = true
		   )
		   List<List> info(Long id);

		   @Query(
		      value = "SELECT entidade,referencia,preco_pac,id_pag FROM cliente,pacotes,pagamento WHERE id_pac=pacote_id AND id=cliente_id AND id=? ORDER BY id_pag DESC LIMIT 1",
		      nativeQuery = true
		   )
		   List<List> PG(Long id);
		   
		   
		   
		   @Query(value = "SELECT id,fundo,logomarca,nome,`data_sub` FROM `cliente` WHERE estado='Pago' ORDER BY `cliente`.`data_sub` DESC LIMIT 28 ", nativeQuery = true )
		   List<List> parceiros();
		   

		   @Query(
		      value = "SELECT duracao FROM cliente,pacotes,pagamento WHERE id_pac=pacote_id AND id=cliente_id AND id=? AND referencia=?",
		      nativeQuery = true
		   )
		   int duracao(Long id, String referencia);

		   @Transactional
		   @Modifying
		   @Query(
		      value = "UPDATE cliente SET estado=?, data_sub=? WHERE id =?",
		      nativeQuery = true
		   )
		   int pagamentocliente(String estatuto, Date data, Long id_c);

		   @Query(
		      value = "SELECT telefone FROM cliente WHERE id=?",
		      nativeQuery = true
		   )
		   String Numero(Long id);
		   
		   @Query(
				      value = "SELECT estado FROM cliente WHERE id=?",
				      nativeQuery = true
				   )
				   String estado(Long id);
		   
		   @Transactional
		   @Modifying
		   @Query(
		      value = "UPDATE cliente SET prim=0 WHERE id=?",
		      nativeQuery = true
		   )
		   int actprim(Long id);
		   
		   @Transactional
		   @Modifying
		   @Query(
		      value = "UPDATE cliente SET estado='Pago',data_sub=? WHERE id=?",
		      nativeQuery = true
		   )
		   int arer(String data,Long id);
		   
		}
