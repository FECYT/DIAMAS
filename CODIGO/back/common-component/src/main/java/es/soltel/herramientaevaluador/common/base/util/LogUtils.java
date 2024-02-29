package es.soltel.herramientaevaluador.common.base.util;

public class LogUtils {

    public static enum LOG_LEVEL {
        INFO("Info"), DEBUG("Debug"), ERROR("Error"), FATAL("Fatal");

        public String nombre;

        LOG_LEVEL(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

    }

    public static enum POSICION {
        ENTRADA("Entrada"), SALIDA("Salida");

        public String nombre;

        POSICION(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

    }

    public static enum ACCESO {
        LOGIN("Login"), CONSULTA("Consulta"), EDICION("Edicion");

        public String nombre;

        ACCESO(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

    }

    public static enum MODULOS {
        AGENDA("agenda"), BASTANTEO("bastanteo"), CALENDARIO("calendario"), CATALOGO("catalogo"), COMUNES(
                "comunes"), COMUNICACIONES("comunicaciones"), EXPEDIENTES("expedientes"), INFORMES(
                        "informes"), JURISPRUDENCIA("jurisprudencia"), LEXNET("lexnet"), LOGS(
                                "logs"), MENSAJES("mensajes"), PLANTILLAS("plantillas"), USUARIO("usuario");

        public String nombre;

        MODULOS(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

    }
}
