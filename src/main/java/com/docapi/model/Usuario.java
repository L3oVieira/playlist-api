package com.docapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable {
    @Id
    @Column(name = "idUsuario", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable = false)
    private String nome;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private LocalDate nascimento;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id_usuario", referencedColumnName = "idUsuario")
    private List<Playlist> playlist;

}
