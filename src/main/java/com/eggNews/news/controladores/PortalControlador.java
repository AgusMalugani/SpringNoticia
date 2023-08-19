/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggNews.news.controladores;

import com.eggNews.news.entidad.Noticia;
import com.eggNews.news.servicios.NoticiaServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

      @Controller
 @RequestMapping("/")    
public class PortalControlador {
          
          @Autowired
          NoticiaServicio ns ;
      
  @GetMapping("/")
  public String index(){

return "index.html";
}
  
//    
//   // @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
//    @GetMapping("/perfil")
//    public String perfil(ModelMap modelo, HttpSession session){
//        Noticia noticia =  (Noticia) session.getAttribute("usuariosession"); // atraves de la session me recupera los datos del usuario
//        // en este get no hace falta pasarle el usuario por id, xq ya tenemos el usuario logueado por la session
//        modelo.put("noticia", noticia); // con el modelo lo autocompleto al formulario con los datos que traigo
//        
//        return "noticia_modificar.html";    
//        
//        
//    }
//    
//    @PostMapping("/perfil/{id}")
//    public String actualizar(@RequestParam MultipartFile archivo,@PathVariable Long id,//@RequestParam //String nombre,
//           // @RequestParam String email,@RequestParam String password,@RequestParam String password2,
//     ModelMap modelo ){
//        
//        try {
//            ns.modificarNoticia(archivo, Long.MIN_VALUE, id, id, id);
//            
//            modelo.put("exito", "Usuario actualizado correctamente");
//            
//            return "redirect:/admin/usuarios";
//            
//        } catch (MiException ex) {
//            modelo.put("error", ex.getMessage());
//            modelo.put("nombre", nombre);
//            modelo.put("email", email);
//            
//            return "usuario_modificar.html";
//        }
//        
//    }
//    
//    
//  
    
}
