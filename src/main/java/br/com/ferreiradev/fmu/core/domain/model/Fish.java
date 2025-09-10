package br.com.ferreiradev.fmu.core.domain.model;

import br.com.ferreiradev.fmu.core.domain.model.enums.FishType;
import br.com.ferreiradev.fmu.core.infrastructure.id.annotation.SnowflakeId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Fish {
    @Id
    @SnowflakeId
    private Long id;
    private String name;
    private FishType type;

    public Fish(String name) {
        this.name = name;
    }
}
