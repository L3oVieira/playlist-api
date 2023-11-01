package com.docapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringdocConfig {
    @Bean
    public OpenAPI playlistsAPI(){
        return new OpenAPI()
                .info(new Info().title("Playlist API")
                        .description("Api destinada a organizar a relação entre usuários, playlists e as músicas contidas em cada playlist.")
                        .version("1.0.0")
                        .license(new License().name("Licensa Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório da PlaylistAPI")
                        .url("https://github.com/L3oVieira/playlist-api"));
    }
}
