package es.soltel.recolecta.aspect;

import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import es.soltel.herramientaevaluador.common.base.exception.RestException;
import es.soltel.herramientaevaluador.common.config.seguridad.JwtConfig;
import es.soltel.herramientaevaluador.common.config.seguridad.SeguridadPropertiesConfig;
import es.soltel.recolecta.service.impl.SystemLogsServiceImpl;
import es.soltel.recolecta.vo.SystemLogsVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    private SystemLogsServiceImpl systemLogsService;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private SeguridadPropertiesConfig seguridadPropertiesConfig;

    @Before("execution(* es.soltel.recolecta.controller.*.*(..)) && !@annotation(es.soltel.recolecta.anottation.NoLogging)")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        SystemLogsVO log = new SystemLogsVO();
        log.setUserId(getCurrentUserId());
        log.setActionType("Invocación del método: " + className + ": " + joinPoint.getSignature().getName());
        log.setDateTime(LocalDateTime.now());
        log.setSourceIp(request.getRemoteAddr());
        log.setStatus("Started");
        //log.setNumRepositories(getNumRepositories());
        systemLogsService.create(log);


        logger.info("Entrada al método", className + ": " + methodName);
    }


    // Método que se ejecuta cuando el método termina con éxito
    @AfterReturning("execution(* es.soltel.recolecta.controller.*.*(..)) && !@annotation(es.soltel.recolecta.anottation.NoLogging)")
    public void afterControllerMethodSuccess(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        SystemLogsVO log = new SystemLogsVO();
        log.setUserId(getCurrentUserId());
        log.setActionType("Finalización exitosa del método: " + className + ": " + methodName);
        log.setDateTime(LocalDateTime.now());
        log.setSourceIp(request.getRemoteAddr());
        log.setStatus("Finished");
        //log.setNumRepositories(getNumRepositories());
        systemLogsService.create(log);

        logger.info("Salida exitosa del método", className + ": " + methodName);
    }

    // Método que se ejecuta cuando hay un error en el método
    @AfterThrowing(pointcut = "execution(* es.soltel.recolecta.controller.*.*(..)) && !@annotation(es.soltel.recolecta.anottation.NoLogging)", throwing = "ex")
    public void afterControllerMethodException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        SystemLogsVO log = new SystemLogsVO();
        log.setUserId(getCurrentUserId());
        log.setActionType("Error en el método: " + className + ": " + methodName);
        log.setDateTime(LocalDateTime.now());
        log.setSourceIp(request.getRemoteAddr());
        log.setStatus("Error");
        //log.setNumRepositories(getNumRepositories());
        systemLogsService.create(log);

        logger.error("Excepción en el método", className + ": " + methodName, ex);
    }

    private String getCurrentUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            DecodedJWT jwt = JWT.decode(token);

            String userId = jwt.getClaim("idUser").asLong().toString(); // Cambio aquí para obtener el ID de usuario

            return userId;
        }

        return null;
    }

    private int getNumRepositories() throws RestException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();

            if (header != null && header.startsWith("Bearer ")) {

                JwtConfig jwtConfig = seguridadPropertiesConfig.getJwt(); // Asume que tienes acceso a esta configuración aquí también

                Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes())
                        .parseClaimsJws(token);

                Claims claims = claimsJws.getBody();

                // Obtener el campo numRepositorios
                int numberRepositoryResponse = claims.get("numRepositorios", Integer.class);

                return numberRepositoryResponse;
            }
            else {

                return 0;
            }

        }
        return 0;

    }
}
