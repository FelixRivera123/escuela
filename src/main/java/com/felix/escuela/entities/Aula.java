package com.felix.escuela.entities;

import com.felix.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AULAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;

    private void validarDatos(String nombre){
        StringCustomUtils.validarTamanio(nombre, 5, 100,
                "El tamaño del nombre debe ser de mínimo 5 y máximo de 100 caracteres");
    }

    public void actualizar(String nombre, Integer capacidad){
        validarDatos(nombre);
        this.nombre = nombre.trim();
        this.capacidad = capacidad;
    }
}
