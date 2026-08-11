package com.felix.escuela.entities;

import com.felix.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CURSOS")
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;

    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;

    private void validarDatos(String nombre, String descripcion){
        StringCustomUtils.validarTamanio(nombre, 5, 100, "" +
                "El tamaño del nombre debe ser de mínimo 5 y máximo de 100 caracteres");
        StringCustomUtils.validarTamanio(descripcion, 10, 200,
                "El tamaño de la descripción debe ser de mínimo 10 y máximo de 200 caracteres");
    }

    public void actualizar(String nombre, String descripcion, Integer creditos){
        validarDatos(nombre, descripcion);
        this.nombre = nombre.trim();
        this.descripcion = descripcion.trim();
        this.creditos = creditos;
    }
}
