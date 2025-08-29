Feature: Validar los flujos de WhatsApp para líneas Postpago

  @Whatsapp_Post_01 @WhatsApp_Post
  Scenario Outline: Validar menú principal postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo   |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Validar menu principal Post
    And Vaciar chat

    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@
   |Hola   |2840|


  @Whatsapp_Post_02  @WhatsApp_Post
  Scenario Outline: Todo sobre tu plan - Consulta tus consumos
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo   |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Consultar consumos del plan postpago
    And Vaciar chat

    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@
   |Hola   |2840|



  @Whatsapp_Post_03  @WhatsApp_Post
  Scenario Outline: Todo sobre tu plan -  Conoce o mejora tu plan
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo   |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Conoce o mejora tu plan postpago
    And Vaciar chat


    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@
   |Hola   |2840|



  @Whatsapp_Post_04  @WhatsApp_Post
  Scenario Outline: Todo sobre tu plan - tu lealtad merece mas - Claro musica
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo   |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar claro musica
    Then Validar direccionamiento claro musica
    And Vaciar chat


    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@
   |Hola   |2840|


  @Whatsapp_Post_05  @WhatsApp_Post
  Scenario Outline: Todo sobre tu plan - tu lealtad merece mas - Claro Video
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo   |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar claro video
    Then Validar direccionamiento claro video
    And Vaciar chat


    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@
   |Hola   |2840|


  @Whatsapp_Post_06  @WhatsApp_Post
  Scenario Outline: Todo sobre tu plan - tu lealtad merece mas - Claro Drive
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo   |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar la opcion claro Drive
    Then Validar claro Drive Postpago
    And Vaciar chat


    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@
   |Hola   |2840|



  @Whatsapp_Post_07  @WhatsApp_Post
  Scenario Outline: Todo sobre tu plan - tu lealtad merece mas - Claro Club
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo   |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar la opcion claro Club
    Then Validar claro Club
    And Vaciar chat


    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@
   |Hola   |2840|



  @Whatsapp_Post_08  @WhatsApp_Post
  Scenario Outline: Todo sobre tu plan - tu lealtad merece mas - Aniversario Claro
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo   |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar la opcion aniversario Claro
    Then Validar aniversario Claro
    And Vaciar chat


    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@
   |Hola   |2840|


  @Whatsapp_Post_09  @WhatsApp_Post
  Scenario Outline: Todo sobre tu plan - tu lealtad merece mas - sorpresa de cumpleaños
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo   |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar al menu todo sobre tu plan postpago
    And Ingresar tu lealtad merece mas postpago
    And Seleccionar la opcion sorpresa de cumpleanos
    Then Validar aniversario Claro
    And Vaciar chat


    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@
   |Hola   |2840|


  @Whatsapp_Post_10  @WhatsApp_Post
  Scenario Outline: Validar menu Compra por WhatsApp postpago
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresa al menu Compra por WhatsApp
    And Clic boton selecciona
    Then Validar el menu Compra por WhatsApp
    And Vaciar chat

    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@


  @Whatsapp_Post_11  @WhatsApp_Post
  Scenario Outline: Tus pagos y facturas - validar portal de pagos y transacción
    Given Ingresar a WhatsAPP
    When Validar Version de la App
    And Buscar el chat de Claro Colombia
    And Iniciar el chat con Claro Colombia
      | saludo   |
      | <saludo> |
    And Seleccionar linea de consulta postpago
      | numeroPost   |
      | <numeroPost> |
    And Validar politica de tratamientos de datos
    And Seleccionar menu principal Post
    And Ingresar a tus pagos y facturas


    Examples:
      | saludo | numeroPost |
      ##@externalData@./src/test/resources/data/Datos.xlsx@Post@
