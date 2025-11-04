package br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.thrower;

import br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.ResourceNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.constants.ErrorConstants.NOT_FOUND_MESSAGE;


@Aspect()
@Component
public class NotEmptyCollectionAspect {

    @Around("@annotation(br.com.ferreiradev.fmu.core.infrastructure.adapter.rest.exception.thrower.annotation.NotEmptyCollection)")
    public Object checkNotEmpty(ProceedingJoinPoint pjp) throws Throwable {
        Object result = pjp.proceed();

        if (result instanceof List<?> list && list.isEmpty()) {
            throw new ResourceNotFoundException(NOT_FOUND_MESSAGE);
        }

        if (result instanceof Collection<?> col && col.isEmpty()) {
            throw new ResourceNotFoundException(NOT_FOUND_MESSAGE);
        }
        return result;
    }
}
