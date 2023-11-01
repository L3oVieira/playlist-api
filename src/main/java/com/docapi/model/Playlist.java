package com.docapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "Playlist")
public class Playlist  implements Serializable {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlaylist;

    @Column(nullable = false)
    private String nome;

    @Schema(type = "String", pattern = "00:00:00")
    @JsonFormat(pattern = "HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private LocalTime duracao;

    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_id_playlist", referencedColumnName = "idPlaylist")
    private List<Musica> musicas;


}
