package ao.dely.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ao.dely.model.Admin;
import ao.dely.model.Cliente;
import ao.dely.repository.AdminRepository;
import ao.dely.repository.ClienteRepository;





@Controller
public class LoginController {

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	
	@GetMapping({"/","/index",})
	public String inicio(Model model, HttpSession session) {

		return "index";

	}
	


	
	 @RequestMapping("/login") 
	 public String loginUser(@Valid Cliente cliente,@Valid Admin admin, Model model, HttpServletRequest request,RedirectAttributes attributes) {
		  
		  for(Cliente user: clienteRepository.login(cliente.getTelefone(),cliente.getPalavrapasse())) {
		
		  
			  attributes.addFlashAttribute("sucesso", "de"); 
			  
			  return loginCliente(cliente,model, request, attributes);
		  
		  }
	
		  for(Admin ad: adminRepository.login(admin.getTelefone(),admin.getPalavrapasse())) {
				
			  
			  attributes.addFlashAttribute("sucesso", "de"); 
			  
			  return loginAdmin(admin,model, request, attributes);
		  
		  }
		 // model.addAttribute("erro","Email ou senha estão incorretos verifique!");
		  attributes.addFlashAttribute("erro","Email ou senha estão incorretos, verifique por favor!");
		   
		  return "redirect:/index";}

	

	private String loginCliente(@Valid Cliente cliente, Model model, HttpServletRequest request,
			RedirectAttributes attributes) {

		String sessionID;
		boolean control = false;
		for (Cliente user : clienteRepository.verify(cliente.getTelefone(), cliente.getPalavrapasse())) {

			control = true;

			HttpSession session = request.getSession();
			session.setAttribute("telefone", user.getTelefone());
			session.setAttribute("palavrapasse", user.getPalavrapasse());

			sessionID = session.getId();
			// System.out.println(sessionID);

		}

		if (control)
			return "redirect:/cliente/dashboard";

		else {
			attributes.addFlashAttribute("erro", "Email ou senha estão incorretos verifique!");
			attributes.addFlashAttribute("email");
			return "redirect:/index";
		}

	}
	
	
	
	private String loginAdmin(@Valid Admin admin, Model model, HttpServletRequest request,
			RedirectAttributes attributes) {

		String sessionID;
		boolean control = false;
		for (Admin adm : adminRepository.verify(admin.getTelefone(), admin.getPalavrapasse())) {

			control = true;

			HttpSession session = request.getSession();
			session.setAttribute("telefone", admin.getTelefone());
			session.setAttribute("palavrapasse", admin.getPalavrapasse());

			sessionID = session.getId();
			// System.out.println(sessionID);

		}

		if (control)
			return "redirect:/admin/dashboard_admin/"+0;

		else {
			attributes.addFlashAttribute("erro", "Email ou senha estão incorretos verifique!");
			attributes.addFlashAttribute("email");
			return "redirect:/index";
		}

	}
	
	
	

	@RequestMapping("/logout")
	public String Logout(Model model, HttpSession session, @Valid Cliente cliente) {
		// session=null;
		session.invalidate();

		// session.removeAttribute(null);
		session.setMaxInactiveInterval(1 * 1);
		// System.out.print(session);
		return "/index";
	}
}
