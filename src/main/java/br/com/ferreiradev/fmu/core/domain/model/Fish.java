package br.com.ferreiradev.fmu.core.domain.model;

import br.com.ferreiradev.fmu.core.domain.model.enums.FishType;
import br.com.ferreiradev.fmu.core.infrastructure.id.annotation.SnowflakeId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_fish")
public class Fish {
    @Id
    @SnowflakeId
    private Long id;
    private String name;
    private FishType type;
}
