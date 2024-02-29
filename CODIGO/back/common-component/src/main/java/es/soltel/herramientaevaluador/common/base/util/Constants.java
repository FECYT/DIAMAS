package es.soltel.herramientaevaluador.common.base.util;

public class Constants {

    // Servicios
    public static final String SERVICE_PARAMETER = "parameter";
    public static final String SERVICE_USER = "user";
    public static final String SERVICE_ROLE = "role";
    public static final String SERVICE_ROLE_USER = "roleUser";
    public static final String SERVICE_LANGUAGE = "language";
    public static final String SERVICE_MESSAGE = "message";
    public static final String SERVICE_TRANSLATED_MESSAGE = "translatedMessages";
    public static final String SERVICE_BANNER = "banner";
    public static final String SERVICE_TIER = "tier";
    public static final String SERVICE_MEMBER_TIER_HISTORY = "membertierhistory";
    public static final String SERVICE_MEMBER_STATUS_HISTORY = "memberstatushistory";
    public static final String SERVICE_MEMBER = "member";
    public static final String SERVICE_REFERRED_MEMBER = "referredmember";
    public static final String SERVICE_RULE = "rule";
    public static final String SERVICE_TRANSACTION = "transaction";
    public static final String SERVICE_PROVISION = "provision";
    public static final String SERVICE_BOND = "bond";
    public static final String SERVICE_DASHBOARD = "dashboard";
    public static final String SERVICE_LOG = "log";
    public static final String SERVICE_INTEGRATION = "integration";
    public static final String SERVICE_LOGIN = "login";

    // ERROR
    public static final String GENERIC_ERROR = "Internal server error";
    public static final String BAD_CREDENTIALS = "Bad credentials";
    // Message code
    public static final String INFO = "INFO";
    public static final String WARNING = "WARNING";
    public static final String ERROR = "ERROR";

    public static final String OPERATOR_LANGUAGE = "language";

    //plantillas


    //Claves cifrado login RSA
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbt8T/AbWNVkuGtqzN7uqlYScXfrIZ97D/SnbbT1q2QVmnVrzSFJxOfR0hUgqYC4F1H5v67Klk7P8vAowy34TeiiqC+bUFAztGa6G+18g63QUWJc5hUmN8uOFor7wA/ldR9OfedA3wUqNj8LH2HTCb+y9Dpp5rTEa8kVhTkWiYrwIDAQAB";

    public static final String PRIVATE_KEY = "MIICXAIBAAKBgQCbt8T/AbWNVkuGtqzN7uqlYScXfrIZ97D/SnbbT1q2QVmnVrzSFJxOfR0hUgqYC4F1H5v67Klk7P8vAowy34TeiiqC+bUFAztGa6G+18g63QUWJc5hUmN8uOFor7wA/ldR9OfedA3wUqNj8LH2HTCb+y9Dpp5rTEa8kVhTkWiYrwIDAQABAoGABWrXR6ffkRM3fXBNgryVHTvMCF04BP9BASCuEeMo3L5ZSCEUYJqm9XQU11vrUm7UCicXXhttqT01g4UrK3nrPWSip7JLAe3/n6n2zGboMf42KcKY4rVJZ0hbj3GdsFm2H1pO8c061Qj2CZVSkFzpgvLvP9SSgl+5PJ9kz2C/0YECQQDvJRaOtbZsk0IUow4okFVCFrENssfmH3ibTolOsXepk2DKRQecq76MWLqn/mSa3xYB3DYgHeMWrqlBXlOMfqTXAkEAprFmeRDMOtxgrkwfbSFW9ojldTpxNQLUT45nVMmkWIej3nn06bn33Gcek7A8PAt5zCpSQt73V1JsKvzVLWzX6QJBAIP6K+DGV87D1gwdkIrCXMsESuIMcFWHuL+9L/nF5wwm0ZvPTMwh3B8IOLrNoif2Rebw6M4AsHqsGayBqgo3OjECQHZ3PUxtE7gBRvSYRP1Z5GPxhqF5l1sFV73yhp1LcPE59Mv4AIbbE7wfzStI5IYH4denfC7qJv54JUenyBkwcYECQCp+SCWEDxeV6mXhkiwmSoooh9BFUVPHkABriTqeU3t4l0lduk4k8ZKONTV5HVPTYvjMWqg7AwZC8Vv4he+W160=";

    //Mensaje lexnet

    public static final String ESCRITO_TRAMITE = "2";
    public static final String INICIADOR_ASUNTO = "6";
    public static final String INICIADOR_EJECUCION = "7";
    public static final String RECURSO_QUEJA = "15";
    public static final String RECURSO_CASACION = "17";
    public static final String PERSONACION = "14";


    public static final Integer TOTAL_REGISTROS = 9999;


    //Office365
    public static final String TIPO_DATO_MENSAJE = "#microsoft.graph.fileAttachment";

    //Portafirmas
    public static final String PORTAFIRMAS_APPLICATION = "GALERA";
    public static final String PORTAFIRMAS_REFERENCIA = "Referencia";
    public static final String PORTAFIRMAS_SUBJECT = "Firma requerida desde el subsistema GALERA de eGobex.";
    public static final String PORTAFIRMAS_TEXTO = "Se requiere la firma del documento desde el subsistema GALERA de eGobex.";

    public static final Long ALERTAS_ID_TIPO_ALERTA_EXPEDIENTE = 1L;
    public static final Long ALERTAS_ID_TIPO_ALERTA_AGENDA = 2L;
    public static final String ALERTAS_URL_AGENDA = "/agenda";
    public static final String ALERTAS_URL_ACTUACIONES = "/actuaciones";
    public static final String USUARIO_OFFICE365 = "uLictor@juntaex.es";


    public static String TIPO_DIA_ADMINISTRATIVO = "342";
    public static String TIPO_DIA_JUDICIAL = "341";


    // Posibles estados de los registros en una tabla
    public static enum ESTADO_REGISTRO {
        INACTIVO(0), ACTIVO(1), BORRADO(2);

        private Integer codigo;

        ESTADO_REGISTRO(Integer codigo) {
            this.codigo = codigo;
        }

        public Integer getCodigo() {
            return codigo;
        }
    }

    // Posibles estados de los registros en una tabla
    public static enum TIPO_IMPLICADO {
        CONSEJERIA(1250L), CONTRARIO(1251L), ABOGADO(1258L), PROCURADOR(1254L), RESPONSABLE(1255L);

        private Long id;

        TIPO_IMPLICADO(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }

}
