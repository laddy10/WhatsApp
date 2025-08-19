Feature: Validar los flujos de WhatsApp

  @Whatsapp_01
  Scenario Outline: Validar menú principal
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo |
      | <saludo> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Validar menu principal
    And Vaciar chat

    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|



  @Whatsapp_02
  Scenario Outline: Consultar el saldo de la linea
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo |
      | <saludo> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingresar al menu todo sobre tu linea
    Then Consultar saldo de la linea
    And Vaciar chat

    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|


  @Whatsapp_03
  Scenario Outline: Consultar los consumos
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo |
      | <saludo> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingresar al menu todo sobre tu linea
    Then Consultar los consumos
    And Vaciar chat

    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|


  @Whatsapp_04
  Scenario Outline: Conoce - Mejora tu plan
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo |
      | <saludo> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingresar al menu todo sobre tu linea
    Then Conoce o mejora tu plan
    And Vaciar chat

    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|


  @Whatsapp_05
  Scenario Outline: Consultar otra linea
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo |
      | <saludo> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingresar al menu todo sobre tu linea
    Then Consulta otra linea
    And Vaciar chat

    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|


  @Whatsapp_06
  Scenario Outline: Tu lealtad merece mas - Claro musica
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo |
      | <saludo> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingresar al menu todo sobre tu linea
    And Ingresar a la opcion tu lealtad merece mas
    And Seleccionar la opcion claro musica
    Then Validar direccionamiento claro musica
    And Vaciar chat


    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|


  @Whatsapp_08
  Scenario Outline: Tu lealtad merece mas - Claro Drive
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo |
      | <saludo> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingresar al menu todo sobre tu linea
    And Ingresar a la opcion tu lealtad merece mas
    And Seleccionar la opcion claro Drive
    Then Validar direccionamiento claro Drive
    And Vaciar chat

    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|


  @Whatsapp_10
  Scenario Outline: Haz tus recargas
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo | | numero   |
      | <saludo> || <numero> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingresar a haz tus recargas
    And Seleccionar el valor de la recarga
      |valor|
      |<valor>|
    Then Validar direccionamiento al medio de pago
    And Vaciar chat

    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|

  ############################################################################################


  @Whatsapp_11
  Scenario Outline: Otras opciones - cambiate a postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo | | numero   |
      | <saludo> || <numero> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingreso a otras opciones
    Then Validar el menu otras opciones
    And Vaciar chat

    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|

  @Whatsapp_13

  Scenario Outline: Otras opciones - cambiate a postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo | | numero   |
      | <saludo> || <numero> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingreso a otras opciones
    And Cambiate a postpago
    And Vaciar chat

    Examples:
      | saludo |  numero | valor |

  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|

  @Whatsapp_14

  Scenario Outline: Otras opciones - cambiate a postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo | | numero   |
      | <saludo> || <numero> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingreso a otras opciones
    And Tus servicios LDI
    And Validar menu LDI
    And Vaciar chat

    Examples:
      | saludo |  numero | valor |

  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|



  @Whatsapp_16
  Scenario Outline: Otras opciones - cambiate a postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo | | numero   |
      | <saludo> || <numero> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingreso a otras opciones
    And Renueva tu sim
    And Vaciar chat
    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|



  @Whatsapp_17
  Scenario Outline: Validar menu Compra por WhatsApp
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo |
      | <saludo> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingresa al menu Compra por WhatsApp
    And Clic boton selecciona
    Then Validar el menu Compra por WhatsApp
    And Vaciar chat


    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|


  @Whatsapp_18
  Scenario Outline: Validar menu compra tus paquetes - todo incluido
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo |
      | <saludo> |
    And Seleccionar linea de consulta
      | numero   |
      | <numero> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
      | numero   |
      | <numero> |
    And Ingresar al menu compra tus paquetes
    And Clic boton selecciona
    Then Validar los paquetes todo incluido disponibles
    And Vaciar chat


    Examples:
      | saludo |  numero | valor |
  ##@externalData@./src/test/resources/data/Datos.xlsx@correo@
   |Hola   |3558   |$ 50.000|









