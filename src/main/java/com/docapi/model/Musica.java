package com.docapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "Musicas")
public class Musica implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMusica;

    @Column(nullable = false)
    private String artista;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String album;

    @Column(nullable = false)
    private Integer numeroDaMusicaNoAlbum;

    @Schema(type = "String", pattern = "00:00:00")
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private LocalTime duracao;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private LocalDate dataLancamento;

}
