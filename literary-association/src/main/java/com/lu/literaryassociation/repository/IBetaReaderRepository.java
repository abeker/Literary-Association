package com.lu.literaryassociation.repository;

import com.lu.literaryassociation.entity.BetaReader;
import org.glassfish.jersey.Beta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IBetaReaderRepository extends JpaRepository<BetaReader, UUID> {

    List<BetaReader> findByGenresId(UUID uuid);

}
