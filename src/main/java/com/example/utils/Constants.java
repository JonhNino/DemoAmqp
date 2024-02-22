package com.example.utils;

public class Constants {

    public static final String YYYYMMDD = "yyyy-MM-dd";

    public static final String D20000101 = "2000-01-01";
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String MS_NAME = "MS_consultar";
    public static final String EMPTY_STRING = "";
    public static final String REQUIRED_HEADERS_MISSING = "[MS_consultar] All required headers not found";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String ERROR_CODE_6099 = "6099";
    public static final String STD_ERROR_MSG = "[MS_consultar] Something went wrong";
    public static final String STD_ERROR_CODE = "9899";
    public static final String HEADER_REQUIRED_MESSAGE = "[MS_consultar] The header 'Authorization' must not be empty";
    public static final String SERVICIO = "servicio";
    public static final String OPERACION = "operacion";
    public static final String CANAL = "canal";
    public static final String CORRELID = "correlid";
    public static final String TIPODOCUMENTO = "tipoDocumento";
    public static final String NUMERODOCUMENTO = "numeroDocumento";
    public static final String STD_ERROR_NO_SESSION_DATA = "[MS_consultar] No Session Data";
    public static final String STD_ERROR_NO_BODY_DATA = "[MS_consultar] No Body Data";
    public static final String STD_ERROR_NO_CANALES_DATA = "[MS_consultar] No Canales Data";
    public static final String STD_ERROR_NO_PARAMETROS_DATA = "[MS_consultar] No Parametros Data";
    public static final String STD_ERROR_NO_COMISION_DATA = "[MS_consultar] No Comision Data";
    public static final String STD_ERROR_NO_DATA_POLICY = "[MS_consultar] No Policy Data";
    public static final String STD_ERROR_NO_MATCH_ACCOUNT = "[MS_consultar] AccountNumber doesn't Match";

    public static final String ERROR_NOT_FOUND_CUSTOMER_PRODUCT = "[MS_consultar] Error consulting Customer product in database";

    public static final String STD_ERROR_RSA_CHALLENGE = "[LoginService] Error. User has incomplete RSA challenge with transactionId";

    public static final String HIDE = "HIDE";
    public static final String SHOW = "SHOW";
    public static final String ADD = "ADD";
    public static final String DELETE = "DELETE";
    public static final String SI = "Si";
    public static final String NO = "No";

    public static final String RESPONSE = "response";
    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_DESCRIPTION = "errorDescription";
    public static final String ERROR_DESCRIPTION_UPDATE = "[Ms consultar] something went wrong update the account";
    public static final String ERROR_DESCRIPTION_ACTION = "[Ms consultar] Action not supported";

    public static final String CODE_200 = "0000";
    public static final String ERROR_CODE_204 = "204";
    public static final String ERROR_CODE_400 = "400";
    public static final String ERROR_CODE_401 = "401";
    public static final String ERROR_CODE_404 = "404";
    public static final String ERROR_CODE_405 = "405";
    public static final String ERROR_CODE_406 = "406";
    public static final String ERROR_CODE_500 = "500";
    public static final String ERROR_CODE_502 = "502";


    /// XML OUT
    public static final String MESSAGE_TYPE = "messageType";
    public static final String CHANNEL = "channel";
    public static final String SERVICE_SOURCE = "serviceSource";
    public static final String SERVER_SOURCE = "serverSource";
    public static final String SERVICE_DESTINATION = "serviceDestination";
    public static final String SERVER_DESTINATION = "serverDestination";
    public static final String CORRELATION_ID = "correlationId";

    //
    public static final String FUNCTION = "function";
    public static final String SEC = "sec";
    public static final String APP_NAME = "appName";
    public static final String APP_AUTHOR = "appAuthor";
    public static final String APP_START_TIME = "appStartTime";
    public static final String APP_END_TIME = "appEndTime";
    public static final String DATA_RESULT = "dataResult";
    public static final String PERFIL_ID = "perfilId";
    public static final String PERFIL_APELLIDOS = "perfilApellidos";
    public static final String PERFIL_NOMBRES = "perfilNombres";
    public static final String PERFIL_EMAIL = "perfilEmail";
    public static final String PERFIL_PAIS = "perfilPais";
    public static final String PERFIL_TELEFONO1 = "perfilTelefono1";
    public static final String PERFIL_TELEFONO2 = "perfilTelefono2";
    public static final String PERFIL_ADMINISTRADOR = "perfilAdministrador";
    public static final String PERFIL_TIPO = "perfilTipo";
    public static final String PERFIL_SEGMENTO = "perfilSegmento";
    public static final String PERFIL_ID_GERENTE = "perfilIdGerente";
    public static final String PERFIL_TIMEOUT = "perfilTimeout";
    public static final String PERFIL_CODIGO_EMPLEADO_OFICIAL = "perfilCodigoEmpleadoOficial";
    public static final String PERFIL_NOMBRE_OFICIAL = "perfilNombreOficial";
    public static final String PERFIL_BENEFICIARIOS_EN_LINE = "perfilBeneficiariosEnLine";
    public static final String PERFIL_CODIGO = "perfilCodigo";
    public static final String PERFIL_CODIGO_OFICINA = "perfilCodigoOficina";
    public static final String PERFIL_NOMBRE_OFICINA = "perfilNombreOficina";
    public static final String PERFIL_CODIGO_ZONA = "perfilCodigoZona";
    public static final String PERFIL_NOMBRE_ZONA = "perfilNombreZona";
    public static final String PERFIL_NUM_DOCUMENTO2 = "perfilNumDocumento2";
    public static final String PERFIL_TIPO_DOCUMENTO2 = "perfilTipoDocumento2";
    public static final String PERFIL_EMAIL_VALIDADO_ALGUNA_VEZ = "perfilEmailValidadoAlgunaVez";
    public static final String PERFIL_SUB_SEGMENTO = "perfilSubSegmento";
    public static final String PERFIL_STATUS = "perfilStatus";
    public static final String PERFIL_REQUEST_TYPE = "perfilRequestType";
    public static final String PERFIL_DIRECCION_ICBS = "perfilDireccionICBS";
    public static final String PERFIL_TELEFONO_ICBS = "perfilTelefonoICBS";
    public static final String PERFIL_CIUDAD_ICBS = "perfilCiudadICBS";

    // Identificacion
    public static final String IDENTIFICACION_NUMERO = "identificacionNumero";
    public static final String IDENTIFICACION_NOMBRE = "identificacionNombre";
    public static final String IDENTIFICACION_TIPO = "identificacionTipo";
    public static final String IDENTIFICACION_ID_GERENTE = "identificacionIdGerente";
    public static final String IDENTIFICACION_CODIGO_EMPLEADO_OFICIAL = "identificacionCodigoEmpleadoOficial";
    public static final String IDENTIFICACION_NOMBRE_OFICIAL = "identificacionNombreOficial";
    public static final String IDENTIFICACION_RESULT = "identificacionResult";
    public static final String IDENTIFICACION_STATUS_DESCRIPTION = "identificacionStatusDescription";

    // Empresa
    public static final String EMPRESA_NOMBRE = "empresaNombre";

    // Producto
    public static final String PRODUCTO_ID = "productoId";
    public static final String PRODUCTO_TIPO = "productoTipo";
    public static final String PRODUCTO_NUMERO = "productoNumero";
    public static final String PRODUCTO_SUB_TIPO = "productoSubTipo";
    public static final String PRODUCTO_MONEDA = "productoMoneda";
    public static final String PRODUCTO_ALIAS = "productoAlias";
    public static final String PRODUCTO_VISIBLE = "productoVisible";
    public static final String PRODUCTO_BALANCE = "productoBalance";

    // Campo
    public static final String CAMPO_NOMBRE = "campoNombre";
    public static final String CAMPO_VALOR = "campoValor";

    private Constants(){}
}

