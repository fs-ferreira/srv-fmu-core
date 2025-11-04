package br.com.ferreiradev.fmu.core.domain.model;

import br.com.ferreiradev.fmu.core.infrastructure.id.annotation.SnowflakeId;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "tb_user")
@Data
public class User {
    @Id
    @SnowflakeId
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Type(ListArrayType.class)
    @Column(columnDefinition = "varchar[]")
    private List<String> roles;

}
