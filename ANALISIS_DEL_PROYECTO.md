# 📱 Análisis Completo del Proyecto Auto_WhatsApp

> **Documento de análisis técnico** — Escrito en lenguaje coloquial para que cualquier persona lo entienda, no solo los devs.

---

## 🤔 ¿Qué hace este proyecto en palabras simples?

Imagina que tienes que verificar manualmente, todos los días, que el **chatbot de WhatsApp de Claro Colombia** funciona bien. Tendrías que abrir WhatsApp en un celular, buscar el chat de Claro, escribirle, navegar por todos los menús, comprobar que cada opción responde correctamente, y hacerlo para clientes prepago, postpago y hogar. Eso es un trabajo enorme y aburrido que se repetiría constantemente.

**Este proyecto automatiza exactamente eso.** En lugar de hacerlo un humano, un robot lo hace. El robot abre WhatsApp en un celular Android real (o virtual), interactúa con el bot de Claro como si fuera un cliente, verifica que todo funcione bien, toma capturas de pantalla como evidencia, y genera un reporte en Word.

**En resumen:** Es un **robot de pruebas automáticas** que verifica que el asistente virtual de Claro en WhatsApp funcione correctamente para los tres tipos de clientes que tienen: prepago, postpago y hogar.

---

## 🏗️ ¿Cómo está organizado el proyecto?

El proyecto sigue la estructura estándar de un proyecto Java con Gradle, y está dividido en dos grandes áreas:

```
Auto_WhatsApp/
├── src/main/java/          ← El "cerebro" del robot (herramientas y lógica)
└── src/test/               ← Las "instrucciones" de qué probar y cómo
```

### La parte principal (`src/main/java/`)

Aquí vive todo el código que hace que el robot sepa cómo moverse por WhatsApp. Está organizado en 8 paquetes:

| Paquete | ¿Qué hace? |
|---|---|
| `tasks/` | Las **acciones** que el robot ejecuta (abrir WhatsApp, buscar el chat, hacer click, etc.) |
| `userinterfaces/` | El **mapa de pantallas** — le dice al robot dónde está cada botón en WhatsApp |
| `utils/` | **Herramientas** de apoyo (leer Excel, tomar fotos, generar reportes, conectarse a Appium) |
| `interactions/` | **Movimientos básicos** reutilizables (hacer click, hacer scroll, esperar texto, validar) |
| `models/` | **Objetos de datos** que representan información (usuario, métricas, estado de la conversación) |
| `questions/` | **Preguntas** que el robot se hace sobre la pantalla ("¿existe este texto?") |
| `hooks/` | **Eventos del ciclo de vida** — qué pasa antes/después de cada prueba |
| `exceptions/` | **Errores personalizados** (actualmente vacío, reservado para futuro) |

### La parte de pruebas (`src/test/`)

Aquí están las instrucciones de qué probar. Tiene dos partes:
- **`features/`** — Los guiones en lenguaje humano (Cucumber/Gherkin)
- **`stepDefinitions/`** — El código que convierte esos guiones en acciones reales

---

## 🎭 ¿Qué pruebas hace exactamente?

El robot tiene **3 grandes suites de pruebas**, una por cada tipo de cliente de Claro:

### 📱 Suite Prepago (`Whatsapp.feature`) — 25 escenarios

Prueba todos los menús disponibles para clientes con línea prepago:

- ✅ Validar el menú principal
- ✅ Consultar el saldo de la línea
- ✅ Consultar consumos de datos, voz, apps
- ✅ Conocer o mejorar el plan actual
- ✅ "Tu lealtad merece más" → Claro Música y Claro Drive
- ✅ Haz tus recargas (con validación de medios de pago: Nequi, PSE, tarjeta)
- ✅ Compra tus paquetes (todo incluido, voz, datos, apps, internacionales, comunidad sorda, regala un paquete)
- ✅ Compra por WhatsApp
- ✅ Otras opciones (cámbiate a postpago, servicios LDI, renueva tu SIM, PQRS radicados)
- ✅ Tus equipos (menú de equipos, puntos físicos)
- ✅ Consultar otra cuenta y consultar otra línea

### 🗂️ Suite Postpago (`WhatsappPostpago.feature`) — 20+ escenarios

Prueba los flujos específicos para clientes con plan postpago:

- ✅ Menú principal postpago
- ✅ Todo sobre tu plan (consumos, conoce/mejora tu plan, lealtad)
- ✅ Tu factura (consultar, descargar, validación de identidad con código de seguridad, pago)
- ✅ Compra tus paquetes postpago
- ✅ Haz tus recargas postpago
- ✅ Otras opciones postpago (cámbiate a postpago, PQRS, Roaming Internacional)
- ✅ Tus equipos postpago (reporte de robo/pérdida, equipos asociados)

### 🏠 Suite Hogar (`WhatsappHogar.feature`) — 20+ escenarios

Prueba los flujos para clientes con servicios de internet/TV en casa:

- ✅ Consulta tu factura hogar (con verificación de identidad por PIN)
- ✅ Soporte hogar: fallas en navegación, TV, telefonía
- ✅ Reiniciar módem
- ✅ Verificación de identidad completa (código PIN por SMS)
- ✅ Pago de facturas (genera link de pago)
- ✅ Menú principal hogar (internet para tu hogar, plan para tu celular)

**En total, el proyecto tiene aproximadamente 65-70 escenarios de prueba.**

---

## 🔧 ¿Qué tecnologías usa y para qué?

| Tecnología | Versión | Para qué sirve |
|---|---|---|
| **Java 11** | 11 | Lenguaje de programación del robot |
| **Appium** | 7.3.0 | Controla el celular Android (como si fuera una mano virtual) |
| **Selenium** | 3.141.59 | Base de la automatización web/móvil |
| **Serenity BDD** | 2.0.71 | Framework de pruebas + generación de reportes bonitos HTML |
| **Cucumber** | 1.9.51 | Permite escribir los escenarios en lenguaje humano |
| **UiAutomator2** | — | El motor que Appium usa para controlar la interfaz de Android |
| **Gradle** | — | El sistema de construcción del proyecto |
| **Apache POI** | 5.2.3 | Leer archivos Excel y generar reportes Word |
| **OkHttp + Gson** | 4.12 / 2.10 | Para conectarse con la IA Ollama |
| **Ollama (IA local)** | — | Inteligencia artificial local para analizar fallos automáticamente |
| **Log4j** | 1.2.17 | Sistema de logging/bitácora |

---

## 🤖 La Parte Más Interesante: La IA Integrada

Una de las cosas más avanzadas de este proyecto es que **tiene integrada una IA local (Ollama)** para analizar cuando algo falla. Funciona así:

1. El robot ejecuta una prueba
2. Si algún paso falla, en vez de simplemente decir "falló", el sistema:
   - Captura el estado actual de la pantalla del celular (el "page source")
   - Identifica el tipo de error (elemento no encontrado, timeout, etc.)
   - Envía toda esa información a Ollama (una IA que corre en la misma máquina)
3. Ollama analiza el problema como un **experto QA senior** y devuelve un informe con:
   - Resumen ejecutivo del problema
   - Causa raíz probable
   - Alternativas detectadas en la pantalla
   - Solución recomendada
   - Acción preventiva para evitar que vuelva a pasar

Este análisis aparece embebido directamente en el reporte de Serenity, así que el equipo de QA tiene, en el mismo reporte, no solo qué falló sino **por qué falló y cómo arreglarlo**.

> 💡 Este es un nivel de automatización muy sofisticado — combinar testing automático con análisis de fallos por IA es una práctica de vanguardia en QA.

---

## 📊 Sistema de Reportes: Triple Cobertura

El proyecto genera **tres tipos de reportes** al terminar:

### 1. Reporte HTML de Serenity
El reporte más completo. Serenity genera automáticamente un sitio web con:
- Estado de cada prueba (pasó/falló/pendiente)
- Capturas de pantalla de cada paso
- Análisis de IA cuando hay fallos
- Se guarda en `target/site/serenity/`

### 2. Reporte Word (.docx)
El sistema genera un documento Word por cada prueba ejecutada, con:
- Nombre del escenario, fecha y hora, número de línea probada, duración
- Capturas de pantalla embebidas en el documento
- Se guarda en la carpeta `reportes/`

### 3. Métricas JSON
El sistema también exporta métricas de desempeño en formato JSON:
- Nombre del escenario y del paso/flujo
- Tiempo exacto de ejecución
- Estado (SUCCESS/FAILURE) y dispositivo usado
- Se guarda en `target/tiempos_respuesta.json`

---

## 🔑 ¿Cómo sabe el robot con qué número conectarse?

El robot necesita un número de celular Claro real para hacer las pruebas. La configuración del usuario está en:

**`src/test/resources/config/real-user.json`**
```json
{
  "saludo": "Hola",
  "numeroPre": "3558",    <- Últimos 4 dígitos de la línea prepago
  "valor": "$ 50.000",    <- Valor de recarga para pruebas
  "numeroPost": "9612",   <- Últimos 4 dígitos de la línea postpago
  "numeroHogar": "2961"   <- Últimos 4 dígitos de la cuenta hogar
}
```

> Solo guarda los últimos 4 dígitos de cada número por seguridad. El bot de Claro permite identificar la línea con solo esos 4 dígitos cuando el celular ya tiene la SIM insertada.

---

## 🧠 La Lógica Inteligente del Robot

### Manejo de Estados del Bot

El robot entiende en qué estado está la conversación con el bot de Claro. Cuando envía el saludo "Hola", el bot puede responder de varias formas y el robot sabe qué hacer:

| Estado Detectado | ¿Qué hace el robot? |
|---|---|
| 🟢 **Flujo Normal** | Sigue con el script de prueba normalmente |
| 🟡 **Pantalla Inicial** | El bot no reconoció el número → reinicia el chat y vuelve a intentar |
| 🔴 **Error conversacional** | El bot dice "no entendí" → limpia el chat y reintenta |
| 🧑‍💼 **Esperando Asesor** | El bot escaló a un humano → maneja esta situación especialmente |

### Reintentos Automáticos
El robot tiene una lógica de hasta **2 reintentos** automáticos antes de declarar que algo falló. Esto es muy importante porque los bots a veces tienen demoras o respuestas inconsistentes.

### Manejo de PIN por SMS
Para las pruebas de Hogar, cuando el bot pide verificación de identidad, el robot puede:
1. Abrir el panel de notificaciones del Android
2. Leer el SMS con el código de seguridad
3. Extraer el número del código con expresiones regulares
4. Escribir el código dígito por dígito usando teclas de hardware del Android

---

## 📁 Archivos Importantes en la Raíz

| Archivo/Carpeta | ¿Qué es? |
|---|---|
| `serenity.properties` | Configuración principal: URL de Appium, app, dispositivo, timeouts |
| `build.gradle` | Receta de construcción del proyecto + todas las librerías usadas |
| `Capturas/` | Carpeta donde se guardan temporalmente las fotos de cada paso |
| `reportes/` | Aquí aparecen los reportes Word generados |
| `ruta/` | Contiene la plantilla Word (`PlantillaInforme.docx`) para los reportes |
| `history/` | Historial de ejecuciones anteriores |
| `Error/` | Capturas de pantallas guardadas en caso de error |
| `MiClaro.log` | Bitácora de ejecuciones (~40MB en total entre todos los archivos de log) |
| `docs/` | Documentación técnica adicional |

---

## ⚙️ ¿Cómo se ejecuta?

El proyecto se conecta a un servidor **Appium** que debe estar corriendo localmente en `http://127.0.0.1:4723/wd/hub`. Appium es el intermediario entre el código Java y el celular Android.

Los pasos para ejecutarlo:
1. Conectar el celular Android con WhatsApp instalado (por USB)
2. Iniciar el servidor Appium
3. Ejecutar `gradle test` (o con tags específicos como `--tags @Whatsapp_01`)

### Ejecución por Tags
Cada escenario tiene una etiqueta (`@Whatsapp_01`, `@Whatsapp_Post_01`, `@EXUS_HOG_WSP_01`), lo que permite ejecutar pruebas específicas sin correr todo el suite.

---

## 🏛️ Arquitectura: Patrón Screenplay

El proyecto usa el **Patrón Screenplay** de Serenity BDD, que es la forma más moderna de escribir pruebas automáticas. Es como una obra de teatro:

- **Actor** = El robot que ejecuta las acciones (representa al usuario)
- **Task** = Una tarea compuesta de varios pasos (ej: "Iniciar chat con Claro")
- **Interaction** = Un paso básico e indivisible (ej: "Hacer click en este botón")
- **Question** = Una pregunta sobre el estado de la pantalla (ej: "¿Existe este texto?")

Esta arquitectura hace que el código sea muy fácil de mantener. Si cambia un menú del bot de Claro, solo hay que actualizar el archivo correspondiente, sin tocar nada más.

```
Feature (Gherkin) → StepDefinitions → Tasks → Interactions → UI Page Objects
```

---

## 🔍 Mapas de Pantalla (Page Objects)

El robot "conoce" la interfaz de WhatsApp gracias a tres archivos que mapean todos los elementos:

- **`WhatsAppPage.java`** (~12KB) — Todos los elementos de WhatsApp para flujos prepago
- **`WhatsAppPostpagoPage.java`** (~14KB) — Elementos específicos del flujo postpago
- **`WhatsAppHogarPage.java`** (~1.3KB) — Elementos del flujo hogar

Cada elemento está definido con su localizador de Android (por texto, ID, o UiAutomator), lo que le dice al robot exactamente cómo encontrar cada botón en la pantalla.

---

## 🚦 Fortalezas del Proyecto

✅ **Cobertura amplia** — Cubre prácticamente todos los flujos del chatbot de Claro para tres tipos de clientes  
✅ **IA integrada** — Análisis automático de fallos con Ollama es innovador y ahorra tiempo de debugging  
✅ **Reportes triples** — HTML + Word + JSON satisface necesidades técnicas y gerenciales  
✅ **Arquitectura limpia** — Patrón Screenplay facilita mantenimiento y escalabilidad  
✅ **Manejo de estados** — El robot es inteligente para manejar respuestas inesperadas del bot  
✅ **Reintentos automáticos** — Reduce falsos positivos por inestabilidad del chatbot  
✅ **Capturas por paso** — Evidencia visual completa de cada ejecución  
✅ **Métricas de desempeño** — Tracking automático de tiempos de respuesta del bot  

---

## ⚠️ Áreas de Mejora Identificadas

🔶 **Dependencias desactualizadas** — Serenity 2.0.71 y Cucumber 1.9.51 son versiones muy antiguas. Las versiones actuales están en 4.x y 7.x respectivamente.

🔶 **APIs deprecadas** — Se usan métodos como `findElementByAndroidUIAutomator()` que están marcados como obsoletos en versiones nuevas de Appium Java Client.

🔶 **Credenciales en código** — La contraseña de la cuenta hogar está directamente en `Constantes.java` como texto plano. Debería estar en variables de entorno o un vault seguro.

🔶 **Log4j versión antigua** — Se usa Log4j 1.2.17, una versión que tiene múltiples vulnerabilidades de seguridad conocidas. Debería actualizarse a Log4j2.

🔶 **Paquete `exceptions/` vacío** — Está creado pero sin contenido.

🔶 **README.md vacío** — El archivo README.md del proyecto tiene solo 11 bytes. Un README con instrucciones sería muy valioso para nuevos integrantes.

🔶 **Logs muy grandes** — Los archivos de log ocupan ~40MB en total. Falta una política de limpieza/rotación más agresiva.

---

## 📈 Resumen Ejecutivo

| Aspecto | Detalle |
|---|---|
| **Tipo de proyecto** | Framework de automatización de pruebas móviles |
| **Aplicación bajo prueba** | Chatbot de WhatsApp de Claro Colombia |
| **Dispositivo objetivo** | Android real o emulado |
| **Lenguaje** | Java 11 |
| **Patrones usados** | Screenplay Pattern, BDD, Page Object Model |
| **Total de escenarios** | ~65-70 escenarios de prueba |
| **Suites** | Prepago (25), Postpago (20+), Hogar (20+) |
| **Madurez del proyecto** | ⭐⭐⭐⭐ Alto — proyecto completo y funcional |
| **Innovación** | ⭐⭐⭐⭐⭐ Muy alta — IA integrada para análisis de fallos |
| **Mantenibilidad** | ⭐⭐⭐⭐ Alta — buena arquitectura, dependencias un poco viejas |

---

*Documento generado el 3 de julio de 2026 | Análisis realizado sobre el código fuente del proyecto Auto_WhatsApp*
