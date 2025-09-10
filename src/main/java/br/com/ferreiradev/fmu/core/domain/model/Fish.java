package br.com.ferreiradev.fmu.core.domain.model;

import br.com.ferreiradev.fmu.core.domain.model.enums.FishType;
import lombok.Data;

@Data
public class Fish {
    private String name;
    private FishType type;
}
