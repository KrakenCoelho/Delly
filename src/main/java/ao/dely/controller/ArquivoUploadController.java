package ao.dely.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
//import javax.swing.text.html.HTMLDocument.Iterator;
import javax.xml.bind.DatatypeConverter;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import lombok.extern.slf4j.Slf4j;






@RestController
@RequestMapping("/upload")
@Slf4j
public class ArquivoUploadController{
	
	
	
	
	
	 @RequestMapping(value="/", method=RequestMethod.GET)
	   public String Home(){
	    return "/index";
	    
	    }
	 
	 
	//-------- admin Funcionarios
	 @RequestMapping(value="/index",method=RequestMethod.GET)
	   public String Index(){
	    return "/index";
	    
	    }
	 

//	 public String singleFileUpload (HttpServletRequest request,MultipartFile file, String pasta) {
//	        String novoNome="sem-foto.png";
//	        if(!(file.isEmpty())) {
//	        try {
//	             String realPathtoUploads =  request.getServletContext().getRealPath("upload/");
//	             novoNome = new Date().getTime() +file.getOriginalFilename().substring(file.getOriginalFilename().length()-5);
//	             //novoNome = new Date().getTime() +file.getOriginalFilename();
//	             //novoNome = file.getOriginalFilename();
//	             novoNome = novoNome.replace(")","");
//	             
//	             System.out.println(realPathtoUploads+"--"+novoNome);
//	             String filePath = realPathtoUploads +pasta +"/"+ novoNome;
//                 File dest = new File(filePath);
//                 file.transferTo(dest);
//                
//                
//                 BufferedImage image = ImageIO.read(new File(filePath));
//
//                 OutputStream os =new FileOutputStream(filePath);
//
//                 Iterator<ImageWriter>writers = ImageIO.getImageWritersByFormatName("jpg");
//                 ImageWriter writer = (ImageWriter) writers.next();
//
//                 ImageOutputStream ios = ImageIO.createImageOutputStream(os);
//                 writer.setOutput(ios);
//
//                 ImageWriteParam param = writer.getDefaultWriteParam();
//                
//                 param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//                 param.setCompressionQuality(0.2f);
//                 writer.write(null, new IIOImage(image, null, null), param);
//                
//                 os.close();
//                 ios.close();
//                 writer.dispose();
//	            } catch (IOException e) {
//	                
//	                System.out.println("ERRO DE UPLOADS: "+ e.getMessage());
//	            }}
//	         return novoNome;  
//
//	    }

	 
	 public String singleFileUpload(HttpServletRequest request, MultipartFile file, String pasta) {
		    String novoNome = "sem-foto.png";
		    if (!file.isEmpty()) {
		        try {
		            String realPathtoUploads = request.getServletContext().getRealPath("upload/");
		            String originalFilename = file.getOriginalFilename();
		            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

		            // Define um novo nome para o ficheiro
		            novoNome = new Date().getTime() + "." + fileExtension;
		            novoNome = novoNome.replace(")", "");

		            System.out.println(realPathtoUploads + "--" + novoNome);
		            String filePath = realPathtoUploads + pasta + "/" + novoNome;
		            File dest = new File(filePath);
		            file.transferTo(dest);

		            // Processar apenas se for uma imagem suportada
		            if (fileExtension.matches("jpg|jpeg|png|gif")) {
		                BufferedImage image = ImageIO.read(new File(filePath));
		                if (image != null) {
		                    OutputStream os = new FileOutputStream(filePath);

		                    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(fileExtension);
		                    if (writers.hasNext()) {
		                        ImageWriter writer = writers.next();

		                        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		                        writer.setOutput(ios);

		                        ImageWriteParam param = writer.getDefaultWriteParam();
		                        if (param.canWriteCompressed()) {
		                            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		                            param.setCompressionQuality(0.8f); // Ajusta a qualidade de compressão
		                        }
		                        writer.write(null, new IIOImage(image, null, null), param);

		                        os.close();
		                        ios.close();
		                        writer.dispose();
		                    } else {
		                        System.out.println("Formato de imagem não suportado: " + fileExtension);
		                    }
		                }
		            } else {
		                System.out.println("Ficheiro não é uma imagem, mas foi guardado: " + fileExtension);
		            }
		        } catch (IOException e) {
		            System.out.println("ERRO DE UPLOADS: " + e.getMessage());
		        }
		    }
		    return novoNome;
		}

	 
//	 public String singleFileUpload (HttpServletRequest request,MultipartFile file, String pasta) {
//	        String novoNome="sem-foto.png";
//	        if(!(file.isEmpty())) {
//	        try {
//	             String realPathtoUploads =  request.getServletContext().getRealPath("upload/");
//	             novoNome = new Date().getTime() +file.getOriginalFilename().substring(file.getOriginalFilename().length()-5);
//	             //novoNome = new Date().getTime() +file.getOriginalFilename();
//	             //novoNome = file.getOriginalFilename();
//	             novoNome = novoNome.replace(")","");
//	             
//	             System.out.println(realPathtoUploads+"--"+novoNome);
//	                String filePath = realPathtoUploads +pasta +"/"+ novoNome;
//	                 File dest = new File(filePath);
//	                 file.transferTo(dest);    
//	            } catch (IOException e) {
//	                
//	                System.out.println("ERRO DE UPLOADS: "+ e.getMessage());
//	            }}
//	         return novoNome;  
//
//	    }
	 
	 
	 public String singleFileUploadEdit (HttpServletRequest request,MultipartFile file, String pasta, String prev_image) {
	        String novoNome=prev_image;
	        if(!(file.isEmpty())) {
	        try {
	             String realPathtoUploads =  request.getServletContext().getRealPath("upload/");
	             novoNome = new Date().getTime() +file.getOriginalFilename().substring(file.getOriginalFilename().length()-5);
	             novoNome = novoNome.replace(")", "");
	             System.out.println(realPathtoUploads+"--"+novoNome);
	                String filePath = realPathtoUploads +pasta +"/"+ novoNome;
	                 File dest = new File(filePath);
	                 file.transferTo(dest);    
	            } catch (IOException e) {
	                
	                System.out.println("ERRO DE UPLOADS: "+ e.getMessage());
	            }}
	         return novoNome;  

	    }

	    
	 
	 @ResponseBody
	    @RequestMapping("/ckeditorUpload")
	    public String ckeditorUpload(@RequestParam("upload") MultipartFile file,HttpServletRequest request) throws Exception {
		 
		 String newFilename = singleFileUpload(request,file,"uploads");
		 
	
	
		 String ret = "{\n " +
			        "    \"uploaded\": 1,\n" +
			        "    \"fileName\": \""+newFilename+"\",\n" +
			        "    \"url\": \"http://localhost:8080/imgs/uploads/"+newFilename+"\"\n" +
			        "}";
		 
	     return ret;
	     
	  // To sent server
	     
	    /* String ret = "{\n " +
	                "    \"uploaded\": 1,\n" +
	                "    \"fileName\": \""+newFilename+"\",\n" +
	                "    \"url\": \"http://clientes.dexa.ao/imgs/uploads/"+newFilename+"\"\n" +
	                "}";
	                
	        return ret; */      
	     
		 
		 
	  // To sent server Rellevant
		    /*String ret = "{\n " +
		                "    \"uploaded\": 1,\n" +
		                "    \"fileName\": \""+newFilename+"\",\n" +
		                "    \"url\": \"https://dexa_hr.appteste.info/imgs/uploads/"+newFilename+"\"\n" +
		                "}";
		                
		        return ret;*/    
	     
	 	
	    }
	
	 
	 
	 
	 public String DataActual() {
		 Date date = new Date();
		 SimpleDateFormat formatter= new SimpleDateFormat("dd-MM-yyyy");
		
		 return formatter.format(date);
	}
	 
	 public String Datadia() {
		 Date date = new Date();
		 SimpleDateFormat formatter= new SimpleDateFormat("dd");
		
		 return formatter.format(date);
	}
	 
	 public String Datames() {
	 Date date = new Date();
	 SimpleDateFormat formatter= new SimpleDateFormat("MM");
	
	 return formatter.format(date);
}
	 
	 
	 public String Dataano() {
		 Date date = new Date();
		 SimpleDateFormat formatter= new SimpleDateFormat("yyyy");
		
		 return formatter.format(date);
	}
	
	 public Long Data() {
		 Date date = new Date();
		 SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd");
		
		 return Long.parseLong(formatter.format(date));
	}
	 
	 public static String DataE() {
		 Date date = new Date();
		 SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		
		 return formatter.format(date);
	}
	 
	 public static String Dataw() {
		 Date date = new Date();
		 SimpleDateFormat formatter= new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		 return formatter.format(date);
	}
	 
	 public static String f(String data) {
	     String [] dt = data.split("-");

	    return dt[2]+"/"+dt[1]+"/"+dt[0];
	}
	 
	 
	 public static String Codifica(Long id) {
		 
		 String str = DatatypeConverter.printBase64Binary(id.toString().getBytes());			
		 //System.out.println(str);
		 return str;
	}
		 
	 
	public static Long Decodifica(String id) {
			 
		 String res = new String(DatatypeConverter.parseBase64Binary(id));
		 //System.out.println(res);
		 return Long.parseLong(res);
		 
	}
	 
	
	public  static String Mescre(String valor) {
		 String mes="";
	  switch (valor) {
			case "01":	mes="Janeiro"; break;
			case "02":	mes="Fevereiro"; break;
			case "03":	mes="Março"; break;
			case "04":	mes="Abril"; break;
			case "05":	mes="Maio"; break;
			case "06":	mes="Junho"; break;
			case "07":	mes="Julho"; break;
			case "08":	mes="Agosto"; break;
			case "09":	mes="Setembro"; break;
			case "10":	mes="Outubro"; break;
			case "11":	mes="Novembro"; break;
			case "12":	mes="Dezembro"; break;
				
			default:
				break;
			}
	  return mes;
}
	 
	
	public String GeraReferencia() {
        Date date = new Date();
        SimpleDateFormat formatter= new SimpleDateFormat("HHmmssddMM");
       
        return formatter.format(date);
   }
	

	
	public static String gerarCodigo() {
        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digito = random.nextInt(10); // Gera um número aleatório de 0 a 9
            codigo.append(digito);
        }

        return codigo.toString();
    }

	
	
}