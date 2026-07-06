# Auto_WhatsApp - Reglas de Asistencia (QA Workflow)

## Revisión de Alertas y Pruebas Fallidas

Cuando se te solicite revisar fallas, validar el dashboard de pruebas o analizar alertas, DEBES seguir estrictamente este flujo de trabajo:

1. **Revisar el Dashboard:**
   - Selecciona el cliente **Claro**.
   - Haz scroll hacia arriba en la página (si es necesario) y entra a **Continuous Testing**.
   - Navega a la pestaña de **Alertas** y filtra por el proyecto **WhatsApp**.
   - Valida las alertas y, para ver el reporte detallado, ingresa a **Ver Reporte** y navega en el reporte.
   - Identifica cuáles pruebas fallaron y captura el **Tag exacto** (ej. `@Whatsapp_Post_22`). **IMPORTANTE:** Presta especial atención a las mayúsculas y minúsculas. El tag válido debe tener la misma capitalización que existe en los archivos `.feature`.

2. **Ejecución de Prueba Local:**
   - Una vez identificado el Tag correcto, advierte al usuario que vas a ejecutar la prueba localmente para validar la falla.
   - Usa la herramienta para lanzar comandos en consola y ejecuta el siguiente script asegurándote de usar `cmd /c` para evitar problemas con PowerShell:
     `cmd /c 'gradle clean test -Dcucumber.options="--tags @ElTagEncontrado"'`
   - Asegúrate de que el comando se ejecute en la raíz del proyecto.

3. **Diagnóstico y Reporte:**
   - Espera a que termine la ejecución del comando.
   - Si la prueba local es **Exitosa (BUILD SUCCESSFUL)**: Informa al usuario que es una **falsa alerta** ocasionada por una desconexión remota (como "socket hang up") en la granja de dispositivos, confirmando que no hay cambios de texto en el bot.
   - Si la prueba local **Falla (BUILD FAILED)**: Informa al usuario que es una **alerta real**. Analiza la traza del error e inicia el proceso de revisión de código para actualizar el texto o el elemento visual que falló.
