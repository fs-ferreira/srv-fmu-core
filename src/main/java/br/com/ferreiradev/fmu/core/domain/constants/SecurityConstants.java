package br.com.ferreiradev.fmu.core.domain.constants;

import java.time.Duration;

public final class SecurityConstants {

    public static final Duration ACCESS_TOKEN_TTL = Duration.ofMinutes(5);
    public static final Duration REFRESH_TOKEN_TTL = Duration.ofMinutes(30);
}
