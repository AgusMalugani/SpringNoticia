/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.eggNews.news.repositorios;

import com.eggNews.news.entidad.Noticia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticiaRepositorio extends JpaRepository<Noticia,Long> {
    
        @Query("SELECT n FROM Noticia n WHERE n.titulo = :titulo")
    public Noticia BuscarPorTitulo(@Param ("titulo")String titulo);
    
  
    
}

