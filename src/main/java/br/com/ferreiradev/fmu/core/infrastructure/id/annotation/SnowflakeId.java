package br.com.ferreiradev.fmu.core.infrastructure.id.annotation;

import br.com.ferreiradev.fmu.core.infrastructure.id.SnowflakeIdentifierGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@IdGeneratorType(SnowflakeIdentifierGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD})
public @interface SnowflakeId {
}
