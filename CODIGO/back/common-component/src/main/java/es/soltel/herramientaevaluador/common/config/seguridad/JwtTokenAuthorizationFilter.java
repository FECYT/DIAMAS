package es.soltel.herramientaevaluador.common.config.seguridad;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.soltel.herramientaevaluador.common.base.exception.ExceptionResponse;
import es.soltel.herramientaevaluador.common.base.util.Constants;
import es.soltel.herramientaevaluador.common.base.vo.MessageResponseVO;
import es.soltel.herramientaevaluador.common.base.vo.PermisoInner;
import es.soltel.herramientaevaluador.common.base.vo.ResponseVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClaims;

public class JwtTokenAuthorizationFilter extends OncePerRequestFilter {

    private SeguridadPropertiesConfig seguridadPropertiesConfig;

    public JwtTokenAuthorizationFilter(SeguridadPropertiesConfig seguridadPropertiesConfig) {
        this.seguridadPropertiesConfig = seguridadPropertiesConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Recoger el header donde viene el token
        String header = request.getHeader(seguridadPropertiesConfig.getJwt().getHeader());

        // 2. Validar el encabezado y el prefijo (Bearer)
        if (header == null || !header.startsWith(seguridadPropertiesConfig.getJwt().getPrefix())) {
            filterChain.doFilter(request, response); // Si no es válido se continua con la cadena de filtros (¿acceso a
            // recurso público?)
            return;
        }

        // Si hay un token de autenticación, se valida que sea correcto

        // 3. Se obtiene el token
        String token = header.replace(seguridadPropertiesConfig.getJwt().getPrefix(), "");

        try {
            // Las excepciones se lanzarán al crearse los claims (por ejemplo cuando el
            // token ha expirado

            // 4. Validar el token
            Claims claims = null;
            try {
                claims = Jwts.parser().setSigningKey(seguridadPropertiesConfig.getJwt().getSecret().getBytes())
                        .parseClaimsJws(token).getBody();
            }
            catch (ExpiredJwtException e) {
                // Si el token ha expirado, salta ExpiredJwtException y hay que rearmar las
                // claims manualmente aprovechando el token
                String[] split_string = token.split("\\.");
                String base64EncodedBody = split_string[1];
                String body = new String(Base64.getDecoder().decode(base64EncodedBody));
                @SuppressWarnings("unchecked")
                HashMap<String, Object> mapaClaims = new ObjectMapper().readValue(body, HashMap.class);
                claims = new DefaultClaims(mapaClaims);
                throw e;
            }

            String username = claims.getSubject();
            if (username != null) {
                @SuppressWarnings("unchecked")
                List<Map<String, String>> authorities = (List<Map<String, String>>) claims.get("permisos");
                List<PermisoInner> permisos = new ArrayList<PermisoInner>();
                if (authorities != null) {
                    for (Map<String, String> permiso : authorities) {
                        PermisoInner permisoInner = new PermisoInner(permiso.get("authority"), permiso.get("ajeno"));
                        permisos.add(permisoInner);
                    }
                }
                // 5. Crear un objeto de autenticación
                // UsernamePasswordAuthenticationToken: Lo usa spring para representar al
                // usuario autenticado o que se va a autenticar
                // Necesitará una lista de autoridades, con la interfaz GrantedAuthority, siendo
                // SimpleGrantedAuthority una implementación de esa interfaz
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
                        permisos);
                // 6. Autenticar al usuario
                // Para spring security
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            // Se continua con la cadena de filtros
            filterChain.doFilter(request, response);

        }
        catch (ExpiredJwtException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ExceptionResponse exception = new ExceptionResponse();
            exception.setTimestamp(LocalDateTime.now());
            exception.setError(e.getMessage());
            exception.setMessage("Error en la validación del token");
            response.setContentType("application/json");
            @SuppressWarnings({ "rawtypes" })
            ResponseVO<?> responseException = new ResponseVO();
            List<MessageResponseVO> messages = new ArrayList<MessageResponseVO>();
            messages.add(new MessageResponseVO("Error en la validación del token", Constants.ERROR));
            responseException.setMessages(messages);

            response.getWriter().write(new ObjectMapper().writeValueAsString(responseException));
        }
        // Si hay fallo, nos garanrizamos que el usuario no se autentica
        catch (Exception e) {
            // Si hay fallo, nos garanrizamos que el usuario no se autentica
            SecurityContextHolder.clearContext();
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ExceptionResponse exception = new ExceptionResponse();
            exception.setTimestamp(LocalDateTime.now());
            exception.setError(e.getMessage());
            exception.setMessage("Error en el servidor");
            response.setContentType("application/json");
            @SuppressWarnings({ "rawtypes" })
            ResponseVO<?> responseException = new ResponseVO();
            List<MessageResponseVO> messages = new ArrayList<MessageResponseVO>();

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            messages.add(new MessageResponseVO(sw.toString(), Constants.ERROR));
            responseException.setMessages(messages);

            response.getWriter().write(new ObjectMapper().writeValueAsString(responseException));

        }
    }

}
