/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.eggNews.news.entidad;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Imagen implements Serializable {

    
    @Id
   @GeneratedValue(generator = "uuid")
     @GenericGenerator(name = "uuid", strategy="uuid2")
    private String id;
    
    private String mime; // asigna el formato del archivo de la imagen
    
    private String nombre;
    @Lob @Basic(fetch = FetchType.LAZY) // le decimos a spring que este atributo puede ser grande, q puede manejar muchos byte 
    // le decimos en el basico si va a ser pererzo o lazy, este contenido se va a cargar solamente cuando lo pidamos, asi es mas liviano nuestro producto
    // cuando accedo a mi imagen todos estos atributos se cargan. este atributo lazy lo va a traer, solamente cuando traemos un GET!
    
    private byte[] contenido;

    public Imagen() {
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Imagen{" + "id=" + id + ", mime=" + mime + ", nombre=" + nombre + ", contenido=" + contenido + '}';
    }
}
