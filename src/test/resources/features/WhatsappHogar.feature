Feature: Validar los flujos de WhatsApp para líneas Hogar


  Scenario: Validar opciones rapidas - ver factura y pagar
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu de tus pagos y facturas
    And Clic boton selecciona
    And Clic Tu factura



  Scenario: Validar opciones rapidas - soporte hogar-internet
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Ingresar en opciones rapidas a soporte hogar
    And Seleccionar el servicio fallas en tu navegacion
    Then Validar opcion reiniciar modem
    And Vaciar chat



  Scenario: Validar opciones rapidas - soporte hogar-television
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Ingresar en opciones rapidas a soporte hogar
    And Seleccionar el servicio fallas en tu en tu señal de TV
    Then Validar imagen de recomendaciones
    And Vaciar chat



  Scenario: Validar opciones rapidas - soporte hogar-telefonia
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Ingresar en opciones rapidas a soporte hogar
    And Seleccionar el servicio fallas al hacer o recibir llamadas
    Then Validar imagen de recomendaciones
    And Vaciar chat


  @EXUS_HOG_WSP_07
  Scenario: Validar menu principal -Tus pagos y factura-tu factura
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Seleccionar la opcion Conoce y descarga tu factura
    Then Validar mensaje de validacion de identidad
    And Ingresar el codigo de verificacion
    Then Validar mensaje de identidad confirmada con exito
    And Ver factura hogar
    And Vaciar chat


  @EXUS_HOG_WSP_08
  Scenario: Menu principal -Tus pagos y factura-Tu factura-Pagar factura TC
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Ingresar al link de pago hogar
    And Realizar transaccion de pago con tarjeta de credito hogar


  @EXUS_HOG_WSP_09
  Scenario: Menu principal -Tus pagos y factura-Tu factura-Pagar factura Boton Bancolombia
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Ingresar al link de pago hogar
    And Realizar transaccion de pago con Boton Bancolombia hogar


  @EXUS_HOG_WSP_10
  Scenario: Menu principal -Tus pagos y factura-Tu factura-Pagar factura Daviplata
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Ingresar al link de pago hogar
    And Realizar transaccion de pago con Daviplata hogar


  @EXUS_HOG_WSP_11
  Scenario: Menu principal -Tus pagos y factura-Tu factura-Pagar factura Mis tarjetas registradas
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Ingresar al link de pago hogar
    And Realizar transaccion de pago con Mis tarjetas registradas hogar


  @EXUS_HOG_WSP_12
  Scenario: Validar menu principal -Tus pagos y factura-Tu factura-Pagar factura PSE
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Ingresar al link de pago hogar
    And Realizar transaccion de pago con PSE hogar


  @EXUS_HOG_WSP_13
  Scenario: Menu principal -Tus pagos y factura-Otras facturas Claro
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Clic boton selecciona
    And Seleccionar otras facturas claro
    And Vaciar chat


  @EXUS_HOG_WSP_14
  Scenario: Menu principal -Tus pagos y factura-Tu historial
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Clic boton selecciona
    And Seleccionar Tu historial
    Then Validar mensaje de validacion de identidad
    And Ingresar el codigo de verificacion
    Then Validar mensaje de identidad confirmada con exito gradual
    Then Validar historial de facturas completo
    And Vaciar chat


  @EXUS_HOG_WSP_15
  Scenario: Menu principal -Tus pagos y factura- Mensajes de cobranza
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Clic boton selecciona
    And Seleccionar Mensaje de cobranza
    Then Validar mensaje casa cobranza completo
    And Vaciar chat


  @EXUS_HOG_WSP_16
  Scenario: Menu principal -Tus pagos y factura- Productos financiados
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Clic boton selecciona
    And Seleccionar Productos financiados y validar mensaje
    And Vaciar chat

  @EXUS_HOG_WSP_17
  Scenario: Menu principal -Tus pagos y factura- Programa tus pagos
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu de tus pagos y facturas
    And Validar informacion de la factura
    And Clic boton selecciona
    And Seleccionar Programa tus pagos
    And Validar Programar pagos hogar
    And Vaciar chat


  @EXUS_HOG_WSP_18
  Scenario: Menu principal -Soporte y servicio-Estado de servicios-television
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Seleccionar Soporte y servicio
    And Seleccionar Estado servicios y esperar revision hogar
    And Seleccionar el servicio fallas en tu en tu señal de TV
    Then Validar imagen de recomendaciones
    And Vaciar chat


  @EXUS_HOG_WSP_22
  Scenario: Menu principal -Todo sobre tu plan - Conoce/mejora tu plan - Cambia tu plan
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu todo sobre tu plan postpago
    And Validar opciones de Cambia tu plan hogar
    And Vaciar chat


  @EXUS_HOG_WSP_23
  Scenario: Menu principal - Todo sobre tu plan - Conoce/mejora tu plan - Servicios adicionales
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu todo sobre tu plan postpago
    And Validar opcion Servicios adicionales hogar
    Then Validar mensaje de validacion de identidad
    And Ingresar el codigo de verificacion
    Then Validar mensaje de identidad confirmada con exito
    And Salir conversacion
    And Vaciar chat


  @EXUS_HOG_WSP_24
  Scenario: Menu principal - Todo sobre tu plan - Conoce/mejora tu plan - T-Resuelve_0p1
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta hogar
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal hogar
    And Ingresar al menu todo sobre tu plan postpago
    And Validar T-Resuelve soporte tecnologico hogar
    And Vaciar chat
