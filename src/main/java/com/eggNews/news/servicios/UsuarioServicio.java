/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eggNews.news.servicios;

import com.eggNews.news.entidad.Imagen;
import com.eggNews.news.entidad.Usuario;
import com.eggNews.news.enumeraciones.Rol;
import com.eggNews.news.excepciones.MiException;
import com.eggNews.news.repositorios.ImagenRepositorio;
import com.eggNews.news.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    UsuarioRepositorio us;
//
//    @Autowired
//    ImagenServicio is;
//
//    @Autowired
//    ImagenRepositorio ir;

    @Transactional
    public void registrar(//MultipartFile archivo, 
            String nombre, String email, String password, String password2) throws MiException {
        validar(nombre, email, password, password2);
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);

//        Imagen imagen = is.guardar(archivo);
//
//        usuario.setImagen(imagen);

        us.save(usuario);

    }

    public void actualizar(//MultipartFile archivo, 
            String id, String nombre, String email, String password, String password2) throws MiException {
        validar(nombre, email, password, password2);
        Optional<Usuario> respuesta = us.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();

            usuario.setEmail(email);
            usuario.setNombre(nombre);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
//
//            String idImagen = null;
//            if (usuario.getImagen() != null) {
//                idImagen = usuario.getImagen().getId();
//            }
//            Imagen imagen = is.actualizar(archivo, idImagen);
//            usuario.setImagen(imagen);

            us.save(usuario);

        }

    }

    public void validar(String nombre, String email, String password, String password2) throws MiException {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre no puede estar vacio o ser nulo");
        }

        if (email == null || email.isEmpty()) {
            throw new MiException("El email no puede estar vacio o ser nulo");
        }

        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("El password no puede estar vacio o ser nulo y debe tener almenos 5 digitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas deben ser iguales");
        }

    }
    
    
    public List listarUsuarios(){
        List<Usuario> usuarios = us.findAll(); // traeme todos los usuarios 
        return usuarios;
        
    }
    @Transactional // va a modificar la bd
    public void cambiarRol(String id){
        Optional<Usuario> respuesta = us.findById(id);
        if(respuesta.isPresent()){
            Usuario usuario = respuesta.get();
            
            if(usuario.getRol().equals(Rol.USER)){
                // si el rol de ese usuario es user, qiero cambiarlo a admin
                usuario.setRol(Rol.ADMIN);
                
            } else if(usuario.getRol().equals(Rol.ADMIN)){
                usuario.setRol(Rol.USER);
            }
            
        }
        
    }
    
    
    

    // cuando un usuario inicie sesion
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // va a entrar a este metodo
        Usuario usuario = us.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()); // y va a otorgar los permisos que tenga este usuario

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        } else {
            return null;
        }

    }
    
    public Usuario getOne(String id){
        return us.getOne(id);
        
    }
    

}
