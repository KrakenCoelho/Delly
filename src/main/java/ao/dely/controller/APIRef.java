package ao.dely.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import ao.dely.model.Cliente;




@Service
public class APIRef  {

//	@Autowired
//	DCRepository dc;
	
//	@Autowired
//	PagamentoRepository pgr;

	
	
	//ArquivoUploadController anexo = new ArquivoUploadController();
	 
	
	
//	 private final String API_KEY = "srarbb65qj4phisdt94nr8nnpm5bem5u";//teste
//	 private final String LINK ="https://api.sandbox.proxypay.co.ao";
//	 
	 
   private final String API_KEY = "a95teci62t7v15d63s3om2recu4fp5v3";
   private final String LINK = "https://api.proxypay.co.ao";
	 
//	public String gerarReferencia(Cliente cliente,Long  valor, String package_type) {
//	      final String NF = geraRef();
//	      if (NF != null) {
//	    	  
//	         System.out.println("REFERENCE: " + NF + "\nNAME: " + cliente.getNome_c());
//	     
//	         JSONObject custom_fields = new JSONObject();
//	         custom_fields.put("name", cliente.getNome_c())
//	         			  .put("description", "Subscription")
//	         			  .put("telephone", cliente.getNumero())
//	         			  .put("callback_url", "https://legoo.digital/app/payment/payments.php");
//	         
////	         Calendar now = Calendar.getInstance();
////	         Calendar novoCalendario = (Calendar)now.clone();
////	         novoCalendario.add(1, 5);
//	         
//	         
//	         Calendar now = Calendar.getInstance();
//	         now.add(Calendar.HOUR_OF_DAY, 2);
//	         
//	         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//	         String endDatetime = dateFormat.format(now.getTime());
//	         
//	         System.out.println("DATA EXPIRAÇÃO: "+endDatetime);
//	         
//	         JSONObject reference = new JSONObject();
//	         reference.put("amount", valor)
//	         		  .put("end_datetime", "2023-10-12 15")
//	         		  .put("custom_fields", custom_fields);
//	         
//	         try {
//	             OkHttpClient client = new OkHttpClient();
//	             
//	             MediaType mediaType = MediaType.parse("application/json");
//	             
//	             RequestBody body = RequestBody.create(mediaType, reference
//	            		 					   .toString());
//	             
//	             Request request = (new Request.Builder())
//	            		                       .url(LINK+"/references/" + NF)
//	            		                       .put(body)
//	            		                       .addHeader("Authorization", "Token "+API_KEY)
//	            		                       .addHeader("Accept", "application/vnd.proxypay.v2+json")
//	            		                       .addHeader("Content-Type", "application/json")
//	            		                       .build();
//	             Response response = client.newCall(request)
//	            		                   .execute();
//	             System.out.println("RESPONSE: " + response); 
//	             
//	             if (response.code() > 199 && response.code() < 300) {
//	                try {
//	                   String MSG_SMS = "Prezado(a) " + cliente.getNome_c() + ", pague o seu bilhete no ATM ou aplicativo do seu banco, Entidade: 01068" + ", Referencia: " + NF + ", Montante Akz: " + valor;
//	                   
//	                   
//	                 System.out.println(MSG_SMS);
////	            	 Response r =enviarSms(cliente.getNumero(),MSG_SMS);
////	            	 r.body().close();
//	            	 
////	            	 Response t=enviarMensagem("+244944272703");
////	            	 t.body().close();
//	                } catch (Exception e1) {
//	                	System.out.println("Erro: " + e1);
//	                }
//	                response.body().close();
//	                return NF;
//	             }
//	          } catch (Exception e) {
//	        	  System.out.println("ERROR: "+ e);
//	          }
//	      }
//
//	      return null;
//	   }
//   
   
   
	 //@Scheduled(fixedDelay = 20000)
	public String gerarReferencia(Cliente cliente,Long  valor, String package_type,HttpServletRequest requestD) {
	      final String NF = geraRef();
	      if (NF != null) {
	    	  
	         System.out.println("REFERENCE: " + NF + "\nNAME: " + cliente.getNome());
	     
	         JSONObject custom_fields = new JSONObject();
	         custom_fields.put("name", cliente.getNome())
	         			  .put("description", "Subscription")
	         			  .put("telephone", cliente.getTelefone())
	         			 .put("callback_url", "https://"+requestD.getServerName()+"/app/payment/payments.php");
//	         			  .put("callback_url", "https://legoo.digital/app/payment/payments.php");
	         			  
	         Calendar now = Calendar.getInstance();
	         Calendar novoCalendario = (Calendar)now.clone();
	         novoCalendario.add(Calendar.HOUR_OF_DAY, 2);
	         JSONObject reference = new JSONObject();
	         reference.put("amount", valor)
	         		  .put("end_datetime", (new SimpleDateFormat("yyyy-MM-dd hh:mm"))
	         		  .format(novoCalendario.getTime())).put("custom_fields", custom_fields);
	         
	         try {
	             OkHttpClient client = new OkHttpClient();
	             
	             MediaType mediaType = MediaType.parse("application/json");
	             
	             RequestBody body = RequestBody.create(mediaType, reference
	            		 					   .toString());
	             
	             Request request = (new Request.Builder())
	            		                       .url(LINK+"/references/" + NF)
	            		                       .put(body)
	            		                       .addHeader("Authorization", "Token "+API_KEY)
	            		                       .addHeader("Accept", "application/vnd.proxypay.v2+json")
	            		                       .addHeader("Content-Type", "application/json")
	            		                       .build();
	             Response response = client.newCall(request)
	            		                   .execute();
	             System.out.println("RESPONSE: " + response); 
	             
	             if (response.code() > 199 && response.code() < 300) {
//	                try {
//	                   String MSG_SMS = "Prezado(a) " + cliente.getNome() + ", pague o seu bilhete no ATM ou aplicativo do seu banco, Entidade: 01068" + ", Referencia: " + NF + ", Montante Akz: " + valor;
//	                   
//	                   
//	                 System.out.println(MSG_SMS);
//	            	 Response r =enviarSms(cliente.getTelefone(),MSG_SMS);
//	            	 r.body().close();
//	            	 
////	            	 Response t=enviarMensagem("+244944272703");
////	            	 t.body().close();
//	                } catch (Exception e1) {
//	                	System.out.println("Erro: " + e1);
//	                }
	            	 
	                return NF;
	             }
	             response.body().close();
	          } catch (Exception e) {
	        	  System.out.println("ERROR: "+ e);
	          }
	         
	      }

	      return null;
	   }
	 

	 
	public String geraRef() {
       String R = new SimpleDateFormat("HHmsddMMyy").format(new Date()).substring(0, 9);
//        String R = "1015200709";
//		//return R;
//		
//      if(pgr.id(R)<1) {
//      return R;}
//  else {
//	  System.out.println("Entrou");
//      return new SimpleDateFormat("HHmsSSSddMMyy").format(new Date()).substring(0, 9) ;
//   }
		return R;
}

	

	public static Response enviarSms(String telefone, String msg) throws IOException{

        String xmlString = "<SMS>"
                + "<authentification> <username>sservices</username> <password>smsillico</password> </authentification>"
                + "<message> <sender>Delly</sender> "
                + "<text>"
                + msg
                + "</text> </message> <recipients> "
                + "<gsm>"
                + telefone
                + "</gsm>"
                + "</recipients></SMS>";

               
           
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/xml");
            RequestBody body = RequestBody.create(mediaType, xmlString);
       
            Request request = new Request.Builder()
                .url("https://api.smsillico-ao.com/sendsms/xml")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();        
               
                    return client.newCall(request).execute();
                   
               
            }
	
	
	  
//	public String Apagaref(String referencia) {
//		
//		try {
//			OkHttpClient client = new OkHttpClient();
//
//			Request request = new Request.Builder()
//			    .url(LINK+"/references/"+referencia)
//			    .delete()
//			    .addHeader("Authorization", "Token " + API_KEY)
//			    .addHeader("Accept", "application/vnd.proxypay.v2+json")
//			    .build();
//
//			Response response = client.newCall(request).execute();
//			System.out.println("Resposta: "+ response);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//		return "Sim";
//	}
	
	
	
	




//	    private static final String API_URL = "https://graph.facebook.com/v17.0/messages";
//	    private static final String ACCESS_TOKEN = "EAAN5vQzEsZBwBOZBl3ZANvNzSWVIZAtrru0vSbFMZBwFC0F7COOde6suRRhTZBm2ZBNnA3lK8zS7ZAUm8ciUV8zkOT7M0y5xNvRy1cEUZBMVkSavx7qWPGkWvPTMRTqt0LFBb5kQ3pwbkDtlIYrPjIBPvTuKObIPn788lMEGShrmjriK40FrKaXLZBsHRBJZC1KhrQdyyi9qZBfz5rGctiMHdQAZD";
//
//	    public Response enviarMensagem(String destinatario, String Mensagem) throws IOException {
//	    	OkHttpClient httpClient = new OkHttpClient();
//	     
//	            
//
//	            MediaType mediaType = MediaType.parse("application/json");
////	            String jsonPayload = "{ messaging_product: whatsapp, to: " + destinatario + ", " +
////	                    "type: template, template: { name:hello_world, " +
////	                    "language: { code: en_US } } }";
//	            
//	            String jsonPayload = "{ \"messaging_product\": \"whatsapp\", \"to\": \"" + destinatario + "\", " +
//	                    "\"type\": \"template\", \"template\": { \"name\": \""+Mensagem+", " +
//	                    "\"language\": { \"code\": \"en_US\" } } }";
//
//
//	            RequestBody body = RequestBody.create(mediaType, jsonPayload);
//
//	            Request request = new Request.Builder()
//	                    .url(API_URL)
//	                    .post(body)
//	                    .addHeader("Authorization", "Bearer " + ACCESS_TOKEN)
//	                    .addHeader("Content-Type", "application/json")
//	                    .build();
//
//	            Response response = httpClient.newCall(request).execute();
//
//	            // Processar a resposta, se necessário
//	            String responseBody = response.body().string();
//	            System.out.println("Resposta da API: " + responseBody);
//	        
//			return httpClient.newCall(request).execute();
//	    }
//
//	    
//	


	    
	


	
	
	
}


