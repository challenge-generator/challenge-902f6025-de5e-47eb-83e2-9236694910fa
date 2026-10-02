# Optimización de rendimiento con Redis y Memcached en un sistema de alta concurrencia

En un entorno de banca digital, el sistema de préstamos debe manejar altas tasas de solicitudes de préstamos en tiempo real. El equipo de backend ha identificado que la latencia en la recuperación de datos está afectando la experiencia del usuario. Tu misión es implementar y optimizar el uso de caché utilizando Redis y Memcached para mejorar el rendimiento del sistema.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | optimización de desempeño con caché: redis y memcached |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 15 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Implementación de caché básica

**Objetivo:** Configurar y utilizar Redis para almacenar en caché los datos de préstamos más solicitados.

**Tiempo estimado:** 5 horas

**Instrucciones:**

- Identifica los datos de préstamos que se consultan con más frecuencia.
- Configura Redis para almacenar estos datos en caché.
- Implementa la lógica para recuperar los datos de la caché antes de consultar la base de datos.

**Entregable:** Configuración de Redis y lógica de caché implementada para los datos de préstamos más solicitados.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la latencia de recuperación de datos y el impacto en la experiencia del usuario.
- Evalúa la consistencia de los datos almacenados en caché.

</details>

### Fase 2: Estrategias de invalidación de caché

**Objetivo:** Implementar estrategias de invalidación de caché para asegurar la consistencia de los datos.

**Tiempo estimado:** 5 horas

**Instrucciones:**

- Investiga y selecciona una estrategia de invalidación de caché (cache-aside, write-through, write-behind).
- Implementa la estrategia seleccionada en el sistema.
- Verifica que la estrategia de invalidación mantenga la consistencia de los datos.

**Entregable:** Estrategia de invalidación de caché implementada y verificada.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los pros y contras de cada estrategia de invalidación.
- Evalúa el impacto de la invalidación en la latencia y la consistencia de los datos.

</details>

### Fase 3: Optimización de rendimiento con Memcached

**Objetivo:** Configurar y utilizar Memcached para optimizar aún más el rendimiento del sistema.

**Tiempo estimado:** 5 horas

**Instrucciones:**

- Configura Memcached para almacenar en caché los datos de préstamos.
- Implementa la lógica para recuperar los datos de Memcached antes de consultar Redis o la base de datos.
- Compara el rendimiento del sistema con y sin Memcached.

**Entregable:** Configuración de Memcached y lógica de caché implementada para los datos de préstamos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la latencia de recuperación de datos y el impacto en el rendimiento del sistema.
- Evalúa la consistencia de los datos almacenados en caché.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es una estrategia de invalidación de caché y por qué es importante en este contexto?
- **paraQueSirve**: ¿Para qué sirve la implementación de caché en un sistema de alta concurrencia?
- **comoSeUsa**: ¿Cómo se utiliza Redis y Memcached en este reto para mejorar el rendimiento del sistema?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar una estrategia de invalidación de caché y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica la selección de una estrategia de invalidación de caché y cómo afecta al rendimiento del sistema?

## Criterios de Evaluacion

- Implementación correcta de Redis para almacenar en caché los datos de préstamos más solicitados.
- Selección e implementación de una estrategia de invalidación de caché.
- Configuración y utilización de Memcached para optimizar el rendimiento del sistema.
- Comparación del rendimiento del sistema con y sin Memcached.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
