package ao.dely.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
//import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.type.LocalDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import ao.dely.model.Artigo;
import ao.dely.model.Categoria;
import ao.dely.model.Cliente;
import ao.dely.model.Pagamento;
import ao.dely.model.Reclamacao;
import ao.dely.model.Subcategoria;
import ao.dely.model.Visitas;
import ao.dely.repository.ArtigoRepository;
import ao.dely.repository.CategoriaRepository;
import ao.dely.repository.ClienteRepository;
import ao.dely.repository.PacoteRepository;
import ao.dely.repository.PagamentoRepository;
import ao.dely.repository.ReclamacaoRepository;
import ao.dely.repository.SubcategoriaRepository;
import ao.dely.repository.VisitasRepository;



@Controller
public class ClienteController {
	
	
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	SubcategoriaRepository subcatRepository;
	
	@Autowired
	ArtigoRepository artigoRepository;
	
	
	@Autowired
	ReclamacaoRepository reclamacaoRepository;
	
	@Autowired
	PacoteRepository pacoteRepository;
	
	@Autowired
	PagamentoRepository pagRepository;
	
	 @Autowired
	 VisitasRepository visitasRepository;
	
	ArquivoUploadController anexo = new ArquivoUploadController();
	
	APIRef api = new APIRef();
	
	
//	 ▄████▄   ██▓     ██▓▓█████  ███▄    █ ▄▄▄█████▓▓█████   ██████ 
//	 ▒██▀ ▀█  ▓██▒    ▓██▒▓█   ▀  ██ ▀█   █ ▓  ██▒ ▓▒▓█   ▀ ▒██    ▒ 
//	 ▒▓█    ▄ ▒██░    ▒██▒▒███   ▓██  ▀█ ██▒▒ ▓██░ ▒░▒███   ░ ▓██▄   
//	 ▒▓▓▄ ▄██▒▒██░    ░██░▒▓█  ▄ ▓██▒  ▐▌██▒░ ▓██▓ ░ ▒▓█  ▄   ▒   ██▒
//	 ▒ ▓███▀ ░░██████▒░██░░▒████▒▒██░   ▓██░  ▒██▒ ░ ░▒████▒▒██████▒▒
//	 ░ ░▒ ▒  ░░ ▒░▓  ░░▓  ░░ ▒░ ░░ ▒░   ▒ ▒   ▒ ░░   ░░ ▒░ ░▒ ▒▓▒ ▒ ░
//	   ░  ▒   ░ ░ ▒  ░ ▒ ░ ░ ░  ░░ ░░   ░ ▒░    ░     ░ ░  ░░ ░▒  ░ ░
//	 ░          ░ ░    ▒ ░   ░      ░   ░ ░   ░         ░   ░  ░  ░  
//	 ░ ░          ░  ░ ░     ░  ░         ░             ░  ░      ░  
//	 ░                                                               
	
	@GetMapping(value={"/cliente/autenticacao/esqueci-a-palavra-passe"})
    public String recpass(Model model, HttpServletRequest request) throws ParseException {
        return "/cliente/autenticacao/esqueci-a-palavra-passe";
    }
	
	

	
	
	@GetMapping(value={"/parceirossection"})
    public String Parceirossection1(Model model, HttpServletRequest request) throws ParseException {
		
		
		 model.addAttribute("parceiros",clienteRepository.parceiros());
		
        return "/index3";
    }
	
	
	
	@GetMapping(value={"/parceirossection1"})
    public String Parceirossection(Model model, HttpServletRequest request) throws ParseException {
		
		
		 model.addAttribute("parceiros",clienteRepository.parceiros());
		
        return "/index4";
    }
	
    @PostMapping(value={"/checknumero"})
    @ResponseBody
    public String checknumero(Model model, @Valid Cliente cliente, RedirectAttributes attributes, HttpServletRequest request) throws ParseException, IOException {
        if (this.clienteRepository.VerifyNumero(cliente.getTelefone()) == 1) {
            System.out.println(cliente.getTelefone());
            String codigo = ArquivoUploadController.gerarCodigo();
            System.out.println(codigo);
            this.clienteRepository.coderepo(codigo, cliente.getTelefone());
            String MSG_SMS = "O seu codigo de confirma\u00e7\u00e3o \u00e9: " + codigo;
            try {
                Response r = ClienteController.enviarSms(cliente.getTelefone(), MSG_SMS);
                r.body().close();
            }
            catch (IOException e) {
                System.out.println("ERRO: " + e);
                e.printStackTrace();
            }
            return "EXISTE";
        }
        return "numeronoexiste";
    }

    @GetMapping(value={"/cliente/autenticacao/codigo-de-autenticacao"})
    public String autcode(Model model, HttpServletRequest request) throws ParseException {
        return "/cliente/autenticacao/codigo-de-autenticacao";
    }

    @PostMapping(value={"/checkcode"})
    @ResponseBody
    public String checkcode(Model model, @Valid Cliente cliente, RedirectAttributes attributes, HttpServletRequest request) throws ParseException, IOException {
        System.out.println(cliente.getRep_code());
        if (this.clienteRepository.Verifycode(cliente.getRep_code()) == 1) {
            Long r = Long.parseLong(cliente.getRep_code());
            return ArquivoUploadController.Codifica(r);
        }
        return "numeronoexiste";
    }

    @GetMapping(value={"/cliente/autenticacao/nova-senha/{rep_code}"})
    public String Elshadai(@PathVariable(value="rep_code") String rep_code, Model model, HttpServletRequest request) throws ParseException {
        return "/cliente/autenticacao/nova-senha";
    }

    @PostMapping(value={"/editpass/{rep_code}"})
    @ResponseBody
    public String editpass(@PathVariable(value="rep_code") String rep_code, Model model, @Valid Cliente cliente, RedirectAttributes attributes, HttpServletRequest request) throws ParseException, IOException {
        Long rota = ArquivoUploadController.Decodifica(rep_code);
        String r = String.valueOf(rota);
        if (this.clienteRepository.Verifycode(r) == 1) {
            this.clienteRepository.actualizarsenha(cliente.getPalavrapasse(), r);
          //  this.clienteRepository.limpacode(cliente.getPalavrapasse());
            return "index";
        }
        return "numeronoexiste";
    }

    @GetMapping(value={"/cliente/autenticacao/nova-conta-cliente"})
    public String criarcontacliente(Model model, HttpServletRequest request) throws ParseException {
        return "/cliente/autenticacao/nova-conta-cliente";
    }

    @GetMapping(value={"/cliente/dashboard"})
    public String dashboardPromotor(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        boolean control = false;
        String r = "redirect:/index";
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("reclamacao", this.reclamacaoRepository.avalr(cliente.getId()));
                model.addAttribute("reclama", (Object)this.reclamacaoRepository.countrecla(cliente.getId()));
                model.addAttribute("today", (Object)this.visitasRepository.visitashoje(cliente.getId()));
                model.addAttribute("prim", (Object)cliente.getPrim());
                model.addAttribute("id", (Object)ArquivoUploadController.Codifica(cliente.getId()));
                control = true;
                if (!control) continue;
                r = this.clienteRepository.diasrestantes(cliente.getId()) <= 0 ? "redirect:/cliente/definicoes-gerais/escolher-pacote" : "/cliente/dashboard";
            }
        } else {
            r = "redirect:/index";
        }
        return r;
    }

    @PostMapping(value={"/savecliente"})
    public ResponseEntity<String> savecliente(Model model, @Valid Cliente cliente, RedirectAttributes attributes, HttpServletRequest request) throws ParseException {
        if (this.clienteRepository.VerifyNumero(cliente.getTelefone()) > 0) {
            return ResponseEntity.ok().body("numero_existe");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(5, 2);
        Date date = new Date(calendar.getTime().getTime());
        cliente.setData_sub(date);
        cliente.setEstado("Pago");
        cliente.setPrim(1);
        this.clienteRepository.save(cliente);
        boolean control = false;
        for (Cliente user : this.clienteRepository.verify(cliente.getTelefone(), cliente.getPalavrapasse())) {
            control = true;
            HttpSession session = request.getSession();
            session.setAttribute("telefone", (Object)user.getTelefone());
            session.setAttribute("palavrapasse", (Object)user.getPalavrapasse());
            String string = session.getId();
        }
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping(value={"/prim/{id}"})
    @ResponseBody
    public int prim(Model model, @PathVariable(value="id") String id, RedirectAttributes attributes, HttpServletRequest request) throws ParseException, IOException {
        this.clienteRepository.actprim(ArquivoUploadController.Decodifica(id));
        return 1;
    }
	
//	    █████   ██▀███      ▄████▄   ▒█████  ▓█████▄ ▓█████ 
//	  ▒██▓  ██▒▓██ ▒ ██▒   ▒██▀ ▀█  ▒██▒  ██▒▒██▀ ██▌▓█   ▀ 
//	  ▒██▒  ██░▓██ ░▄█ ▒   ▒▓█    ▄ ▒██░  ██▒░██   █▌▒███   
//	  ░██  █▀ ░▒██▀▀█▄     ▒▓▓▄ ▄██▒▒██   ██░░▓█▄   ▌▒▓█  ▄ 
//	  ░▒███▒█▄ ░██▓ ▒██▒   ▒ ▓███▀ ░░ ████▓▒░░▒████▓ ░▒████▒
//	  ░░ ▒▒░ ▒ ░ ▒▓ ░▒▓░   ░ ░▒ ▒  ░░ ▒░▒░▒░  ▒▒▓  ▒ ░░ ▒░ ░
//	   ░ ▒░  ░   ░▒ ░ ▒░     ░  ▒     ░ ▒ ▒░  ░ ▒  ▒  ░ ░  ░
//	     ░   ░   ░░   ░    ░        ░ ░ ░ ▒   ░ ░  ░    ░   
//	      ░       ░        ░ ░          ░ ░     ░       ░  ░
//	                       ░                  ░             
    
    
    @GetMapping(value={"/cliente/definicoes-gerais/meu-qr-code"})
    public String qrcode(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        boolean control = false;
        String r = "redirect:/index";
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("id", (Object)ArquivoUploadController.Codifica(cliente.getId()));
                control = true;
                if (!control) continue;
                r = this.clienteRepository.diasrestantes(cliente.getId()) <= 0 ? "redirect:/cliente/definicoes-gerais/escolher-pacote" : "/cliente/definicoes-gerais/meu-qr-code";
            }
        } else {
            r = "redirect:/index";
        }
        return r;
    }
	
	
	
//	  ███▄ ▄███▓▓█████  ███▄    █  █    ██ 
//	 ▓██▒▀█▀ ██▒▓█   ▀  ██ ▀█   █  ██  ▓██▒
//	 ▓██    ▓██░▒███   ▓██  ▀█ ██▒▓██  ▒██░
//	 ▒██    ▒██ ▒▓█  ▄ ▓██▒  ▐▌██▒▓▓█  ░██░
//	 ▒██▒   ░██▒░▒████▒▒██░   ▓██░▒▒█████▓ 
//	 ░ ▒░   ░  ░░░ ▒░ ░░ ▒░   ▒ ▒ ░▒▓▒ ▒ ▒ 
//	 ░  ░      ░ ░ ░  ░░ ░░   ░ ▒░░░▒░ ░ ░ 
//	 ░      ░      ░      ░   ░ ░  ░░░ ░ ░ 
//	        ░      ░  ░         ░    ░     
	                                       
	
	
	

    @GetMapping(value={"/cliente/menu/menu/{id}"})
    public String Menu(@PathVariable(value="id") String id_cliente, Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        Long decod_id = ArquivoUploadController.Decodifica(id_cliente);
        model.addAttribute("cliente", this.clienteRepository.info(decod_id));
        model.addAttribute("categorias", this.categoriaRepository.categorianomeId2(decod_id));
        String ip = request.getRemoteAddr();
        this.erta(ip, ArquivoUploadController.Decodifica(id_cliente));
        return "/cliente/menu/menu";
    }

    @GetMapping(value={"/cliente/menu/avaliar/{id}"})
    public String MenuaVal(@PathVariable(value="id") String id_cliente, Model model, HttpServletRequest request) throws ParseException {
        Long decod_id = ArquivoUploadController.Decodifica(id_cliente);
        model.addAttribute("cliente", this.clienteRepository.info(decod_id));
        return "/cliente/menu/avaliar";
    }

    @PostMapping(value={"/savereclamacao"})
    public ResponseEntity<String> savereclamacao(Model model, @Valid Reclamacao reclamacao, HttpServletRequest request) {
        Date dataHoraAtual = new Date();
        dataHoraAtual.setTime(dataHoraAtual.getTime() + 3600000L);
        Timestamp timestamp = new Timestamp(dataHoraAtual.getTime());
        reclamacao.setData(timestamp);
        this.reclamacaoRepository.save(reclamacao);
        return ResponseEntity.ok().body(ArquivoUploadController.Codifica(reclamacao.getRcliente_id()));
    }

    @GetMapping(value={"/cliente/menu/categoria/{id}"})
    public String categoriaentra(@PathVariable(value="id") String id_categoria, Model model, HttpServletRequest request) throws ParseException {
        Long decod_id = ArquivoUploadController.Decodifica(id_categoria);
        System.out.println(decod_id);
        model.addAttribute("categoria", this.categoriaRepository.ccj(decod_id));
        model.addAttribute("subcategoria", this.subcatRepository.subcategorianomeId(decod_id));
        model.addAttribute("artigo", this.artigoRepository.pr(decod_id));
        return "/cliente/menu/bebidas";
    }

    @GetMapping(value={"/cliente/menu/sobre-nos/{id}"})
    public String sobrenos(@PathVariable(value="id") String id_categoria, Model model, HttpServletRequest request) throws ParseException {
        Long decod_id = ArquivoUploadController.Decodifica(id_categoria);
        System.out.println(decod_id);
        model.addAttribute("cliente", this.clienteRepository.info(decod_id));
        return "/cliente/menu/sobre-nos";
    }
	
	
	
	
//	  ▄▄▄       ██▀███  ▄▄▄█████▓ ██▓  ▄████  ▒█████  
//	 ▒████▄    ▓██ ▒ ██▒▓  ██▒ ▓▒▓██▒ ██▒ ▀█▒▒██▒  ██▒
//	 ▒██  ▀█▄  ▓██ ░▄█ ▒▒ ▓██░ ▒░▒██▒▒██░▄▄▄░▒██░  ██▒
//	 ░██▄▄▄▄██ ▒██▀▀█▄  ░ ▓██▓ ░ ░██░░▓█  ██▓▒██   ██░
//	  ▓█   ▓██▒░██▓ ▒██▒  ▒██▒ ░ ░██░░▒▓███▀▒░ ████▓▒░
//	  ▒▒   ▓▒█░░ ▒▓ ░▒▓░  ▒ ░░   ░▓   ░▒   ▒ ░ ▒░▒░▒░ 
//	   ▒   ▒▒ ░  ░▒ ░ ▒░    ░     ▒ ░  ░   ░   ░ ▒ ▒░ 
//	   ░   ▒     ░░   ░   ░       ▒ ░░ ░   ░ ░ ░ ░ ▒  
//	       ░  ░   ░               ░        ░     ░ ░  
	
    @GetMapping(value={"/cliente/artigos/artigos-main"})
    public String artigosMain(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        boolean control = false;
        String r = "redirect:/";
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("artigos", this.artigoRepository.artigonomeId(cliente.getId()));
                model.addAttribute("id", (Object)cliente.getId());
                model.addAttribute("categorias", this.categoriaRepository.categorianomeId2(cliente.getId()));
                model.addAttribute("subcategorias", this.subcatRepository.subes(cliente.getId()));
                control = true;
                if (!control) continue;
                r = this.clienteRepository.diasrestantes(cliente.getId()) <= 0 ? "redirect:/cliente/definicoes-gerais/escolher-pacote" : "/cliente/artigos/artigos-main";
            }
        } else {
            r = "redirect:/index";
        }
        return r;
    }

    @RequestMapping(value={"/artigo/procurar/"}, method={RequestMethod.POST})
    @ResponseBody
    public List<List> ProcurarEvento(Model model, @Valid Long id, @Valid String search, @Valid Long categoria, @Valid Long subcategoria, HttpServletRequest request) {
        if (id != 0L && search != "" && categoria != null && subcategoria != null) {
            return this.artigoRepository.proctodos(id, search, categoria, subcategoria);
        }
        if (id != 0L && search != "" && categoria == null && subcategoria == null) {
            return this.artigoRepository.procnome(id, search);
        }
        if (id != 0L && search != "" && categoria != null && subcategoria == null) {
            return this.artigoRepository.procnc(id, search, categoria);
        }
        if (id != 0L && search != "" && categoria == null && subcategoria != null) {
            return this.artigoRepository.procns(id, search, subcategoria);
        }
        if (id != 0L && search == "" && categoria == null && subcategoria != null) {
            return this.artigoRepository.procs(id, subcategoria);
        }
        if (id != 0L && search == "" && categoria != null && subcategoria != null) {
            return this.artigoRepository.procsc(id, categoria, subcategoria);
        }
        if (id != 0L && search == "" && categoria != null && subcategoria == null) {
            return this.artigoRepository.proc(id, categoria);
        }
        System.out.println(this.artigoRepository.artigonomeId(id));
        return this.artigoRepository.artigonomeId(id);
    }

    @GetMapping(value={"/cliente/artigos/criar-artigo"})
    public String artigocriar(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("categorias", this.categoriaRepository.categorianomeId2(cliente.getId()));
                control = true;
            }
        }
        if (control) {
            return "/cliente/artigos/criar-artigo";
        }
        return "redirect:/index";
    }

    @RequestMapping(value={"/subespec"}, method={RequestMethod.GET})
    @ResponseBody
    public List<List> espec2(@RequestParam(value="categoria_id", required=true) Long categoria_id, HttpSession session) {
        return this.subcatRepository.subcategorianomeId(categoria_id);
    }

    @PostMapping(value={"/saveartigo"})
    public ResponseEntity<String> saveartigo(Model model, @Valid Artigo artigo, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                this.artigoRepository.save(artigo);
            }
        } else {
            return ResponseEntity.ok().body("index");
        }
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping(value={"/cliente/artigos/editar-artigo/{id}"})
    public String editarartigo(@PathVariable(value="id") String artigo_id, Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        Long decod_id = ArquivoUploadController.Decodifica(artigo_id);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("artigo", this.artigoRepository.artfinbId(decod_id));
                model.addAttribute("categorias", this.categoriaRepository.categorianomeId2(cliente.getId()));
                control = true;
            }
        }
        if (control) {
            return "/cliente/artigos/editar-artigo";
        }
        return "redirect:/index";
    }

    @PostMapping(value={"/editartigo/{id}"})
    public ResponseEntity<String> editartigo(@PathVariable(value="id") String artigo_id, Model model, @Valid Artigo artigo, HttpServletRequest request) {
        Long decod_id = ArquivoUploadController.Decodifica(artigo_id);
        HttpSession session = request.getSession(false);
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("artigo", this.artigoRepository.save(artigo));
            }
        } else {
            return ResponseEntity.ok().body("index");
        }
        return ResponseEntity.ok().body(ArquivoUploadController.Codifica(artigo.getAcategoria_id()));
    }

    @RequestMapping(value={"/apagarartigo/{id}"})
    public String Apargarartigo(@PathVariable(value="id") String id, HttpServletRequest request, Model model) {
        long id_decod = ArquivoUploadController.Decodifica(id);
        HttpSession session = request.getSession(false);
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                this.artigoRepository.deleteById(id_decod);
            }
        } else {
            return "redirect:/index";
        }
        return "redirect:/cliente/categorias/categorias-main";
    }

	
	
    @PostMapping(value={"/editarcliente/{id}"})
    public ResponseEntity<String> EditarCliente(@PathVariable(value="id") String id, Model model, @Valid Cliente client, @RequestParam(value="files") MultipartFile files, @RequestParam(value="files2") MultipartFile files2, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id_decod = ArquivoUploadController.Decodifica(id);
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                Iterable<Cliente> ade;
                if (files.isEmpty()) {
                    ade = this.clienteRepository.edit(id_decod);
                    model.addAttribute("Ade", ade);
                    client.setLogomarca(ade.iterator().next().getLogomarca());
                } else {
                    client.setLogomarca(this.anexo.singleFileUpload(request, files, "cliente"));
                }
                if (files2.isEmpty()) {
                    ade = this.clienteRepository.edit(id_decod);
                    model.addAttribute("Ade", ade);
                    client.setFundo(ade.iterator().next().getFundo());
                } else {
                    client.setFundo(this.anexo.singleFileUpload(request, files2, "cliente"));
                }
                client.setPrim(0);
                this.clienteRepository.save(client);
            }
        } else {
            return ResponseEntity.ok().body("index");
        }
        return ResponseEntity.ok().body("ok");
    }

	
	 
//	▄████▄   ▄▄▄     ▄▄▄█████▓▓█████   ▄████  ▒█████   ██▀███   ██▓ ▄▄▄      
//	▒██▀ ▀█  ▒████▄   ▓  ██▒ ▓▒▓█   ▀  ██▒ ▀█▒▒██▒  ██▒▓██ ▒ ██▒▓██▒▒████▄    
//	▒▓█    ▄ ▒██  ▀█▄ ▒ ▓██░ ▒░▒███   ▒██░▄▄▄░▒██░  ██▒▓██ ░▄█ ▒▒██▒▒██  ▀█▄  
//	▒▓▓▄ ▄██▒░██▄▄▄▄██░ ▓██▓ ░ ▒▓█  ▄ ░▓█  ██▓▒██   ██░▒██▀▀█▄  ░██░░██▄▄▄▄██ 
//	▒ ▓███▀ ░ ▓█   ▓██▒ ▒██▒ ░ ░▒████▒░▒▓███▀▒░ ████▓▒░░██▓ ▒██▒░██░ ▓█   ▓██▒
//	░ ░▒ ▒  ░ ▒▒   ▓▒█░ ▒ ░░   ░░ ▒░ ░ ░▒   ▒ ░ ▒░▒░▒░ ░ ▒▓ ░▒▓░░▓   ▒▒   ▓▒█░
//	  ░  ▒     ▒   ▒▒ ░   ░     ░ ░  ░  ░   ░   ░ ▒ ▒░   ░▒ ░ ▒░ ▒ ░  ▒   ▒▒ ░
//	░          ░   ▒    ░         ░   ░ ░   ░ ░ ░ ░ ▒    ░░   ░  ▒ ░  ░   ▒   
//	░ ░            ░  ░           ░  ░      ░     ░ ░     ░      ░        ░  ░
//	░                                                                         
	
	
	
	
    @GetMapping(value={"/cliente/categorias/categorias-main"})
    public String categoriaMain(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        boolean control = false;
        String r = "redirect:/";
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("categorias", this.categoriaRepository.categoriaesp(cliente.getId()));
                control = true;
                if (!control) continue;
                r = this.clienteRepository.diasrestantes(cliente.getId()) <= 0 ? "redirect:/cliente/definicoes-gerais/escolher-pacote" : "/cliente/categorias/categorias-main";
            }
        } else {
            r = "redirect:/index";
        }
        return r;
    }

    @GetMapping(value={"/cliente/categorias/criar-categoria"})
    public String categoriacriar(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                control = true;
            }
        }
        if (control) {
            return "/cliente/categorias/criar-categoria";
        }
        return "redirect:/index";
    }

    @PostMapping(value={"/savecategoria"})
    public ResponseEntity<String> savecategria(Model model, @Valid Categoria categoria, @RequestParam(value="files") MultipartFile files, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                StringBuilder fileNames = new StringBuilder();
                String name_document = this.anexo.singleFileUpload(request, files, "categoria");
                categoria.setImagem_cat(name_document);
                categoria.setCliente_id(cliente);
                this.categoriaRepository.save(categoria);
            }
        } else {
            return ResponseEntity.ok().body("index");
        }
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping(value={"/cliente/categorias/categoria-ind/{id}"})
    public String categoriaind(@PathVariable(value="id") String categoria_id, Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        Long decod_id = ArquivoUploadController.Decodifica(categoria_id);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("categorias", this.categoriaRepository.categorianomeId(decod_id));
                model.addAttribute("subcategorias", this.subcatRepository.subcategorianomeId(decod_id));
                model.addAttribute("artigos", this.artigoRepository.catartigonomeId(decod_id));
                control = true;
            }
        }
        if (control) {
            return "/cliente/categorias/categoria-ind";
        }
        return "redirect:/index";
    }

    @GetMapping(value={"/rs/{id}"})
    public String editarcategoria(@PathVariable(value="id") String categoria_id, Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        Long decod_id = ArquivoUploadController.Decodifica(categoria_id);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("categoria", this.categoriaRepository.findid(decod_id));
                control = true;
            }
        }
        if (control) {
            return "/cliente/categorias/editar-categoria";
        }
        return "redirect:/index";
    }

    @PostMapping(value={"/editarcategoria/{id}"})
    public ResponseEntity<String> Editarcategoria(@PathVariable(value="id") String id, Model model, @Valid Categoria categoria, @RequestParam(value="files") MultipartFile files, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id_decod = ArquivoUploadController.Decodifica(id);
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                if (files.isEmpty()) {
                    Iterable<Categoria> ade = this.categoriaRepository.findid(id_decod);
                    model.addAttribute("Ade", ade);
                    categoria.setImagem_cat(ade.iterator().next().getImagem_cat());
                } else {
                    categoria.setImagem_cat(this.anexo.singleFileUpload(request, files, "categoria"));
                }
                categoria.setCliente_id(cliente);
                this.categoriaRepository.save(categoria);
            }
        } else {
            return ResponseEntity.ok().body("index");
        }
        return ResponseEntity.ok().body(ArquivoUploadController.Codifica(categoria.getId_cat()));
    }

    @RequestMapping(value={"/apagarcategoria/{id}"})
    public String Apargacategoria(@PathVariable(value="id") String id, HttpServletRequest request, Model model) {
        long id_decod = ArquivoUploadController.Decodifica(id);
        HttpSession session = request.getSession(false);
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                String realPathtoUploads = request.getServletContext().getRealPath("upload/promotor/");
                Iterable<Categoria> categoria = this.categoriaRepository.findid(id_decod);
                model.addAttribute("categoria", categoria);
                File f = new File(String.valueOf(realPathtoUploads) + categoria.iterator().next().getImagem_cat());
                f.delete();
                this.categoriaRepository.deleteById(id_decod);
            }
        } else {
            return "redirect:/index";
        }
        return "redirect:/cliente/categorias/categorias-main";
    }

	
	
	
//	
//	    ██████  █    ██  ▄▄▄▄    ▄████▄   ▄▄▄     ▄▄▄█████▓▓█████   ▄████  ▒█████   ██▀███   ██▓ ▄▄▄      
//	  ▒██    ▒  ██  ▓██▒▓█████▄ ▒██▀ ▀█  ▒████▄   ▓  ██▒ ▓▒▓█   ▀  ██▒ ▀█▒▒██▒  ██▒▓██ ▒ ██▒▓██▒▒████▄    
//	  ░ ▓██▄   ▓██  ▒██░▒██▒ ▄██▒▓█    ▄ ▒██  ▀█▄ ▒ ▓██░ ▒░▒███   ▒██░▄▄▄░▒██░  ██▒▓██ ░▄█ ▒▒██▒▒██  ▀█▄  
//	    ▒   ██▒▓▓█  ░██░▒██░█▀  ▒▓▓▄ ▄██▒░██▄▄▄▄██░ ▓██▓ ░ ▒▓█  ▄ ░▓█  ██▓▒██   ██░▒██▀▀█▄  ░██░░██▄▄▄▄██ 
//	  ▒██████▒▒▒▒█████▓ ░▓█  ▀█▓▒ ▓███▀ ░ ▓█   ▓██▒ ▒██▒ ░ ░▒████▒░▒▓███▀▒░ ████▓▒░░██▓ ▒██▒░██░ ▓█   ▓██▒
//	  ▒ ▒▓▒ ▒ ░░▒▓▒ ▒ ▒ ░▒▓███▀▒░ ░▒ ▒  ░ ▒▒   ▓▒█░ ▒ ░░   ░░ ▒░ ░ ░▒   ▒ ░ ▒░▒░▒░ ░ ▒▓ ░▒▓░░▓   ▒▒   ▓▒█░
//	  ░ ░▒  ░ ░░░▒░ ░ ░ ▒░▒   ░   ░  ▒     ▒   ▒▒ ░   ░     ░ ░  ░  ░   ░   ░ ▒ ▒░   ░▒ ░ ▒░ ▒ ░  ▒   ▒▒ ░
//	  ░  ░  ░   ░░░ ░ ░  ░    ░ ░          ░   ▒    ░         ░   ░ ░   ░ ░ ░ ░ ▒    ░░   ░  ▒ ░  ░   ▒   
//	        ░     ░      ░      ░ ░            ░  ░           ░  ░      ░     ░ ░     ░      ░        ░  ░
//	                          ░ ░                                                                         
	
	
    @GetMapping(value={"/cliente/categorias/subcategoria/criar-subcategoria"})
    public String subcategoriacriar(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("categorias", this.categoriaRepository.categorianomeId2(cliente.getId()));
                control = true;
            }
        }
        if (control) {
            return "/cliente/categorias/subcategoria/criar-subcategoria";
        }
        return "redirect:/index";
    }

    @PostMapping(value={"/savesubcategoria"})
    public ResponseEntity<String> savesubcategria(Model model, @Valid Subcategoria subcategoria, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                this.subcatRepository.save(subcategoria);
            }
        } else {
            return ResponseEntity.ok().body("index");
        }
        return ResponseEntity.ok().body(ArquivoUploadController.Codifica(subcategoria.getCategoria_id()));
    }

    @GetMapping(value={"/cliente/categorias/subcategoria/editar-subcategoria/{te}"})
    public String subcategoriaeditar(@PathVariable(value="te") String subcategoria_id, Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        Long decod_id = ArquivoUploadController.Decodifica(subcategoria_id);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("subcategoria", this.subcatRepository.subfindbyid(decod_id));
                model.addAttribute("categorias", this.categoriaRepository.categorianomeId2(cliente.getId()));
                control = true;
            }
        }
        if (control) {
            return "/cliente/categorias/subcategoria/editar-subcategoria";
        }
        return "redirect:/index";
    }

    @PostMapping(value={"/editsubcategoria/{te}"})
    public ResponseEntity<String> editsubcategria(@PathVariable(value="te") String subcategoria_id, Model model, @Valid Subcategoria subcategoria, HttpServletRequest request) {
        Long decod_id = ArquivoUploadController.Decodifica(subcategoria_id);
        HttpSession session = request.getSession(false);
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("subcategoria", this.subcatRepository.save(subcategoria));
            }
        } else {
            return ResponseEntity.ok().body("index");
        }
        return ResponseEntity.ok().body(ArquivoUploadController.Codifica(subcategoria.getCategoria_id()));
    }

    @RequestMapping(value={"/apagarsubcategoria/{id}"})
    public String Apargarsubcategoria(@PathVariable(value="id") String id, HttpServletRequest request, Model model) {
        long id_decod = ArquivoUploadController.Decodifica(id);
        HttpSession session = request.getSession(false);
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                this.subcatRepository.deleteById(id_decod);
            }
        } else {
            return "redirect:/index";
        }
        return "redirect:/cliente/categorias/categorias-main";}
	
	
	
//	  ▄▄▄    ██▒   █▓ ▄▄▄       ██▓     ██▓ ▄▄▄       ▄████▄   ▄▄▄       ▒█████  
//	 ▒████▄ ▓██░   █▒▒████▄    ▓██▒    ▓██▒▒████▄    ▒██▀ ▀█  ▒████▄    ▒██▒  ██▒
//	 ▒██  ▀█▄▓██  █▒░▒██  ▀█▄  ▒██░    ▒██▒▒██  ▀█▄  ▒▓█    ▄ ▒██  ▀█▄  ▒██░  ██▒
//	 ░██▄▄▄▄██▒██ █░░░██▄▄▄▄██ ▒██░    ░██░░██▄▄▄▄██ ▒▓▓▄ ▄██▒░██▄▄▄▄██ ▒██   ██░
//	  ▓█   ▓██▒▒▀█░   ▓█   ▓██▒░██████▒░██░ ▓█   ▓██▒▒ ▓███▀ ░ ▓█   ▓██▒░ ████▓▒░
//	  ▒▒   ▓▒█░░ ▐░   ▒▒   ▓▒█░░ ▒░▓  ░░▓   ▒▒   ▓▒█░░ ░▒ ▒  ░ ▒▒   ▓▒█░░ ▒░▒░▒░ 
//	   ▒   ▒▒ ░░ ░░    ▒   ▒▒ ░░ ░ ▒  ░ ▒ ░  ▒   ▒▒ ░  ░  ▒     ▒   ▒▒ ░  ░ ▒ ▒░ 
//	   ░   ▒     ░░    ░   ▒     ░ ░    ▒ ░  ░   ▒   ░          ░   ▒   ░ ░ ░ ▒  
//	       ░  ░   ░        ░  ░    ░  ░ ░        ░  ░░ ░            ░  ░    ░ ░  
//	             ░                                   ░                           
	
	
	
	
    @GetMapping(value={"/cliente/avaliacoes/avaliacoes-main"})
    public String Avalia\u00e7\u00f5es(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        String r = "redirect:/index";
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("reclamacao", this.reclamacaoRepository.aval(cliente.getId()));
                model.addAttribute("id",cliente.getId());
                control = true;
                if (!control) continue;
                r = this.clienteRepository.diasrestantes(cliente.getId()) <= 0 ? "redirect:/cliente/definicoes-gerais/escolher-pacote" : "/cliente/avaliacoes/avaliacoes-main";
            }
        } else {
            r = "redirect:/index";
        }
        return r;
    }

    @GetMapping(value={"/cliente/avaliacoes/avaliacao-ind/{id}"})
    public String Avalia\u00e7\u00f5esInd(@PathVariable(value="id") String avl_id, Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        Long decod_id = ArquivoUploadController.Decodifica(avl_id);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("aval", this.reclamacaoRepository.ind(decod_id));
                control = true;
            }
        }
        if (control) {
            return "/cliente/avaliacoes/avaliacao-ind";
        }
        return "redirect:/index";
    }

    @RequestMapping(value={"/avaliacao/procurar/"}, method={RequestMethod.POST})
    @ResponseBody
    public List<List> Procuraravaliacao(Model model, @Valid Long id, @Valid String search, @Valid String inicio, @Valid String fim, HttpServletRequest request) {
        System.out.println("Id: " + id + "\nSearch: " + search + "\nCategoria: " + inicio + "\nSubcategoria: " + fim);
        if (id != 0L && search != "" && inicio == "" && fim == "") {
            return this.reclamacaoRepository.procn(id, search);
        }
        if (id != 0L && search == "" && inicio != "" && fim != "") {
            return this.reclamacaoRepository.procif(id, inicio, fim);
        }
        if (id != 0L && search != "" && inicio != "" && fim != "") {
            return this.reclamacaoRepository.pr(id, search, inicio, fim);
        }
        return this.reclamacaoRepository.aval(id);
    }
	
//	 ██▓███   ▄▄▄        ▄████  ▄▄▄       ███▄ ▄███▓▓█████  ███▄    █ ▄▄▄█████▓ ▒█████  
//	 ▓██░  ██▒▒████▄     ██▒ ▀█▒▒████▄    ▓██▒▀█▀ ██▒▓█   ▀  ██ ▀█   █ ▓  ██▒ ▓▒▒██▒  ██▒
//	 ▓██░ ██▓▒▒██  ▀█▄  ▒██░▄▄▄░▒██  ▀█▄  ▓██    ▓██░▒███   ▓██  ▀█ ██▒▒ ▓██░ ▒░▒██░  ██▒
//	 ▒██▄█▓▒ ▒░██▄▄▄▄██ ░▓█  ██▓░██▄▄▄▄██ ▒██    ▒██ ▒▓█  ▄ ▓██▒  ▐▌██▒░ ▓██▓ ░ ▒██   ██░
//	 ▒██▒ ░  ░ ▓█   ▓██▒░▒▓███▀▒ ▓█   ▓██▒▒██▒   ░██▒░▒████▒▒██░   ▓██░  ▒██▒ ░ ░ ████▓▒░
//	 ▒▓▒░ ░  ░ ▒▒   ▓▒█░ ░▒   ▒  ▒▒   ▓▒█░░ ▒░   ░  ░░░ ▒░ ░░ ▒░   ▒ ▒   ▒ ░░   ░ ▒░▒░▒░ 
//	 ░▒ ░       ▒   ▒▒ ░  ░   ░   ▒   ▒▒ ░░  ░      ░ ░ ░  ░░ ░░   ░ ▒░    ░      ░ ▒ ▒░ 
//	 ░░         ░   ▒   ░ ░   ░   ░   ▒   ░      ░      ░      ░   ░ ░   ░      ░ ░ ░ ▒  
//	                ░  ░      ░       ░  ░       ░      ░  ░         ░              ░ ░  
//	                                                                                     
	
	
	
	
//	▓█████▄ ▓█████   █████▒██▓ ███▄    █  ██▓ ▄████▄   ▒█████  ▓█████   ██████ 
//	▒██▀ ██▌▓█   ▀ ▓██   ▒▓██▒ ██ ▀█   █ ▓██▒▒██▀ ▀█  ▒██▒  ██▒▓█   ▀ ▒██    ▒ 
//	░██   █▌▒███   ▒████ ░▒██▒▓██  ▀█ ██▒▒██▒▒▓█    ▄ ▒██░  ██▒▒███   ░ ▓██▄   
//	░▓█▄   ▌▒▓█  ▄ ░▓█▒  ░░██░▓██▒  ▐▌██▒░██░▒▓▓▄ ▄██▒▒██   ██░▒▓█  ▄   ▒   ██▒
//	░▒████▓ ░▒████▒░▒█░   ░██░▒██░   ▓██░░██░▒ ▓███▀ ░░ ████▓▒░░▒████▒▒██████▒▒
//	 ▒▒▓  ▒ ░░ ▒░ ░ ▒ ░   ░▓  ░ ▒░   ▒ ▒ ░▓  ░ ░▒ ▒  ░░ ▒░▒░▒░ ░░ ▒░ ░▒ ▒▓▒ ▒ ░
//	 ░ ▒  ▒  ░ ░  ░ ░      ▒ ░░ ░░   ░ ▒░ ▒ ░  ░  ▒     ░ ▒ ▒░  ░ ░  ░░ ░▒  ░ ░
//	 ░ ░  ░    ░    ░ ░    ▒ ░   ░   ░ ░  ▒ ░░        ░ ░ ░ ▒     ░   ░  ░  ░  
//	   ░       ░  ░        ░           ░  ░  ░ ░          ░ ░     ░  ░      ░  
//	 ░                                       ░                                 
	
	
	
	
	
    @GetMapping(value={"/cliente/definicoes-gerais/definicoes"})
    public String defini\u00e7\u00f5es(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        String r = "redirect:/";
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("categorias", this.categoriaRepository.categorianomeId2(cliente.getId()));
                model.addAttribute("dias", (Object)this.clienteRepository.diasrestantes(cliente.getId()));
                control = true;
                if (!control) continue;
                r = this.clienteRepository.diasrestantes(cliente.getId()) <= 0 ? "redirect:/cliente/definicoes-gerais/escolher-pacote" : "/cliente/definicoes-gerais/definicoes";
            }
        } else {
            r = "redirect:/index";
        }
        return r;
    }

    @GetMapping(value={"/cliente/definicoes-gerais/definicoes-gerais-do-estabelecimento"})
    public String defini\u00e7\u00f5esgerais(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("cliente", this.clienteRepository.edit(cliente.getId()));
                control = true;
            }
        }
        if (control) {
            return "/cliente/definicoes-gerais/definicoes-gerais-do-estabelecimento";
        }
        return "redirect:/index";
    }

    @GetMapping(value={"/cliente/definicoes-gerais/escolher-pacote"})
    public String escolherPacote(Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("pacotes", this.pacoteRepository.pacotes());
                control = true;
            }
        }
        if (control) {
            return "/cliente/definicoes-gerais/escolher-pacote";
        }
        return "redirect:/index";
    }

    @RequestMapping(value={"/pagar/{id}"})
    public ResponseEntity<String> Pagar(@PathVariable(value="id") String id, HttpServletRequest request, Model model) {
        long id_decod = ArquivoUploadController.Decodifica(id);
        HttpSession session = request.getSession(false);
        if (session != null) {
            String palavrapasse;
            String telefone = (String)session.getAttribute("telefone");
            Iterator<Cliente> iterator = this.clienteRepository.verify(telefone, palavrapasse = (String)session.getAttribute("palavrapasse")).iterator();
            if (iterator.hasNext()) {
                Cliente cliente = iterator.next();
                System.out.println(String.valueOf(cliente.getNome()) + " - " + id);
                Calendar calendar = Calendar.getInstance();
                calendar.add(5, 1);
                Date novaData = calendar.getTime();
                Pagamento pag = new Pagamento();
                pag.setCliente_id(cliente.getId());
                pag.setCliente_numero(cliente.getTelefone());
                pag.setEntidade("01068");
                pag.setReferencia(this.api.gerarReferencia(cliente, this.pacoteRepository.valor(id_decod), "Subscription", request));
                pag.setTipo("Subscri\u00e7\u00e3o");
                pag.setEstatuto("Pendente");
                pag.setData_criacaoo(new Date());
                pag.setData_exp(novaData);
                pag.setPacote_id(id_decod);
                this.pagRepository.save(pag);
                return ResponseEntity.ok().body(ArquivoUploadController.Codifica(cliente.getId()));
            }
        } else {
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.ok().body("ok");
    }

    @GetMapping(value={"/cliente/definicoes-gerais/pagar/{id}"})
    public String pagar(@PathVariable(value="id") String id, Model model, HttpServletRequest request) throws ParseException {
        HttpSession session = request.getSession(false);
        Long decod = ArquivoUploadController.Decodifica(id);
        boolean control = false;
        if (session != null) {
            String telefone = (String)session.getAttribute("telefone");
            String palavrapasse = (String)session.getAttribute("palavrapasse");
            for (Cliente cliente : this.clienteRepository.verify(telefone, palavrapasse)) {
                model.addAttribute("pag", this.clienteRepository.PG(decod));
                control = true;
            }
        }
        if (control) {
            return "/cliente/definicoes-gerais/pagar";
        }
        return "redirect:/index";
    }

    @GetMapping(value={"/olhapag/{id}/{ref}"})
    public ResponseEntity<String> verifref(@PathVariable(value="id") String id, @PathVariable(value="ref") String ref, Model model, RedirectAttributes attributes, HttpServletRequest request, HttpServletResponse response) throws ParseException {
        long id_decod = ArquivoUploadController.Decodifica(id);
        if (this.pagRepository.re(id_decod, ref).equals("Pendente")) {
            return ResponseEntity.ok().body("Reservado");
        }
        return ResponseEntity.ok().body("pago");
    }

    @PostMapping(value={"/confirmapagamento"})
    @ResponseBody
    public String confirmapagamento(@RequestParam(value="referencia", required=true) String reference) {
        try {
            
            Long id = this.pagRepository.idcliente(reference);
           // System.out.println(clienteRepository.estado(id));
            if(clienteRepository.estado(id).equals("Não pago")) {
            this.pagRepository.actuapag("Pago", reference);
          
            //System.out.println("Entrou aqui1");
            int duracao = this.clienteRepository.duracao(id, reference);
            Calendar calendar = Calendar.getInstance();
            calendar.add(2, duracao);
            Date date = new Date(calendar.getTime().getTime());
            this.clienteRepository.pagamentocliente("Pago", date, id);
            
            String MSG_SMS = "Pagamento efectuado com sucesso\nObrigado. ";
            Response r = APIRef.enviarSms(this.clienteRepository.Numero(id), MSG_SMS);
            r.body().close();
            
            }else if(clienteRepository.estado(id).equals("Pago")) {
            	//System.out.println("Entrou aqui2");
            	 if(pagRepository.re(id, reference).equals("Pendente")) {
            	  this.pagRepository.actuapag("Pago", reference);
            	  //System.out.println("Entrou aqui3");
                  int duracao = this.clienteRepository.duracao(id, reference);
                  LocalDate diasr = clienteRepository.data_sub(id);
                  System.out.println(duracao);
                  LocalDate newDate = diasr.plusMonths(duracao);
                  Date date = Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                  this.clienteRepository.pagamentocliente("Pago", date, id);
                  
                  String MSG_SMS = "Pagamento efectuado com sucesso\nObrigado. ";
                  Response r = APIRef.enviarSms(this.clienteRepository.Numero(id), MSG_SMS);
                  r.body().close();}
            	 else {reference="Ja foi pago";}
            }
           
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR: " + e);
        }
        return reference;
    }

    @Scheduled(cron="0 0 0 * * *")
    //@GetMapping("/act")
    public void salvarestado() throws ParseException {
        for (Cliente cliente : this.clienteRepository.vertodos()) {
            if (this.clienteRepository.diasrestantes(cliente.getId()) > 0) {
                cliente.setEstado("Pago");
            } else if (this.clienteRepository.diasrestantes(cliente.getId()) <= 0) {
                cliente.setEstado("N\u00e3o pago");
            }
            this.clienteRepository.save(cliente);
        }
    }

    @PostMapping(value={"/visita"})
    @ResponseBody
    public String visitas(Model model, @Valid String id, @Valid String ip) {
        if (this.visitasRepository.seexistehoje(ip, ArquivoUploadController.Decodifica(id)) <= 0) {
            Visitas vis = new Visitas();
            vis.setData_v(new Date());
            vis.setVcli_id(ArquivoUploadController.Decodifica(id));
            vis.setIp(ip);
            this.visitasRepository.save(vis);
        }
        return "Marco";
    }

    public void erta(String ip, Long id) {
        if (this.visitasRepository.seexistehoje(ip, id) <= 0) {
            Visitas vis = new Visitas(); 
            vis.setData_v(new Date());
            vis.setVcli_id(id);
            vis.setIp(ip);
            this.visitasRepository.save(vis);
        }
    }


    @GetMapping(value={"/fragmentos/bar"})
    public String fragtbutton(Model model, HttpSession session) {
        return "/fragmentos/bar";
    }


	
	
//	    ██████  ███▄ ▄███▓  ██████ 
//	  ▒██    ▒ ▓██▒▀█▀ ██▒▒██    ▒ 
//	  ░ ▓██▄   ▓██    ▓██░░ ▓██▄   
//	    ▒   ██▒▒██    ▒██   ▒   ██▒
//	  ▒██████▒▒▒██▒   ░██▒▒██████▒▒
//	  ▒ ▒▓▒ ▒ ░░ ▒░   ░  ░▒ ▒▓▒ ▒ ░
//	  ░ ░▒  ░ ░░  ░      ░░ ░▒  ░ ░
//	  ░  ░  ░  ░      ░   ░  ░  ░  
//	        ░         ░         ░  
	                               
	
    public static Response enviarSms(String telefone, String msg) throws IOException {
        String xmlString = "<SMS><authentification> <username>sservices</username> <password>smsillico</password> </authentification><message> <sender>Delly</sender> <text>" + msg + "</text> </message> <recipients> " + "<gsm>" + telefone + "</gsm>" + "</recipients></SMS>";
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse((String)"application/xml");
        RequestBody body = RequestBody.create((MediaType)mediaType, (String)xmlString);
        Request request = new Request.Builder().url("https://api.smsillico-ao.com/sendsms/xml").post(body).addHeader("Accept", "application/json").addHeader("Content-Type", "application/json").build();
        return client.newCall(request).execute();
    }
	
}
