package ao.dely.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ao.dely.model.Admin;



public interface AdminRepository extends JpaRepository<Admin, Long> {

	
	@Query(value = "SELECT * FROM admin WHERE telefone =:telefone and palavrapasse =:palavrapasse LIMIT 1", nativeQuery = true)
	Iterable <Admin> login(String telefone,String palavrapasse);
	
	@Query(value = "SELECT * FROM admin WHERE telefone =:telefone and palavrapasse =:palavrapasse", nativeQuery = true)
	Iterable <Admin> verify(Object telefone,Object palavrapasse);
	
	@Query(value = "select * FROM admin ORDER BY admin.id", nativeQuery = true)
	Iterable<Admin> vertodos();
	
	@Query(value = "select * from admin  where id=?", nativeQuery = true)
	Iterable <Admin> fidById(Long id);
}