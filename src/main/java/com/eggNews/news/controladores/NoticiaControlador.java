package com.eggNews.news.controladores;

import com.eggNews.news.entidad.Noticia;
import com.eggNews.news.excepciones.MiException;
import com.eggNews.news.servicios.NoticiaServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {
    
     @Autowired // para inicializarlo
     private  NoticiaServicio ns;
      
      
      @GetMapping("/registrar") // localhost:8080/autor/registrar
  public String registrar(){

return "cargar_noticia.html";
}
  
  
    @PostMapping("/registro")  //para obtener los datos del form
    public String registro( @RequestParam(required = false) Long id ,@RequestParam String titulo, @RequestParam String cuerpo  , ModelMap modelo, @RequestParam String link  ){  // recibo x parametro lo del form
        
          try {
              ns.crearNoticia(id, titulo, cuerpo,link); // lo envio a la creacion de autores
    modelo.put("exito", "La noticia fue creada con exito!");

          } catch (MiException ex) {
     modelo.put("error", ex.getMessage());          
              return "cargar_noticia.html"; // SI HAY UN ERROR, ENTONCES VUELKVE AL FORMULARIO
          }
          
        return "index.html"; // SI ESTA TODO CORRECTO, SE VUELVE AL INDEX
        
  }
    
    @GetMapping("/lista")
    public String lista(ModelMap modelo){
      List<Noticia> noticias = ns.listarNoticias();
      modelo.addAttribute("noticias", noticias);
        return "noticia_list.html";
    }
    
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo){
        
        modelo.put("noticia", ns.getOne(id));
        
        return "noticia_modificar.html";
        
        
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar( @PathVariable Long id, String titulo, String cuerpo, ModelMap modelo, String link ){
          try {
             ns.modificarNoticia(id, titulo, cuerpo,link);
              return "redirect:../lista";
          } catch (MiException ex) {
              modelo.put("error", ex.getMessage());
              return "noticia_modificar.html";
          }
        
    }
    
       @GetMapping("/eliminar/{id}")
    public String cambiarRol(@PathVariable Long id) throws MiException{
      ns.eliminarNoticia(id);
        return "redirect:../lista";
    }
    
    
    
}
