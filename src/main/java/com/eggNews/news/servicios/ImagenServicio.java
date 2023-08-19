
package com.eggNews.news.servicios;

import com.eggNews.news.entidad.Imagen;
import com.eggNews.news.excepciones.MiException;
import com.eggNews.news.repositorios.ImagenRepositorio;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ImagenServicio {
    
    @Autowired
    ImagenRepositorio ir;
    
    
    public Imagen guardar(MultipartFile archivo) throws MiException, IOException{ // es el tipo de archivo donde se va a almacenare la imagen
        
        if(archivo != null ){
            try{
                Imagen imagen = new Imagen();
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
               return ir.save(imagen);
                
            }catch(IOException ex){
                System.err.println(ex.getMessage());
                
            }
        }
        
        return null;
        
    }
    
    public Imagen actualizar( MultipartFile archivo, String idImagen ) throws MiException {
               
        if(archivo != null ){
            try{
                Imagen imagen = new Imagen();
                
                if(idImagen != null){
                    Optional<Imagen> respuesta = ir.findById(idImagen);
                    
                    if(respuesta.isPresent()){
                        imagen = respuesta.get();
                    }
                    
                }
                
                imagen.setMime(archivo.getContentType());
                imagen.setNombre(archivo.getName());
                imagen.setContenido(archivo.getBytes());
                
               return ir.save(imagen);
                
            }catch(IOException e){
                System.err.println(e.getMessage());
                
            }
        }
        
        return null;
        
    } 
        
    }