package ao.dely.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.squareup.okhttp.Response;

import ao.dely.model.Admin;
import ao.dely.model.Cliente;
import ao.dely.repository.AdminRepository;
import ao.dely.repository.ArtigoRepository;
import ao.dely.repository.CategoriaRepository;
import ao.dely.repository.ClienteRepository;
import ao.dely.repository.PacoteRepository;
import ao.dely.repository.PagamentoRepository;
import ao.dely.repository.ReclamacaoRepository;
import ao.dely.repository.SubcategoriaRepository;
import ao.dely.repository.VisitasRepository;
import ao.dely.service.AdminService;


@Controller
public class AdminController {

	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	SubcategoriaRepository subcatRepository;
	
	@Autowired
	ArtigoRepository artigoRepository;
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	ReclamacaoRepository reclamacaoRepository;
	
	@Autowired
	PacoteRepository pacoteRepository;
	
	@Autowired
	PagamentoRepository pagRepository;
	
	 @Autowired
	 VisitasRepository visitasRepository;
	 
	 @Autowired
		AdminService as;
	
	ArquivoUploadController anexo = new ArquivoUploadController();
	
	APIRef api = new APIRef();
	
	
	
//	@GetMapping({"/admin/dashboard_admin"})
//	   public String dashboardAdmin(Model model, HttpServletRequest request) throws ParseException {
//	      HttpSession session = request.getSession(false);
//	      boolean control = false;
//	      if (session != null) {
//	         for(Iterator var6 = this.adminRepository.verify(session.getAttribute("telefone"), session.getAttribute("palavrapasse")).iterator(); var6.hasNext(); control = true) {
//	            Admin adm = (Admin)var6.next();
//	            
//            model.addAttribute("clientes", clienteRepository.vertodos());
//            model.addAttribute("count", clienteRepository.contagem());
//            model.addAttribute("countpago", clienteRepository.contagempagos());
//}   
//	      }
//
//	      return control ? "/admin/dashboard_admin" : "redirect:/index";
//	   }
	
	
	@GetMapping({"/admin/dashboard_admin/{i2}"})
	   public String dashboardAdmin(@PathVariable(value="i2")Integer pageNo,
			   @RequestParam(name="sortField",defaultValue="nome") String sortField,
				 @RequestParam(name="sortDir",defaultValue="DESC") String sortDir,Model model, HttpServletRequest request) throws ParseException {
	      HttpSession session = request.getSession(false);
	      boolean control = false;
	      if (session != null) {
	         for(Iterator var6 = this.adminRepository.verify(session.getAttribute("telefone"), session.getAttribute("palavrapasse")).iterator(); var6.hasNext(); control = true) {
	            Admin adm = (Admin)var6.next();
	            int pageSize = 25;
	            
	            Pageable pageable = PageRequest.of(pageNo, pageSize);
	            List<List> adpag = as.ap.cag(pageable, sortField, sortDir);
	     model.addAttribute("currentPage", pageNo);
		 model.addAttribute("totalPages", (clienteRepository.countt() / pageSize));
		 model.addAttribute("totalItems", clienteRepository.countt());
         model.addAttribute("clientes", adpag);
         model.addAttribute("count", clienteRepository.contagem());
         model.addAttribute("countpago", clienteRepository.contagempagos());
         model.addAttribute("estemes", clienteRepository.contagempagospormes());
         model.addAttribute("total", clienteRepository.contagempagostotal());
}   
	      }

	      return control ? "/admin/dashboard_admin" : "redirect:/index";
	   }
	
	@RequestMapping(value={"/clientesprocurar/"}, method={RequestMethod.POST})
    @ResponseBody
    public Iterable<Cliente> cliente(Model model,  @Valid String search,@Valid String estado, HttpServletRequest request) {
        if ( search != "" & estado=="" ) {
            return clienteRepository.proc( search);
        } if ( search != "" & estado!="" ) {
            return clienteRepository.proce( search,estado);
        } if ( search == "" & estado!="" ) {
            return clienteRepository.proe( estado);
        }
        
        return clienteRepository.vertodos();
    }
	
	
	
	
	 @PostMapping(value={"/pay/{ref}/{param}"})
	    @ResponseBody
	    public String confirmapagamento(@PathVariable("ref") String reference,@PathVariable("param") String param) {
	        
		 Long id = anexo.Decodifica(param);
		 
		 try {
			// System.out.println(reference);
	           // Long id = this.pagRepository.idcliente(reference);
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
	            	
	            	 if(pagRepository.re(id, reference).equals("Pendente")) {
	            	  this.pagRepository.actuapag("Pago", reference);
	            	  //System.out.println("Entrou aqui2");
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
	
	 @PostMapping(value={"/val/{id}/{dt}"})
	   public ResponseEntity<String> saveDate(@PathVariable("id")String id,
			   @PathVariable("dt")String data,Model model, 
			   HttpServletRequest request) throws ParseException {
	      HttpSession session = request.getSession(false);
	      boolean control = false;
	      Long iddecod=anexo.Decodifica(id);
	      if (session != null) {
	         for(Iterator var6 = this.adminRepository.verify(session.getAttribute("telefone"), session.getAttribute("palavrapasse")).iterator(); var6.hasNext(); control = true) {
	            Admin adm = (Admin)var6.next();
	            
         System.out.println(data);
         clienteRepository.arer(data, iddecod);
     }   
	      }

	      return control ? ResponseEntity.ok().body("ok") : ResponseEntity.ok().body("index");
	   }
	
	
//   ,ggggggg,                                              
// ,dP""""""Y8b                                ,dPYb, ,dPYb,
// d8'    a  Y8                                IP'`Yb IP'`Yb
// 88     "Y8P'                                I8  8I I8  8I
// `8baaaa                                     I8  8' I8  8'
//,d8P""""        ,gg,   ,gg  ,gggg,   ,ggg,   I8 dP  I8 dP 
//d8"            d8""8b,dP"  dP"  "Yb i8" "8i  I8dP   I8dP  
//Y8,           dP   ,88"   i8'       I8, ,8I  I8P    I8P   
//`Yba,,_____,,dP  ,dP"Y8, ,d8,_    _ `YbadP' ,d8b,_ ,d8b,_ 
//  `"Y88888888"  dP"   "Y8P""Y8888PP888P"Y8888P'"Y888P'"Y88
//                                                          
                                                          
                                                          
                                                          
                                                          
                                                         
 
 
 
 @GetMapping("/export")
 public void exportToExcelAno( HttpServletResponse response) throws IOException {
     
	 
	   // Obtenha os dados da tabela (lista de objetos)
     List<List> tabelaDados = getDadosTabelaano();

     // Crie um novo livro Excel
     Workbook workbook = new XSSFWorkbook();

     // Crie uma planilha no livro
     Sheet sheet = workbook.createSheet("Relatorio Anual");

     // Crie um cabeçalho para as colunas
     Row headerRow = sheet.createRow(0);
     headerRow.createCell(0).setCellValue("ANO");
     headerRow.createCell(1).setCellValue("RECEITAS");
   
     //headerRow.createCell(4).setCellValue("PROVINCIA");
     // ... Adicione mais células de cabeçalho, se necessário

     // Preencha os dados da tabela
     int rowNum = 1;
     for (List objeto : tabelaDados) {
         Row row = sheet.createRow(rowNum++);
         row.createCell(0).setCellValue(objeto.get(0).toString());
         row.createCell(1).setCellValue(objeto.get(1).toString());
        // row.createCell(4).setCellValue(objeto.get(4).toString());
        
     
     }

     // Defina o tipo de conteúdo da resposta para o tipo de arquivo Excel
     response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
     // Defina o cabeçalho da resposta para indicar que é um anexo de arquivo
     response.setHeader("Content-Disposition", "attachment; filename=dellyrelatonioanual.xlsx");

     // Escreva o livro Excel em OutputStream da resposta
     workbook.write(response.getOutputStream());
     workbook.close();
 }
 
 private List<List> getDadosTabelaano() {

     return clienteRepository.relatorioAnual();
 }
	 
	 
 @GetMapping("/exportmes")
 public void exportToExcelMes( HttpServletResponse response) throws IOException {
     
	 
	   // Obtenha os dados da tabela (lista de objetos)
     List<List> tabelaDados = getDadosTabelames();

     // Crie um novo livro Excel
     Workbook workbook = new XSSFWorkbook();

     // Crie uma planilha no livro
     Sheet sheet = workbook.createSheet("Relatorio Mensal");

     // Crie um cabeçalho para as colunas
     Row headerRow = sheet.createRow(0);
     headerRow.createCell(0).setCellValue("MÊS");
     headerRow.createCell(1).setCellValue("RECEITAS");
   
     //headerRow.createCell(4).setCellValue("PROVINCIA");
     // ... Adicione mais células de cabeçalho, se necessário

     // Preencha os dados da tabela
     int rowNum = 1;
     for (List objeto : tabelaDados) {
         Row row = sheet.createRow(rowNum++);
         row.createCell(0).setCellValue(objeto.get(0).toString());
         row.createCell(1).setCellValue(objeto.get(1).toString());
        // row.createCell(4).setCellValue(objeto.get(4).toString());
        
     
     }

     // Defina o tipo de conteúdo da resposta para o tipo de arquivo Excel
     response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
     // Defina o cabeçalho da resposta para indicar que é um anexo de arquivo
     response.setHeader("Content-Disposition", "attachment; filename=dellyrelatoniomes.xlsx");

     // Escreva o livro Excel em OutputStream da resposta
     workbook.write(response.getOutputStream());
     workbook.close();
 }
 
 
 
 private List<List> getDadosTabelames() {

     return clienteRepository.relatorioMensal();
 }
 
 
 @GetMapping("/excel/{estado}")
 public void exportToExcelestado(@PathVariable("estado") String estado , HttpServletResponse response) throws IOException {
     
	 
	   // Obtenha os dados da tabela (lista de objetos)
     List<List> tabelaDados = clienteRepository.Foi(
    		 
    		 estado.equalsIgnoreCase("null") || estado == null || estado.isEmpty() ? null : estado	
    		 );;

    		 System.out.println(tabelaDados);
    		 
     // Crie um novo livro Excel
     Workbook workbook = new XSSFWorkbook();

     // Crie uma planilha no livro
//     Sheet sheet = workbook.createSheet("Clientes");
//
//     // Crie um cabeçalho para as colunas
//     Row headerRow = sheet.createRow(0);
//     headerRow.createCell(0).setCellValue("NOME");
//     headerRow.createCell(1).setCellValue("TELEFONE");
//     headerRow.createCell(2).setCellValue("EMAIL");
//     headerRow.createCell(3).setCellValue("ESTADO");
//     headerRow.createCell(4).setCellValue("PAGAMENTOS");
//     //headerRow.createCell(4).setCellValue("PROVINCIA");
//     // ... Adicione mais células de cabeçalho, se necessário
//
//     // Preencha os dados da tabela
//     int rowNum = 1;
//     for (List objeto : tabelaDados) {
//         Row row = sheet.createRow(rowNum++);
//         row.createCell(0).setCellValue(objeto.get(0) != null ? objeto.get(0).toString() : "null");  // Caso seja null, coloca uma string vazia
//         row.createCell(1).setCellValue(objeto.get(1) != null ? objeto.get(1).toString() : "null");
//         row.createCell(2).setCellValue(objeto.get(2) != null ? objeto.get(2).toString() : "null");
//         row.createCell(3).setCellValue(objeto.get(3) != null ? objeto.get(3).toString() : "null");
//         row.createCell(4).setCellValue(objeto.get(4) != null ? objeto.get(4).toString() : "null");
//        // row.createCell(4).setCellValue(objeto.get(4).toString());
//        
//     
//     }

     
 

     Sheet sheet = workbook.createSheet("Clientes");

     // Estilo para o cabeçalho
     CellStyle headerStyle = workbook.createCellStyle();
     headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
     headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
     headerStyle.setBorderBottom(BorderStyle.THIN);
     headerStyle.setBorderTop(BorderStyle.THIN);
     headerStyle.setBorderRight(BorderStyle.THIN);
     headerStyle.setBorderLeft(BorderStyle.THIN);
     headerStyle.setAlignment(HorizontalAlignment.CENTER);

     // Fonte para o cabeçalho
     Font headerFont = workbook.createFont();
     headerFont.setBold(true);
     headerFont.setColor(IndexedColors.WHITE.getIndex());
     headerStyle.setFont(headerFont);

     // Crie o cabeçalho e aplique o estilo
     Row headerRow = sheet.createRow(0);
     String[] headers = {"NOME", "TELEFONE", "EMAIL", "ESTADO", "PAGAMENTOS"};
     for (int i = 0; i < headers.length; i++) {
         Cell cell = headerRow.createCell(i);
         cell.setCellValue(headers[i]);
         cell.setCellStyle(headerStyle);
     }

     // Estilo para as células de dados
     CellStyle dataStyle = workbook.createCellStyle();
     dataStyle.setBorderBottom(BorderStyle.THIN);
     dataStyle.setBorderTop(BorderStyle.THIN);
     dataStyle.setBorderRight(BorderStyle.THIN);
     dataStyle.setBorderLeft(BorderStyle.THIN);
     dataStyle.setAlignment(HorizontalAlignment.LEFT);

     // Fonte para as células de dados
     Font dataFont = workbook.createFont();
     dataFont.setColor(IndexedColors.BLACK.getIndex());
     dataStyle.setFont(dataFont);

     // Preencha os dados da tabela e aplique o estilo
     int rowNum = 1;
     for (List<?> objeto : tabelaDados) {
         Row row = sheet.createRow(rowNum++);
         for (int i = 0; i < objeto.size(); i++) {
             Cell cell = row.createCell(i);
             Object value = objeto.get(i);
             cell.setCellValue(value != null ? value.toString() : "null");
             cell.setCellStyle(dataStyle);
         }
     }

     // Autoajuste a largura das colunas para melhorar a visualização
     for (int i = 0; i < headers.length; i++) {
         sheet.autoSizeColumn(i);
     }

     
     
     
     // Defina o tipo de conteúdo da resposta para o tipo de arquivo Excel
     response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
     // Defina o cabeçalho da resposta para indicar que é um anexo de arquivo
     response.setHeader("Content-Disposition", "attachment; filename=dellyrelatoclientes.xlsx");

     // Escreva o livro Excel em OutputStream da resposta
     workbook.write(response.getOutputStream());
     workbook.close();
 }
}
