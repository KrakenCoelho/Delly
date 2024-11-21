package ao.dely.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ao.dely.model.Admin;
import ao.dely.model.Cliente;
import ao.dely.repository.AdminRepository;
import ao.dely.repository.ClienteRepository;


@Service
public class AdminService {
	
	@Autowired
	public ClienteRepository ap ;

	public  List<List>  getAdmins(Pageable pageable){
		
		 //List<Admin> allAdmin = ap.findAll();
		 
		List<List> allAdmin = ap.vertodos1();
		 
	        // Aplica a paginação aos resultados
	        int pageSize = pageable.getPageSize();
	        int currentPage = pageable.getPageNumber();
	        int startItem = currentPage * pageSize;
	        
	        List<List> pagedAdmin= new ArrayList();

	        if (startItem < ((List) allAdmin).size()) {
	            int endItem = Math.min(startItem + pageSize, ((List) allAdmin).size());
	            pagedAdmin = ((List) allAdmin).   subList(startItem, endItem);
	        }
	        
	        
		return pagedAdmin;
	}
	
	//Metodo Simples de paginação e orientação
	public   List<List> findPaginated(Pageable pageable, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		//Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.ap.cag(pageable,sortField,sortDirection);
	}

}
