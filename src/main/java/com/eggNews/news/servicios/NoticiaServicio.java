/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggNews.news.servicios;

import com.eggNews.news.entidad.Imagen;
import com.eggNews.news.entidad.Noticia;
import com.eggNews.news.excepciones.MiException;
import com.eggNews.news.repositorios.NoticiaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class NoticiaServicio {
    
     @Autowired
   NoticiaRepositorio noticiaRepositorio;
     @Autowired
     ImagenServicio is;

    @Transactional
    public void crearNoticia(Long id, String titulo, String cuerpo, String link) throws MiException {
       validar(id,titulo,cuerpo, link);
        Noticia noticia = new Noticia();
       noticia.setId(id);
       noticia.setTitulo(titulo);
       noticia.setCuerpo(cuerpo);
       noticia.setLink(link);

        noticiaRepositorio.save(noticia);

    }

    public List listarNoticias() {
        List<Noticia> listaNot = noticiaRepositorio.findAll();
        return listaNot;
    }

    public void modificarNoticia( Long id, String titulo, String cuerpo, String link) throws MiException {
         validar(id,titulo,cuerpo,link);
        Optional<Noticia> respuestaNoticia = noticiaRepositorio.findById(id);

        if (respuestaNoticia.isPresent()) {
            Noticia noticia = respuestaNoticia.get();

            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticia.setLink(link);
//            
//                String idImagen = null;
//            if (noticia.getImagen() != null) {
//                idImagen = noticia.getImagen().getId();
//            }
//            Imagen imagen = is.actualizar(archivo, idImagen);
//            noticia.setImagen(imagen);
//            

            noticiaRepositorio.save(noticia);
        

        }

    }
    
    
    
        public Noticia getOne(Long id){
            return noticiaRepositorio.getOne(id);
        }
        
        
    
        public void eliminarNoticia(Long id) throws MiException{
            Optional<Noticia> respuestaNoticia = noticiaRepositorio.findById(id);
            
            if(respuestaNoticia.isPresent()){
                Noticia noticia = respuestaNoticia.get();
                noticiaRepositorio.delete(noticia);
            }
            
        }
    

    private void validar(Long id, String titulo, String cuerpo, String link) throws MiException {

        if (titulo.isEmpty() || titulo == null) {

            throw new MiException("El titulo no puede estar vacio o nulo");
        }
        
        if (id == null) {

            throw new MiException("El id no puede estar vacio o nulo");
        }
        
        if (cuerpo.isEmpty() || titulo == null) {

            throw new MiException("El cuerpo no puede estar vacio o nulo");
        }

             if (link.isEmpty() || link == null) {

            throw new MiException("El link no puede estar vacio o nulo");
        }
        
    }
    
    
}
