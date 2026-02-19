Feature: Validar los flujos de WhatsApp en prepago

  @Whatsapp_01 
  Scenario: Validar menú principal
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Validar menu principal
    And Vaciar chat



  @Whatsapp_02 
  Scenario: Consultar el saldo de la linea
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu todo sobre tu linea
    Then Consultar saldo de la linea
    And Vaciar chat



  @Whatsapp_03 
  Scenario: Consultar los consumos
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu todo sobre tu linea
    Then Consultar los consumos
    And Vaciar chat



  @Whatsapp_04
  Scenario: Conoce - Mejora tu plan
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu todo sobre tu linea
    Then Conoce o mejora tu plan
    And Vaciar chat



  @Whatsapp_05
  Scenario: Consultar otra linea
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu todo sobre tu linea
    Then Consulta otra linea
    And Vaciar chat



  @Whatsapp_06
  Scenario: Tu lealtad merece mas - Claro musica
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu todo sobre tu linea
    And Ingresar a la opcion tu lealtad merece mas
    And Seleccionar la opcion claro musica
    Then Validar direccionamiento claro musica
    And Vaciar chat


  @Whatsapp_08
  Scenario: Tu lealtad merece mas - Claro Drive
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu todo sobre tu linea
    And Ingresar a la opcion tu lealtad merece mas
    And Seleccionar la opcion claro Drive
    Then Validar direccionamiento claro Drive
    And Vaciar chat



  @Whatsapp_10
  Scenario: Haz tus recargas
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar a haz tus recargas
    And Seleccionar el valor de la recarga
    Then Validar direccionamiento al medio de pago
    And Vaciar chat



  ############################################################################################


  @Whatsapp_11
  Scenario: Otras opciones - cambiate a postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingreso a otras opciones
    Then Validar el menu otras opciones
    And Vaciar chat


  @Whatsapp_13
  Scenario: Otras opciones - cambiate a postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingreso a otras opciones
    And Cambiate a postpago
    And Vaciar chat


  @Whatsapp_14
  Scenario: Otras opciones - cambiate a postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingreso a otras opciones
    And Tus servicios LDI
    And Validar menu LDI
    And Vaciar chat



  @Whatsapp_16
  Scenario: Otras opciones - cambiate a postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingreso a otras opciones
    And Renueva tu sim
    And Vaciar chat



  @Whatsapp_17
  Scenario: Validar menu Compra por WhatsApp
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresa al menu Compra por WhatsApp
    And Clic boton selecciona
    Then Validar el menu Compra por WhatsApp
    And Vaciar chat


  @Whatsapp_18
  Scenario: Validar menu compra tus paquetes - todo incluido
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu compra tus paquetes
    And Clic boton selecciona
    Then Validar los paquetes todo incluido disponibles
    And Vaciar chat


  @Whatsapp_19
  Scenario: Validar menu compra tus paquetes - de voz
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu compra tus paquetes
    And Clic boton selecciona
    Then Validar los paquetes de voz disponibles
    And Vaciar chat



  @Whatsapp_20
  Scenario: Validar menu compra tus paquetes - de datos
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu compra tus paquetes
    And Clic boton selecciona
    Then Validar los paquetes de datos disponibles
    And Vaciar chat




 # @Whatsapp_21
  Scenario: Validar menu compra tus paquetes - de apps
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu compra tus paquetes
    And Clic boton selecciona
    Then Validar los paquetes de apps disponibles
    And Vaciar chat



  @Whatsapp_22
  Scenario: Validar menu compra tus paquetes - internacionales
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu compra tus paquetes
    And Clic boton selecciona
    Then Validar los paquetes internacionales disponibles
    And Vaciar chat



  #@Whatsapp_23_INHABILITADO
  Scenario: Validar menu compra tus paquetes - comunidad sorda
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu compra tus paquetes
    And Clic boton selecciona
    Then Validar los paquetes comunidad sorda disponibles
    And Vaciar chat



  @Whatsapp_24
  Scenario: Validar menu compra tus paquetes - regala un paquete
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu compra tus paquetes
    And Clic boton selecciona
    Then Validar regala un paquete y redireccion portal pagos
    And Vaciar chat


  @Whatsapp_25
  Scenario: Validar flujo completo de compra de paquete
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar al menu compra tus paquetes
    And Clic boton selecciona
    Then Validar flujo de pago de paquete seleccionado
    And Vaciar chat



  @Whatsapp_26
  Scenario: Haz tus recargas - Validar menu de recargas
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingresar a haz tus recargas
    And Validar menu de haz tus recargas
    And Vaciar chat



  @Whatsapp_27
  Scenario: Tus equipos - Tus equipos en soporte
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Seleccionar Tus equipos
    And Seleccionar Tus equipos en soporte
    Then Ingresar a Ver puntos fisicos
    And Vaciar chat


  @Whatsapp_28
  Scenario: Validar el menu principal de Tus equipos
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Seleccionar Tus equipos
    Then Validar el menu de tus equipos
    And Vaciar chat


  @Whatsapp_29
  Scenario: Otras opciones - Tus PQRS radicados
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    And Ingreso a otras opciones
    Then Seleccionar tus PQRS radicados
    And Vaciar chat


  @Whatsapp_30
  Scenario: Consultar otra cuenta
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal
    Then Validar consultar otra cuenta
    And Vaciar chat

