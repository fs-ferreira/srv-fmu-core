package br.com.ferreiradev.fmu.core.infrastructure.id;


import br.com.ferreiradev.fmu.core.infrastructure.id.configuration.SnowflakeProperties;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SnowflakeIdentifierGenerator implements IdentifierGenerator {

    private static SnowflakeGenerator generator;

    @Autowired
    public SnowflakeIdentifierGenerator(SnowflakeProperties props) {
        if (generator == null) {
            generator = new SnowflakeGenerator(props.getDatacenterId(), props.getWorkerId());
        }
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        return generator.nextId();
    }
}
