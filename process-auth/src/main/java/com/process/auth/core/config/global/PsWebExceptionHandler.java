package com.process.auth.core.config.global;

import com.process.auth.core.config.global.domain.PsFeignExceptionResolver;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * 统一web异常处理
 *
 * @author Danfeng
 * @since 2018/12/10
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class PsWebExceptionHandler {

    private final List<PsFeignExceptionResolver> resolvers;

//    @ExceptionHandler({FeignException.class})
//    public ResponseEntity feignException(FeignException exception, HttpServletResponse response) {
//        int status = exception.status();
//        Optional<PsFeignExceptionResolver> exceptionResolver = resolvers.stream().filter(resolver -> resolver.support(status)).findAny();
//        if (exceptionResolver.isPresent()) {
//            return exceptionResolver.get().handle(status);
//        }
//        throw new RuntimeException(exception);
//    }

}
