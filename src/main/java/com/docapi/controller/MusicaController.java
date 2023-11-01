package com.docapi.controller;

import com.docapi.model.Musica;
import com.docapi.repository.MusicaRepository;
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
@RequestMapping("/musica")
@Tag(name = "Música")
public class MusicaController {

    @Autowired
    private MusicaRepository MusicaRepository;

    @Operation(summary = "Listar todas as músicas", description = "Lista as músicas presentes no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK, sucesso na requisição",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Musica.class))
            ),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @GetMapping
    public List<Musica> listarMusicas() {
        return MusicaRepository.findAll();
    }

    @Operation(summary = "Adicionar uma nova música", description = "Adiciona um nova música ao banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "OK, música criada com sucesso",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Musica.class))),
            @ApiResponse(responseCode = "400", description = "Erro, os dados fornercidos são incompatíveis", content = @Content),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Musica adicionar(@RequestBody Musica musica) {
        return MusicaRepository.save(musica);
    }

    @Operation(summary = "Editar dados de uma música", description = "Permite editar os dados de uma música cadastrada fornecendo sua ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK, dados salvos",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Musica.class))),
            @ApiResponse(responseCode = "400", description = "Erro, a Id é inválida ou os dados fornercidos são incompatíveis", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PutMapping("/{idMusica}")
    public Musica editar(@PathVariable Integer idMusica, @RequestBody Musica musica) {

        return MusicaRepository.findById(idMusica)
                .map(msc -> {
                    msc.setIdMusica(musica.getIdMusica());
                    msc.setArtista(musica.getArtista());
                    msc.setNome(musica.getNome());
                    msc.setAlbum(musica.getAlbum());
                    msc.setDuracao(musica.getDuracao());
                    msc.setDataLancamento(musica.getDataLancamento());

                    return MusicaRepository.save(musica);
                })
                .orElseGet(() -> {
                    return MusicaRepository.save(musica);
                });
    }

    @Operation(summary = "Remover uma música", description = "Remove uma música cadastrada fornecendo sua ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK, playlist removida",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Musica.class)
                            )),
            @ApiResponse(responseCode = "400", description = "Erro, Id inválido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @DeleteMapping("/{idMusica}")
    @ResponseStatus(HttpStatus.OK)
    public void remover(@PathVariable("idMusica") Integer idMusica) {
        if (idMusica !=  null){
            MusicaRepository.deleteById(idMusica);
        }
    }
}
