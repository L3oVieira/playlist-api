package com.docapi.repository;

import com.docapi.model.Musica;
import com.docapi.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicaRepository extends JpaRepository<Musica, Integer> {

}
