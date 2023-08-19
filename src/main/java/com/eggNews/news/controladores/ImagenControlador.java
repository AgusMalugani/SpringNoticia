
package com.eggNews.news.controladores;

import com.eggNews.news.entidad.Noticia;
import com.eggNews.news.servicios.NoticiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    @Autowired
    NoticiaServicio ns;
    
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable Long id){ // va acontener un arreglo de byte.
        
        Noticia noticia = ns.getOne(id);
        
       byte[] imagen =  noticia.getImagen().getContenido();
       
       HttpHeaders headers = new HttpHeaders();// estas cabeceras le dicen al navegador que lo que estamos devolviendo es una imagen
       
       headers.setContentType(MediaType.IMAGE_JPEG);
       
       // estadoHTTP es el estado en el que termina el proceso, en la cabecera de http venia un codigo, ej 404, 200, etc
       // vamos a devolver el pedido con un codigo 200
       
       return new ResponseEntity<>(imagen,headers,HttpStatus.OK);
        
    }
    
    
}
