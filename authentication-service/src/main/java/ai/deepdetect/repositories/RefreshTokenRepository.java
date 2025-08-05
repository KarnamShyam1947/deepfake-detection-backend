package ai.deepdetect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ai.deepdetect.entities.RefreshTokenEntity;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer>{
    RefreshTokenEntity findByRefreshToken(String refreshToken);
}
