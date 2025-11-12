package br.com.ferreiradev.fmu.core.domain.model.auth;

import br.com.ferreiradev.fmu.core.infrastructure.id.annotation.SnowflakeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "tb_client")
@Data
public class AuthClient {
    @Id
    @SnowflakeId
    private Long id;
    @Column(nullable = false)
    private String clientId;
    @Column(nullable = false)
    private String clientSecret;
    @Column(nullable = false)
    private String redirectUri;
    private String scope;
}
