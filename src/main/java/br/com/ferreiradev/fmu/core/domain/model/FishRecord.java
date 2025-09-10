package br.com.ferreiradev.fmu.core.domain.model;

import br.com.ferreiradev.fmu.core.domain.model.enums.FishingPlace;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FishRecord {
    private Fish fish;
    private Double weight;
    private LocalDate date;
    private FishingPlace place;
}
