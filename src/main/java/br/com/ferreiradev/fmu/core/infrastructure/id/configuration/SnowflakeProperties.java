package br.com.ferreiradev.fmu.core.infrastructure.id.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "snowflake")
@Data
public class SnowflakeProperties {
    private long datacenterId;
    private long workerId;
}
