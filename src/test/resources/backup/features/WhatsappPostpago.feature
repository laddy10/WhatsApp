Feature: Validar los flujos de WhatsApp para líneas Postpago

  @Whatsapp_Post_01 @WhatsApp_Post
  Scenario: Validar menú principal postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Validar menu principal Post
    And Vaciar chat



  @Whatsapp_Post_02 @WhatsApp_Post
  Scenario: Todo sobre tu plan - Consulta tus consumos
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Consultar consumos del plan postpago
    And Vaciar chat


  @Whatsapp_Post_03 @WhatsApp_Post
  Scenario: Todo sobre tu plan - Conoce o mejora tu plan
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Conoce o mejora tu plan postpago
    And Vaciar chat



  @Whatsapp_Post_04 @WhatsApp_Post
  Scenario: Todo sobre tu plan - tu lealtad merece mas - Claro Musica
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar claro musica
    Then Validar direccionamiento claro musica
    And Vaciar chat


  @Whatsapp_Post_05 @WhatsApp_Post
  Scenario: Todo sobre tu plan - tu lealtad merece mas - Claro Video
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar claro video
    Then Validar direccionamiento claro video
    And Vaciar chat


  @Whatsapp_Post_06 @WhatsApp_Post
  Scenario: Todo sobre tu plan - tu lealtad merece mas - Claro Drive
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar la opcion claro Drive
    Then Validar claro Drive Postpago
    And Vaciar chat



  @Whatsapp_Post_07 @WhatsApp_Post
  Scenario: Todo sobre tu plan - tu lealtad merece mas - Claro Club
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar la opcion claro Club
    Then Validar claro Club
    And Vaciar chat


  @Whatsapp_Post_08 @WhatsApp_Post
  Scenario: Todo sobre tu plan - tu lealtad merece mas - Aniversario Claro
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar la opcion aniversario Claro
    Then Validar aniversario Claro
    And Vaciar chat



  @Whatsapp_Post_09 @WhatsApp_Post
  Scenario: Todo sobre tu plan - tu lealtad merece mas - sorpresa de cumpleaños
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar la opcion sorpresa de cumpleanos
    Then Validar aniversario Claro
    And Vaciar chat


  @Whatsapp_Post_10 @WhatsApp_Post
  Scenario: Validar menu Compra por WhatsApp postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresa al menu Compra por WhatsApp
    And Clic boton selecciona
    Then Validar el menu Compra por WhatsApp
    And Vaciar chat



  @Whatsapp_Post_11 @WhatsApp_Post_PENDIENTE
  Scenario: Tus pagos y facturas - validar portal de pagos y transacción
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar a tus pagos y facturas


  @Whatsapp_Post_12 @WhatsApp_Post
  Scenario: Tus pagos y facturas - Tu factura
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu de tus pagos y facturas
    And Clic boton selecciona
    And Clic Tu factura
    And Ingresar el codigo que llega en el mensaje de texto
    And Abrir factura
    And Vaciar chat


  @Whatsapp_Post_13 @WhatsApp_Post
  Scenario: Tus pagos y facturas - Otras facturas Claro
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu de tus pagos y facturas
    And Clic boton selecciona
    And Seleccionar otras facturas claro
    And Vaciar chat


  @Whatsapp_Post_14 @WhatsApp_Post
  Scenario: Tus pagos y facturas - Tu historial
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu de tus pagos y facturas
    And Clic boton selecciona
    And Seleccionar Tu historial
    And Ingresar el codigo que llega en el mensaje de texto
    Then Validar historial de facturas completo
    And Vaciar chat


  @Whatsapp_Post_15 @WhatsApp_Post
  Scenario: Tus pagos y facturas - Mensaje de cobranza
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu de tus pagos y facturas
    And Clic boton selecciona
    And Seleccionar Mensaje de cobranza
    Then Validar mensaje casa cobranza completo
    And Vaciar chat


  @Whatsapp_Post_16 @WhatsApp_Post
  Scenario: Tus pagos y facturas - Productos financiados
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu de tus pagos y facturas
    And Clic boton selecciona
    And Seleccionar Productos financiados y validar mensaje
    And Vaciar chat


  @Whatsapp_Post_17 @WhatsApp_Post
  Scenario: Tus pagos y facturas - Programa tus pagos
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu de tus pagos y facturas
    And Clic boton selecciona
    And Seleccionar Programa tus pagos
    And Validar direccionamiento Programar pagos
    And Vaciar chat

  @Whatsapp_Post_18 @WhatsApp_Post
  Scenario: Soporte y servicio - Estado servicios - Fallas en tu navegacion Datos
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Seleccionar Soporte y servicio
    And Ingresar al boton selecciona de soporte
    And Estado de servicios
    And Seleccionar No tengo servicio
    Then Validar fallas en tu navegacion Datos
    And Vaciar chat


  @Whatsapp_Post_19 @WhatsApp_Post
  Scenario: Soporte y servicio - Estado servicios - Fallas llamadas
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Seleccionar Soporte y servicio
    And Ingresar al boton selecciona de soporte
    And Estado de servicios
    And Seleccionar No tengo servicio
    Then Validar fallas en hacer o recibir llamadas
    And Vaciar chat



  @Whatsapp_Post_20 @WhatsApp_Post
  Scenario: Soporte y servicio - Estado servicios - Fallas Otros servicios
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Seleccionar Soporte y servicio
    And Ingresar al boton selecciona de soporte
    And Estado de servicios
    And Seleccionar No tengo servicio
    Then Validar fallas otros servicios
    And Vaciar chat


  @Whatsapp_Post_21 @WhatsApp_Post
  Scenario: Soporte y servicio - Reporte robo o perdida
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Seleccionar Soporte y servicio
    And Ingresar al boton selecciona de soporte
    Then Validar permita seleccionar opción Reporte robo o perdida
    And Vaciar chat



  @Whatsapp_Post_22 @WhatsApp_Post
  Scenario: Soporte y servicio - Tus PQR Radicados
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Seleccionar Soporte y servicio
    And Ingresar al boton selecciona de soporte
    Then Validar PQR radicados y direccionamiento pagina Claro
    And Vaciar chat


  @Whatsapp_Post_23 @WhatsApp_Post
  Scenario: Soporte y servicio - Tus equipos en soporte
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Seleccionar Soporte y servicio
    And Ingresar al boton selecciona de soporte
    Then Validar tus equipos en soporte y direccionamiento pagina Claro
    And Vaciar chat


  @Whatsapp_Post_24 @WhatsApp_Post
  Scenario: Otras opciones - Registra tu equipo
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
    And Seleccionar linea de consulta postpago
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Seleccionar otras opciones
    And Clic boton selecciona
    And Seleccionar registra tu equipo
    Then Realizar el regitro del equipo
    And Realizar el proceso de regsitro
    And Vaciar chat


