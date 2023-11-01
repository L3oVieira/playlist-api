package com.docapi.controller;

import com.docapi.model.Playlist;
import com.docapi.repository.PlaylistRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/playlist")
@Tag(name = "Playlist")
public class PlaylistController {

    @Autowired
    private PlaylistRepository PlaylistRepository;

    @Operation(summary = "Listar todas as playlists", description = "Lista as playlists presentes no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK, sucesso na requisição",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Playlist.class))),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public List<Playlist> listar() {
        return PlaylistRepository.findAll();
    }

    @Operation(summary = "Adicionar uma nova playlist", description = "Adiciona um nova playlist ao banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "OK, playlist criada com sucesso",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Playlist.class))),
            @ApiResponse(responseCode = "400", description = "Erro, os dados fornercidos são incompatíveis"),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Playlist adicionar(@RequestBody Playlist playlist) {
        return PlaylistRepository.save(playlist);
    }

    @Operation(summary = "Editar dados de uma playlist", description = "Permite editar os dados de uma playlist cadastrada fornecendo sua ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK, dados salvos",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Playlist.class))),
            @ApiResponse(responseCode = "400", description = "Erro, a Id é inválida ou os dados fornercidos são incompatíveis"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{idPlaylist}")
    public Playlist editar(@PathVariable Integer idPlaylist, @RequestBody Playlist playlist) {

        return PlaylistRepository.findById(idPlaylist)
                .map(pl -> {
                    pl.setNome(playlist.getNome());
                    pl.setMusicas(playlist.getMusicas());
                    pl.setDuracao(playlist.getDuracao());
                    pl.setIdPlaylist(playlist.getIdPlaylist());

                    return PlaylistRepository.save(playlist);
                })
                .orElseGet(() -> {
                    return PlaylistRepository.save(playlist);
                });
    }

    @Operation(summary = "Remover uma playlist", description = "Remove uma playlist cadastrada fornecendo sua ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, playlist removida",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Playlist.class))),
            @ApiResponse(responseCode = "400", description = "Erro, Id inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{idPlaylist}")
    @ResponseStatus(HttpStatus.OK)
    public void remover(@PathVariable("idPlaylist") Integer idPlaylist) {
        if (idPlaylist !=  null){
            PlaylistRepository.deleteById(idPlaylist);
        }
    }
}
