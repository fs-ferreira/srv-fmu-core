package br.com.ferreiradev.fmu.core.presentation.dto;

import java.util.List;

public record UserRecord(String username, String password, List<String> roles) {
}
