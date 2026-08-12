package com.felix.escuela.entities;

import com.felix.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ALUMNOS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Alumno {
    //Se crean los atributos de la entidad dependiendo la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUMNO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false, length = 50)
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "MATRICULA", nullable = false, length = 10, unique = true)
    private String matricula;

    @Builder.Default
    @Column(name = "FECHA_INGRESO")
    private LocalDate fechaIngreso = LocalDate.now();

    @Builder.Default
    @OneToMany(mappedBy = "alumno")
    private List<Inscripcion> inscripciones = new ArrayList<>();

///Se crea un constructor y posteriormente se validan datos
    private void validarDatos(String nombre, String apellidoPaterno, String apellidoMaterno){

        StringCustomUtils.validarTamanio(nombre, 1, 50,
                "El nombre es requerido y debe tener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoPaterno,1,50,
                "El apellido paterno es requedrido y debe tener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoMaterno,1,50,
                "El apellido materno es requedrido y debe tener entre 1 y 50 caracteres");


    }
/////Checa si hay algun cambio en los datos, si no hay los deja asi y si hay pasa al siguiente metodo
    public boolean cambioEnDatos(String nombre, String apellidoPaterno, String apellidoMaterno){
        return !this.nombre.equals(nombre) ||
                !this.apellidoPaterno.equals(apellidoPaterno) ||
                !this.apellidoMaterno.equals(apellidoMaterno);
    }
///Asignar datos academicos ya que se solo se recibe el nombre, apellidoPa y apellidoMa, email y matricula se generan solos en base de datos por eso se separan
    public void asignarDatosAcademicos(String email, String matricula){
        StringCustomUtils.validarTamanio(email,1,100,
                "El email es requedrido y debe tener entre 1 y 100 caracteres");

        StringCustomUtils.validarTamanio(matricula,10,10,
                "La matricula es requedrida y debe tener exactamente 10 caracteres");

        this.email = email.toLowerCase().trim();
        this.matricula = matricula.trim();
    }

/////Crear contructor para actualizar si es que hubo datos que se cambiaron
    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String matricula) {

        validarDatos(nombre, apellidoPaterno, apellidoMaterno);
        asignarDatosAcademicos(email,matricula);

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();
    }

    public BigDecimal calcularPromedio(){
        List<BigDecimal> calificaciones = inscripciones.stream()
                .map(Inscripcion::getCalificacion)
                .filter(Objects::nonNull)
                .map(Calificacion::getCalificacion)
                .filter(Objects::nonNull).toList();

        if(calificaciones.isEmpty())
            return BigDecimal.ZERO;

        BigDecimal suma = calificaciones.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return suma.divide(
                 BigDecimal.valueOf(calificaciones.size()),
                2, RoundingMode.HALF_UP);
    }
}
