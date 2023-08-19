/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggNews.news.controladores;

import com.eggNews.news.entidad.Noticia;
import com.eggNews.news.entidad.Usuario;
import com.eggNews.news.excepciones.MiException;
import com.eggNews.news.servicios.NoticiaServicio;
import com.eggNews.news.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
          UsuarioServicio us ;
      
  @GetMapping("/")
  public String index(){

return "index.html";
}
 
    @GetMapping("/registrar")
    public String registrar(){
        
        return "registro.html";
    }
    
    
    @PostMapping("/registro")
    public String registro(//MultipartFile archivo  ,
            @RequestParam String nombre,@RequestParam String email,  @RequestParam String password, String password2, ModelMap modelo){
        try{
            us.registrar(//archivo, 
                    nombre, email, password, password2);
            
            modelo.put("exito", "Usuario cargado con exito");
            
            return "index.html";
        }catch(MiException ex){
            modelo.put("error", ex.getMessage());
            return "registro.html";
        }
        
        
    }
    
    
    
    @GetMapping("/login")
    public String login( @RequestParam(required = false) String error, ModelMap modelo ){
        
        if(error != null){
            modelo.put("error", "usuario o contraseña invalidas");
        }
        
        return "login.html";
    }
    
    
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')") // es necesario para que solo puedan verlo las personas que esten logueadas
    @GetMapping("/inicio")
    public String inicio(HttpSession session){
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");// para obtener la sesionb
        
        if(logueado.getRol().toString().equals("ADMIN")){  
            
            return "redirect:/admin/dashboard"; // si es rol admin, va a esa pagina, en lugar de inicio
            
        }
        
        return"inicio.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuariosession"); // atraves de la session me recupera los datos del usuario
        // en este get no hace falta pasarle el usuario por id, xq ya tenemos el usuario logueado por la session
        modelo.put("usuario", usuario); // con el modelo lo autocompleto al formulario con los datos que traigo
        
        return "usuario_modificar.html";    
        
        
    }
    
    @PostMapping("/perfil/{id}")
    public String actualizar(//@RequestParam MultipartFile archivo,
            @PathVariable String id,@RequestParam String nombre,
            @RequestParam String email,@RequestParam String password,@RequestParam String password2, ModelMap modelo ){
        
        try {
            us.actualizar(//archivo, 
                    id, nombre, email, password, password2);
            
            modelo.put("exito", "Usuario actualizado correctamente");
            
            return "redirect:/admin/usuarios";
            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            
            return "usuario_modificar.html";
        }
        
    }
    
}
