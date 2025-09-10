package br.com.ferreiradev.fmu.core.infrastructure.id.annotation;

import br.com.ferreiradev.fmu.core.infrastructure.id.SnowflakeIdentifierGenerator;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@IdGeneratorType(SnowflakeIdentifierGenerator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, METHOD})
public @interface SnowflakeId {
}
