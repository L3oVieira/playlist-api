package com.docapi.controller;

import com.docapi.model.Usuario;
import com.docapi.repository.UsuarioRepository;
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
@RequestMapping("/usuario")
@Tag(name = "Usuário")
public class UsuarioController {

    @Autowired
    private UsuarioRepository UsuarioRepository;

    @Operation(summary = "Listar todos os usuários", description = "Lista os usuários  presentes no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, sucesso na requisição"),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public List<Usuario> listar() {
        return UsuarioRepository.findAll();
    }

    @Operation(summary = "Adicionar um novo usuário", description = "Adiciona um novo usuário ao banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "OK, usuário criado com sucesso",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usuario.class))

            ),
            @ApiResponse(responseCode = "400", description = "Erro, os dados fornercidos são incompatíveis"),
            @ApiResponse(responseCode = "404", description = "Requisição não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario adicionar(@RequestBody Usuario usuario) {
        return UsuarioRepository.save(usuario);
    }

    @Operation(summary = "Editar dados de um usuário", description = "Permite editar os dados de um usuário cadastrado fornecendo sua ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK, dados salvos",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Erro, a Id é inválida ou os dados fornercidos são incompatíveis"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping("/{idUsuario}")
    public Usuario editar(@PathVariable Integer idUsuario, @RequestBody Usuario usuario) {

        return UsuarioRepository.findById(idUsuario)
                .map(user -> {
                    user.setIdUsuario(usuario.getIdUsuario());
                    user.setNome(usuario.getNome());
                    user.setNascimento(usuario.getNascimento());
                    return UsuarioRepository.save(usuario);
                })
                .orElseGet(() -> {
                    return UsuarioRepository.save(usuario);
                });
    }

    @Operation(summary = "Remover um usuário", description = "Remove um usuário cadastrado fornecendo sua ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK, usuário removido",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Erro, Id inválido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    public void remover(@PathVariable("idUsuario") Integer idUsuario) {
        if (idUsuario !=  null){
            UsuarioRepository.deleteById(idUsuario);
        }
    }
}
