# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/bank/loans/LoansApplication.java` — `com.bank.loans.infrastructure.config.CacheProperties`: El import com.bank.loans.infrastructure.config.CacheProperties usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/bank/loans/LoansApplication.java` — `com.bank.loans.application.port.out.CachePort`: El import com.bank.loans.application.port.out.CachePort usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/bank/loans/LoansApplication.java` — `com.bank.loans.application.service.LoanService`: El import com.bank.loans.application.service.LoanService usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- `src/main/java/com/bank/loans/LoansApplication.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/application/usecase/GetLoanUseCase.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapter.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.core.JsonProcessingException pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapter.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.core.JsonProcessingException pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapter.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/infrastructure/config/RedisConfig.java` — `com.fasterxml.jackson`: El import com.fasterxml.jackson.annotation.JsonTypeInfo pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/infrastructure/config/RedisConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/infrastructure/cache/CacheManager.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/bank/loans/LoansApplication.java` — `CacheManager.initializeCacheStrategies`: Se invoca `initializeCacheStrategies` sobre `CacheManager`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getId`: Se invoca `getId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getCustomerId`: Se invoca `getCustomerId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getAmount`: Se invoca `getAmount` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getInterestRate`: Se invoca `getInterestRate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getTermMonths`: Se invoca `getTermMonths` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getStatus`: Se invoca `getStatus` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getApprovedAt`: Se invoca `getApprovedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getCreatedAt`: Se invoca `getCreatedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getUpdatedAt`: Se invoca `getUpdatedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setId`: Se invoca `setId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setCustomerId`: Se invoca `setCustomerId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.customerId`: Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setAmount`: Se invoca `setAmount` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.amount`: Se invoca `amount` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setInterestRate`: Se invoca `setInterestRate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.interestRate`: Se invoca `interestRate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setTermMonths`: Se invoca `setTermMonths` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.termMonths`: Se invoca `termMonths` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setStatus`: Se invoca `setStatus` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.status`: Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setApprovedAt`: Se invoca `setApprovedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.approvedAt`: Se invoca `approvedAt` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setCreatedAt`: Se invoca `setCreatedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.createdAt`: Se invoca `createdAt` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setUpdatedAt`: Se invoca `setUpdatedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.updatedAt`: Se invoca `updatedAt` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/application/usecase/GetLoanUseCase.java` — `Loan.isPresent`: Se invoca `isPresent` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/application/usecase/GetLoanUseCase.java` — `Loan.get`: Se invoca `get` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/application/usecase/GetLoanUseCase.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `MemcachedProperties.setHost`: Se invoca `setHost` sobre `MemcachedProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `MemcachedProperties.setPort`: Se invoca `setPort` sobre `MemcachedProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `MemcachedProperties.setExpiration`: Se invoca `setExpiration` sobre `MemcachedProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `MemcachedProperties.setOpTimeout`: Se invoca `setOpTimeout` sobre `MemcachedProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `MemcachedProperties.setConnectionTimeout`: Se invoca `setConnectionTimeout` sobre `MemcachedProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `GetLoanUseCase.findById`: Se invoca `findById` sobre `GetLoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `GetLoanUseCase.findByCustomerId`: Se invoca `findByCustomerId` sobre `GetLoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.customerId`: Se invoca `customerId` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `LoanValidator.validateCreateRequest`: Se invoca `validateCreateRequest` sobre `LoanValidator`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `GetLoanUseCase.createLoan`: Se invoca `createLoan` sobre `GetLoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.principal`: Se invoca `principal` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.interestRate`: Se invoca `interestRate` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.termMonths`: Se invoca `termMonths` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.purpose`: Se invoca `purpose` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `GetLoanUseCase.updateLoan`: Se invoca `updateLoan` sobre `GetLoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.status`: Se invoca `status` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `GetLoanUseCase.deleteById`: Se invoca `deleteById` sobre `GetLoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.customerId`: Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.principal`: Se invoca `principal` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.interestRate`: Se invoca `interestRate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.termMonths`: Se invoca `termMonths` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.status`: Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.purpose`: Se invoca `purpose` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.createdAt`: Se invoca `createdAt` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.updatedAt`: Se invoca `updatedAt` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.customerId`: Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.amount`: Se invoca `amount` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.termMonths`: Se invoca `termMonths` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.interestRate`: Se invoca `interestRate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.status`: Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.issueDate`: Se invoca `issueDate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.dueDate`: Se invoca `dueDate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.monthlyIncome`: Se invoca `monthlyIncome` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapterTest.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapterTest.java` — `Loan.customerId`: Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapterTest.java` — `Loan.id`: Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapterTest.java` — `Loan.customerId`: Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `pom.xml` — `com.google.code.simple-spring-memcached:spring-cache@4.2.0`: com.google.code.simple-spring-memcached:spring-cache declara la version 4.2.0, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.
- `pom.xml` — `com.google.code.simple-spring-memcached:spymemcached@2.12.3`: com.google.code.simple-spring-memcached:spymemcached declara la version 2.12.3, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Backend, Especialidad Desarrollador, Tecnología Java, Senior

### Brecha de conocimiento
Implementa prácticas de caché utilizando al menos una herramienta como Redis o Memcached. Domina estrategias de invalidación, patrones de caché (cache-aside, write-through, write-behind), y optimización de rendimiento en aplicaciones distribuidas.

### Misión / candidato
Candidato con experiencia senior en backend, trabajando en equipos que requieren optimización de performance en sistemas de alta concurrencia.

### Reto
- Tema: optimización de desempeño con caché: redis y memcached
- Seniority: senior-l2
- Tipo: practical
- Título: Optimización de rendimiento con Redis y Memcached en un sistema de alta concurrencia
- Tiempo estimado: 15 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Implementación de caché básica — objetivo: Configurar y utilizar Redis para almacenar en caché los datos de préstamos más solicitados. — entregable (NO resolver): Configuración de Redis y lógica de caché implementada para los datos de préstamos más solicitados.
- Fase 2: Estrategias de invalidación de caché — objetivo: Implementar estrategias de invalidación de caché para asegurar la consistencia de los datos. — entregable (NO resolver): Estrategia de invalidación de caché implementada y verificada.
- Fase 3: Optimización de rendimiento con Memcached — objetivo: Configurar y utilizar Memcached para optimizar aún más el rendimiento del sistema. — entregable (NO resolver): Configuración de Memcached y lógica de caché implementada para los datos de préstamos.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.bank</groupId>
    <artifactId>loans</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>loans</name>
    <description>Sistema de préstamos con optimización de caché</description>

    <properties>
        <java.version>21</java.version>
        <spring-cloud.version>2023.0.3</spring-cloud.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>com.google.code.simple-spring-memcached</groupId>
            <artifactId>spring-cache</artifactId>
            <version>4.2.0</version>
        </dependency>
        <dependency>
            <groupId>com.google.code.simple-spring-memcached</groupId>
            <artifactId>spymemcached</artifactId>
            <version>2.12.3</version>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/bank/loans/LoansApplication.java ===
package com.bank.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import com.bank.loans.infrastructure.config.CacheProperties;
import com.bank.loans.application.port.out.CachePort;
import com.bank.loans.application.service.LoanService;
import com.bank.loans.infrastructure.adapter.RedisCacheAdapter;
import com.bank.loans.infrastructure.adapter.MemcachedCacheAdapter;
import com.bank.loans.infrastructure.cache.CacheManager;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties(CacheProperties.class)
public class LoansApplication {
    private static final Logger logger = LoggerFactory.getLogger(LoansApplication.class);
    private final CacheProperties cacheProperties;
    private final CachePort redisCachePort;
    private final CachePort memcachedCachePort;
    private final CacheManager cacheManager;
    private final LoanService loanService;

    public LoansApplication(CacheProperties cacheProperties,
                           RedisCacheAdapter redisCacheAdapter,
                           MemcachedCacheAdapter memcachedCacheAdapter,
                           CacheManager cacheManager,
                           LoanService loanService) {
        this.cacheProperties = cacheProperties;
        this.redisCachePort = redisCacheAdapter;
        this.memcachedCachePort = memcachedCacheAdapter;
        this.cacheManager = cacheManager;
        this.loanService = loanService;
    }

    @PostConstruct
    public void init() {
        logger.info("Initializing cache configurations...");
        cacheManager.initializeCacheStrategies(
            List.of(redisCachePort, memcachedCachePort),
            cacheProperties.getDefaultTtlSeconds()
        );
        logger.info("Cache strategies initialized with Redis and Memcached");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        try {
            var context = SpringApplication.run(LoansApplication.class, args);
            logger.info("Application started successfully");

            // Verificación de conectividad con caches
            var redisCache = context.getBean(RedisCacheAdapter.class);
            var memcachedCache = context.getBean(MemcachedCacheAdapter.class);

            if (!redisCache.isConnected()) {
                logger.warn("Redis cache connection failed - falling back to Memcached");
            }

            if (!memcachedCache.isConnected()) {
                logger.warn("Memcached connection failed - performance may be degraded");
            }

            // Validación de propiedades de caché
            if (cacheProperties.getDefaultTtlSeconds() <= 0) {
                throw new IllegalStateException("Cache TTL must be positive");
            }

            logger.info("Cache configuration validated successfully");
        } catch (Exception e) {
            logger.error("Application failed to start", e);
            System.exit(1);
        }
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: loans-service
  datasource:
    url: jdbc:postgresql://localhost:5432/loans_db
    username: loans_user
    password: loans_password
    driver-class-name: org.postgresql.Driver
    hikari:
      connection-timeout: 20000
      maximum-pool-size: 10
      minimum-idle: 2
      idle-timeout: 30000
      pool-name: LoansHikariPool
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        format_sql: true
        show_sql: false
        use_sql_comments: true
        jdbc:
          batch_size: 20
          order_inserts: true
          order_updates: true
  cache:
    type: simple
    cache-names: loans, loanDetails
    redis:
      time-to-live: 300000
      enable-statistics: true
  redis:
    host: localhost
    port: 6379
    timeout: 5000ms
    lettuce:
      pool:
        max-active: 16
        max-idle: 8
        min-idle: 2
        max-wait: 1000ms

memcached:
  servers: localhost:11211
  protocol: binary
  op-timeout: 5000
  connection-factory-class: net.spy.memcached.DefaultConnectionFactory
  cache-ttl-seconds: 300
  default-cache-strategy: fallback

cache:
  default-ttl-seconds: 300
  strategies:
    - name: redis-primary
      primary: true
      fallback: memcached
    - name: memcached-fallback
      primary: false
      fallback: none

logging:
  level:
    root: INFO
    com.bank.loans: DEBUG
    org.springframework.web: INFO
    org.hibernate.SQL: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"
  file:
    name: logs/loans-service.log
    max-size: 10MB
    max-history: 7

server:
  port: 8080
  servlet:
    context-path: /api/loans
  tomcat:
    threads:
      max: 200
      min-spare: 10
    connection-timeout: 20000ms
    max-connections: 8192
    accept-count: 100

// === ARCHIVO: src/main/java/com/bank/loans/domain/model/Loan.java ===
package com.bank.loans.domain.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record Loan(
    @NotNull(message = "Loan ID cannot be null")
    UUID id,

    @NotNull(message = "Customer ID cannot be null")
    UUID customerId,

    @NotNull(message = "Loan amount cannot be null")
    @Positive(message = "Loan amount must be positive")
    BigDecimal amount,

    @NotNull(message = "Interest rate cannot be null")
    @PositiveOrZero(message = "Interest rate must be zero or positive")
    BigDecimal interestRate,

    @NotNull(message = "Start date cannot be null")
    LocalDate startDate,

    @NotNull(message = "End date cannot be null")
    LocalDate endDate,

    @NotNull(message = "Status cannot be null")
    LoanStatus status,

    @NotNull(message = "Creation date cannot be null")
    LocalDate createdAt,

    LocalDate updatedAt
) {
    public enum LoanStatus {
        ACTIVE, PAID, OVERDUE, REJECTED
    }

    public Loan {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (createdAt == null) {
            createdAt = LocalDate.now();
        }
    }

    public Loan withUpdatedAt(LocalDate updatedAt) {
        return new Loan(id, customerId, amount, interestRate, startDate, endDate, status, createdAt, updatedAt);
    }
}

// === ARCHIVO: src/main/java/com/bank/loans/domain/port/LoanRepositoryPort.java ===
package com.bank.loans.domain.port;

import com.bank.loans.domain.model.Loan;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepositoryPort {
    Optional<Loan> findById(UUID id);
    Loan save(Loan loan);
    void deleteById(UUID id);
    Optional<Loan> findByCustomerIdAndId(UUID customerId, UUID loanId);
}

// === ARCHIVO: src/main/java/com/bank/loans/domain/port/CachePort.java ===
package com.bank.loans.domain.port;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface CachePort {
    <T> Optional<T> get(String key, Class<T> type);
    void put(String key, Object value, long timeout, TimeUnit timeUnit);
    void evict(String key);
    boolean isEnabled();
}

// === ARCHIVO: src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java ===
package com.bank.loans.infrastructure.repository;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LoanJpaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Optional<Loan> findById(UUID id) {
        LoanEntity entity = entityManager.find(LoanEntity.class, id);
        return Optional.ofNullable(entity).map(this::toDomain);
    }

    public Loan save(Loan loan) {
        LoanEntity entity = toEntity(loan);
        if (entity.getId() == null) {
            entityManager.persist(entity);
            return toDomain(entity);
        } else {
            entityManager.merge(entity);
            return toDomain(entity);
        }
    }

    public void deleteById(UUID id) {
        LoanEntity entity = entityManager.find(LoanEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    public Optional<Loan> findByCustomerIdAndId(UUID customerId, UUID loanId) {
        try {
            LoanEntity entity = entityManager
                .createQuery("SELECT l FROM LoanEntity l WHERE l.id = :loanId AND l.customerId = :customerId", LoanEntity.class)
                .setParameter("loanId", loanId)
                .setParameter("customerId", customerId)
                .getSingleResult();
            return Optional.of(toDomain(entity));
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }

    private Loan toDomain(LoanEntity entity) {
        return new Loan(
            entity.getId(),
            entity.getCustomerId(),
            entity.getAmount(),
            entity.getInterestRate(),
            entity.getTermMonths(),
            LoanStatus.valueOf(entity.getStatus()),
            entity.getApprovedAt(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    private LoanEntity toEntity(Loan loan) {
        LoanEntity entity = new LoanEntity();
        entity.setId(loan.id());
        entity.setCustomerId(loan.customerId());
        entity.setAmount(loan.amount());
        entity.setInterestRate(loan.interestRate());
        entity.setTermMonths(loan.termMonths());
        entity.setStatus(loan.status().name());
        entity.setApprovedAt(loan.approvedAt());
        entity.setCreatedAt(loan.createdAt());
        entity.setUpdatedAt(loan.updatedAt());
        return entity;
    }

    @Entity
    @Table(name = "loans")
    public static class LoanEntity {
        @Id
        private UUID id;

        @Column(name = "customer_id", nullable = false)
        private UUID customerId;

        @Column(name = "amount", nullable = false)
        private java.math.BigDecimal amount;

        @Column(name = "interest_rate", nullable = false)
        private java.math.BigDecimal interestRate;

        @Column(name = "term_months", nullable = false)
        private Integer termMonths;

        @Column(name = "status", nullable = false)
        private String status;

        @Column(name = "approved_at")
        private LocalDate approvedAt;

        @Column(name = "created_at", nullable = false)
        private LocalDate createdAt;

        @Column(name = "updated_at", nullable = false)
        private LocalDate updatedAt;

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public UUID getCustomerId() { return customerId; }
        public void setCustomerId(UUID customerId) { this.customerId = customerId; }
        public java.math.BigDecimal getAmount() { return amount; }
        public void setAmount(java.math.BigDecimal amount) { this.amount = amount; }
        public java.math.BigDecimal getInterestRate() { return interestRate; }
        public void setInterestRate(java.math.BigDecimal interestRate) { this.interestRate = interestRate; }
        public Integer getTermMonths() { return termMonths; }
        public void setTermMonths(Integer termMonths) { this.termMonths = termMonths; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public LocalDate getApprovedAt() { return approvedAt; }
        public void setApprovedAt(LocalDate approvedAt) { this.approvedAt = approvedAt; }
        public LocalDate getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
        public LocalDate getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(LocalDate updatedAt) { this.updatedAt = updatedAt; }
    }
}

// === ARCHIVO: src/main/java/com/bank/loans/application/usecase/GetLoanUseCase.java ===
package com.bank.loans.application.usecase;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.port.CachePort;
import com.bank.loans.domain.port.LoanRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class GetLoanUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GetLoanUseCase.class);
    private static final String CACHE_KEY_PREFIX = "loan:";
    private static final long CACHE_TIMEOUT_MINUTES = 30;

    private final LoanRepositoryPort loanRepository;
    private final CachePort cachePort;

    public GetLoanUseCase(LoanRepositoryPort loanRepository, CachePort cachePort) {
        this.loanRepository = loanRepository;
        this.cachePort = cachePort;
    }

    public Optional<Loan> execute(UUID loanId) {
        String cacheKey = buildCacheKey(loanId);
        logger.debug("Recuperando préstamo con ID: {}", loanId);

        if (cachePort.isEnabled()) {
            Optional<Loan> cachedLoan = cachePort.get(cacheKey, Loan.class);
            if (cachedLoan.isPresent()) {
                logger.info("Préstamo {} encontrado en caché", loanId);
                return cachedLoan;
            }
            logger.debug("Préstamo {} no encontrado en caché, consultando base de datos", loanId);
        }

        Optional<Loan> loan = loanRepository.findById(loanId);

        if (loan.isPresent() && cachePort.isEnabled()) {
            Loan loanData = loan.get();
            cachePort.put(cacheKey, loanData, CACHE_TIMEOUT_MINUTES, TimeUnit.MINUTES);
            logger.info("Préstamo {} almacenado en caché por {} minutos", loanId, CACHE_TIMEOUT_MINUTES);
        }

        return loan;
    }

    public Optional<Loan> executeForCustomer(UUID customerId, UUID loanId) {
        String cacheKey = buildCacheKeyForCustomer(customerId, loanId);
        logger.debug("Recuperando préstamo {} para cliente {}", loanId, customerId);

        if (cachePort.isEnabled()) {
            Optional<Loan> cachedLoan = cachePort.get(cacheKey, Loan.class);
            if (cachedLoan.isPresent()) {
                logger.info("Préstamo {} para cliente {} encontrado en caché", loanId, customerId);
                return cachedLoan;
            }
        }

        Optional<Loan> loan = loanRepository.findByCustomerIdAndId(customerId, loanId);

        if (loan.isPresent() && cachePort.isEnabled()) {
            Loan loanData = loan.get();
            cachePort.put(cacheKey, loanData, CACHE_TIMEOUT_MINUTES, TimeUnit.MINUTES);
            logger.info("Préstamo {} para cliente {} almacenado en caché", loanId, customerId);
        }

        return loan;
    }

    public Loan saveLoan(Loan loan) {
        logger.info("Guardando préstamo con ID: {}", loan.id());
        Loan savedLoan = loanRepository.save(loan);

        if (cachePort.isEnabled()) {
            String cacheKey = buildCacheKey(loan.id());
            cachePort.put(cacheKey, savedLoan, CACHE_TIMEOUT_MINUTES, TimeUnit.MINUTES);
            logger.info("Préstamo {} guardado y almacenado en caché", loan.id());
        }

        return savedLoan;
    }

    public void deleteLoan(UUID loanId) {
        logger.info("Eliminando préstamo con ID: {}", loanId);
        loanRepository.deleteById(loanId);

        if (cachePort.isEnabled()) {
            String cacheKey = buildCacheKey(loanId);
            cachePort.evict(cacheKey);
            logger.info("Préstamo {} eliminado de la base de datos y de caché", loanId);
        }
    }

    private String buildCacheKey(UUID loanId) {
        return CACHE_KEY_PREFIX + loanId.toString();
    }

    private String buildCacheKeyForCustomer(UUID customerId, UUID loanId) {
        return CACHE_KEY_PREFIX + "customer:" + customerId.toString() + ":" + loanId.toString();
    }
}

// === ARCHIVO: src/main/java/com/bank/loans/infrastructure/adapter/LoanJpaRepositoryAdapter.java ===
package com.bank.loans.infrastructure.adapter;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import com.bank.loans.domain.port.LoanRepositoryPort;
import com.bank.loans.infrastructure.repository.LoanJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public class LoanJpaRepositoryAdapter implements LoanRepositoryPort {

    private final LoanJpaRepository loanJpaRepository;

    public LoanJpaRepositoryAdapter(LoanJpaRepository loanJpaRepository) {
        this.loanJpaRepository = loanJpaRepository;
    }

    @Override
    public Optional<Loan> findById(UUID id) {
        return loanJpaRepository.findById(id);
    }

    @Override
    public Loan save(Loan loan) {
        return loanJpaRepository.save(loan);
    }

    @Override
    public void deleteById(UUID id) {
        loanJpaRepository.deleteById(id);
    }

    @Override
    public Optional<Loan> findByCustomerIdAndId(UUID customerId, UUID loanId) {
        return loanJpaRepository.findByCustomerIdAndId(customerId, loanId);
    }
}

// === ARCHIVO: src/main/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapter.java ===
package com.bank.loans.infrastructure.adapter;

import com.bank.loans.domain.port.CachePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheAdapter implements CachePort {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheAdapter.class);
    private static final String CACHE_PREFIX = "loan:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final boolean cacheEnabled;

    public RedisCacheAdapter(RedisTemplate<String, Object> redisTemplate,
                            ObjectMapper objectMapper,
                            boolean cacheEnabled) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.cacheEnabled = cacheEnabled;
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        if (!isEnabled()) {
            logger.debug("Cache disabled, skipping Redis get for key: {}", key);
            return Optional.empty();
        }

        try {
            String cacheKey = buildCacheKey(key);
            Object value = redisTemplate.opsForValue().get(cacheKey);
            
            if (value == null) {
                logger.debug("Cache miss for key: {}", cacheKey);
                return Optional.empty();
            }

            logger.debug("Cache hit for key: {}", cacheKey);
            
            if (type.isInstance(value)) {
                return Optional.of(type.cast(value));
            }

            String jsonValue = convertToJson(value);
            T deserialized = objectMapper.readValue(jsonValue, type);
            return Optional.of(deserialized);
            
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize cached value for key: {}", key, e);
            evict(key);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error retrieving from Redis cache for key: {}", key, e);
            return Optional.empty();
        }
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {
        if (!isEnabled()) {
            logger.debug("Cache disabled, skipping Redis put for key: {}", key);
            return;
        }

        try {
            String cacheKey = buildCacheKey(key);
            Object valueToStore = serializeValue(value);
            redisTemplate.opsForValue().set(cacheKey, valueToStore, timeout, timeUnit);
            logger.debug("Cached value with key: {} for {} {}", 
                cacheKey, timeout, timeUnit.name().toLowerCase());
        } catch (Exception e) {
            logger.error("Error storing in Redis cache for key: {}", key, e);
        }
    }

    @Override
    public void evict(String key) {
        if (!isEnabled()) {
            return;
        }

        try {
            String cacheKey = buildCacheKey(key);
            Boolean deleted = redisTemplate.delete(cacheKey);
            if (Boolean.TRUE.equals(deleted)) {
                logger.debug("Evicted cache key: {}", cacheKey);
            } else {
                logger.debug("Cache key not found for eviction: {}", cacheKey);
            }
        } catch (Exception e) {
            logger.error("Error evicting from Redis cache for key: {}", key, e);
        }
    }

    @Override
    public boolean isEnabled() {
        return cacheEnabled;
    }

    private String buildCacheKey(String key) {
        return CACHE_PREFIX + key;
    }

    private Object serializeValue(Object value) {
        if (value instanceof String || value instanceof Number || 
            value instanceof Boolean || value == null) {
            return value;
        }
        
        try {
            String json = objectMapper.writeValueAsString(value);
            return json;
        } catch (JsonProcessingException e) {
            logger.warn("Failed to serialize value, storing as-is: {}", value.getClass().getName());
            return value;
        }
    }

    private String convertToJson(Object value) throws JsonProcessingException {
        if (value instanceof String) {
            return (String) value;
        }
        return objectMapper.writeValueAsString(value);
    }

    public void evictAllByPattern(String pattern) {
        if (!isEnabled()) {
            return;
        }
        
        try {
            String searchPattern = CACHE_PREFIX + pattern;
            var keys = redisTemplate.keys(searchPattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                logger.info("Evicted {} cache entries matching pattern: {}", keys.size(), searchPattern);
            }
        } catch (Exception e) {
            logger.error("Error evicting cache entries by pattern: {}", pattern, e);
        }
    }
}

// === ARCHIVO: src/main/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapter.java ===
package com.bank.loans.infrastructure.adapter;

import com.bank.loans.domain.port.CachePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class MemcachedCacheAdapter implements CachePort {

    private static final Logger logger = LoggerFactory.getLogger(MemcachedCacheAdapter.class);
    private static final String CACHE_PREFIX = "loan:";

    private final MemcachedClient memcachedClient;
    private final ObjectMapper objectMapper;
    private final boolean cacheEnabled;

    public MemcachedCacheAdapter(MemcachedClient memcachedClient,
                                ObjectMapper objectMapper,
                                boolean cacheEnabled) {
        this.memcachedClient = memcachedClient;
        this.objectMapper = objectMapper;
        this.cacheEnabled = cacheEnabled;
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> type) {
        if (!isEnabled()) {
            logger.debug("Cache disabled, skipping Memcached get for key: {}", key);
            return Optional.empty();
        }

        try {
            String cacheKey = buildCacheKey(key);
            Object cached = memcachedClient.get(cacheKey);
            
            if (cached == null) {
                logger.debug("Cache miss for key: {}", cacheKey);
                return Optional.empty();
            }

            logger.debug("Cache hit for key: {}", cacheKey);
            
            String jsonValue = cached.toString();
            T deserialized = objectMapper.readValue(jsonValue, type);
            return Optional.of(deserialized);
            
        } catch (JsonProcessingException e) {
            logger.error("Failed to deserialize cached value for key: {}", key, e);
            evict(key);
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Error retrieving from Memcached for key: {}", key, e);
            return Optional.empty();
        }
    }

    @Override
    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {
        if (!isEnabled()) {
            logger.debug("Cache disabled, skipping Memcached put for key: {}", key);
            return;
        }

        try {
            String cacheKey = buildCacheKey(key);
            String jsonValue = objectMapper.writeValueAsString(value);
            int expiration = calculateExpiration(timeout, timeUnit);
            memcachedClient.set(cacheKey, expiration, jsonValue);
            logger.debug("Cached value with key: {} for {} {}", 
                cacheKey, timeout, timeUnit.name().toLowerCase());
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize value for caching, key: {}", key, e);
        } catch (Exception e) {
            logger.error("Error storing in Memcached for key: {}", key, e);
        }
    }

    @Override
    public void evict(String key) {
        if (!isEnabled()) {
            return;
        }

        try {
            String cacheKey = buildCacheKey(key);
            memcachedClient.delete(cacheKey);
            logger.debug("Evicted cache key: {}", cacheKey);
        } catch (Exception e) {
            logger.error("Error evicting from Memcached for key: {}", key, e);
        }
    }

    @Override
    public boolean isEnabled() {
        return cacheEnabled;
    }

    private String buildCacheKey(String key) {
        return CACHE_PREFIX + key;
    }

    private int calculateExpiration(long timeout, TimeUnit timeUnit) {
        long seconds = timeUnit.toSeconds(timeout);
        if (seconds > Integer.MAX_VALUE) {
            logger.warn("Expiration time {} exceeds Memcached limit, using max value", seconds);
            return Integer.MAX_VALUE;
        }
        return (int) seconds;
    }

    public void evictAllByPattern(String pattern) {
        if (!isEnabled()) {
            return;
        }
        
        logger.info("Memcached does not support pattern-based eviction, manual iteration required");
        logger.debug("Pattern-based eviction requested: {}{}", CACHE_PREFIX, pattern);
    }

    public boolean isServerAvailable() {
        try {
            return !memcachedClient.getAvailableServers().isEmpty();
        } catch (Exception e) {
            logger.warn("Failed to check Memcached server availability", e);
            return false;
        }
    }
}

// === ARCHIVO: src/main/java/com/bank/loans/infrastructure/config/RedisConfig.java ===
package com.bank.loans.infrastructure.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private int redisPort;

    @Value("${spring.data.redis.password:}")
    private String redisPassword;

    @Value("${spring.data.redis.database:0}")
    private int redisDatabase;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);
        config.setDatabase(redisDatabase);

        if (redisPassword != null && !redisPassword.isEmpty()) {
            config.setPassword(redisPassword);
        }

        logger.info("Configuring Redis connection to {}:{}", redisHost, redisPort);
        
        LettuceConnectionFactory factory = new LettuceConnectionFactory(config);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory,
                                                       ObjectMapper redisObjectMapper) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(redisObjectMapper);

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jsonSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jsonSerializer);
        
        template.afterPropertiesSet();
        
        logger.info("RedisTemplate configured with JSON serialization");
        return template;
    }

    @Bean
    public ObjectMapper redisObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        mapper.registerModule(new JavaTimeModule());
        
        mapper.activateDefaultTyping(
            LaissezFaireSubTypeValidator.instance,
            ObjectMapper.DefaultTyping.NON_FINAL,
            JsonTypeInfo.As.PROPERTY
        );
        
        mapper.configure(com.fasterxml.jackson.databind.SerializationFeature.
            WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.
            FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        logger.info("ObjectMapper configured for Redis serialization");
        return mapper;
    }
}

// === ARCHIVO: src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java ===
package com.bank.loans.infrastructure.config;

import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.InetSocketAddress;

@Configuration
@Profile("!test")
public class MemcachedConfig {

    private static final Logger logger = LoggerFactory.getLogger(MemcachedConfig.class);

    @Value("${cache.memcached.host:localhost}")
    private String memcachedHost;

    @Value("${cache.memcached.port:11211}")
    private int memcachedPort;

    @Value("${cache.memcached.expiration:3600}")
    private int defaultExpiration;

    @Value("${cache.memcached.op-timeout:3000}")
    private int operationTimeout;

    @Value("${cache.memcached.connection-timeout:3000}")
    private int connectionTimeout;

    @Bean(destroyMethod = "shutdown")
    public MemcachedClient memcachedClient() {
        try {
            logger.info("Conectando a Memcached en {}:{}", memcachedHost, memcachedPort);
            MemcachedClient client = new MemcachedClient(
                new InetSocketAddress(memcachedHost, memcachedPort)
            );
            logger.info("Cliente Memcached conectado exitosamente");
            return client;
        } catch (IOException e) {
            logger.error("Error al conectar con Memcached: {}", e.getMessage(), e);
            throw new RuntimeException("No se pudo inicializar el cliente Memcached", e);
        }
    }

    @Bean
    public MemcachedProperties memcachedProperties() {
        MemcachedProperties props = new MemcachedProperties();
        props.setHost(memcachedHost);
        props.setPort(memcachedPort);
        props.setExpiration(defaultExpiration);
        props.setOpTimeout(operationTimeout);
        props.setConnectionTimeout(connectionTimeout);
        return props;
    }

    public int getDefaultExpiration() {
        return defaultExpiration;
    }

    public static class MemcachedProperties {
        private String host;
        private int port;
        private int expiration;
        private int opTimeout;
        private int connectionTimeout;

        public String getHost() { return host; }
        public void setHost(String host) { this.host = host; }
        public int getPort() { return port; }
        public void setPort(int port) { this.port = port; }
        public int getExpiration() { return expiration; }
        public void setExpiration(int expiration) { this.expiration = expiration; }
        public int getOpTimeout() { return opTimeout; }
        public void setOpTimeout(int opTimeout) { this.opTimeout = opTimeout; }
        public int getConnectionTimeout() { return connectionTimeout; }
        public void setConnectionTimeout(int connectionTimeout) { this.connectionTimeout = connectionTimeout; }
    }
}

// === ARCHIVO: src/main/java/com/bank/loans/infrastructure/cache/CacheManager.java ===
package com.bank.loans.infrastructure.cache;

import com.bank.loans.domain.port.CachePort;
import com.bank.loans.infrastructure.adapter.MemcachedCacheAdapter;
import com.bank.loans.infrastructure.adapter.RedisCacheAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class CacheManager {

    private static final Logger logger = LoggerFactory.getLogger(CacheManager.class);
    private static final String CACHE_TYPE_REDIS = "redis";
    private static final String CACHE_TYPE_MEMCACHED = "memcached";

    private final RedisCacheAdapter redisCacheAdapter;
    private final MemcachedCacheAdapter memcachedCacheAdapter;

    @Value("${cache.type:redis}")
    private String cacheType;

    @Value("${cache.default-ttl:1800}")
    private long defaultTtl;

    @Value("${cache.enabled:true}")
    private boolean cacheEnabled;

    @Autowired
    public CacheManager(
            @Autowired(required = false) RedisCacheAdapter redisCacheAdapter,
            @Autowired(required = false) MemcachedCacheAdapter memcachedCacheAdapter) {
        this.redisCacheAdapter = redisCacheAdapter;
        this.memcachedCacheAdapter = memcachedCacheAdapter;
    }

    private CachePort getActiveCache() {
        if (!cacheEnabled) {
            logger.debug("Caché deshabilitado globalmente");
            return null;
        }

        if (CACHE_TYPE_REDIS.equalsIgnoreCase(cacheType)) {
            if (redisCacheAdapter != null && redisCacheAdapter.isEnabled()) {
                logger.debug("Usando Redis como proveedor de caché");
                return redisCacheAdapter;
            }
            logger.warn("Redis configurado pero no disponible, cayendo a Memcached");
        }

        if (CACHE_TYPE_MEMCACHED.equalsIgnoreCase(cacheType) || 
            (CACHE_TYPE_REDIS.equalsIgnoreCase(cacheType) && !redisCacheAdapter.isEnabled())) {
            if (memcachedCacheAdapter != null && memcachedCacheAdapter.isEnabled()) {
                logger.debug("Usando Memcached como proveedor de caché");
                return memcachedCacheAdapter;
            }
        }

        logger.warn("Ningún proveedor de caché disponible, operando sin caché");
        return null;
    }

    public <T> Optional<T> get(String key, Class<T> type) {
        CachePort cache = getActiveCache();
        if (cache == null) {
            return Optional.empty();
        }

        try {
            Optional<T> result = cache.get(key, type);
            if (result.isPresent()) {
                logger.debug("Cache HIT para clave: {}", key);
            } else {
                logger.debug("Cache MISS para clave: {}", key);
            }
            return result;
        } catch (Exception e) {
            logger.error("Error al obtener del cache la clave {}: {}", key, e.getMessage());
            return Optional.empty();
        }
    }

    public void put(String key, Object value, long timeout, TimeUnit timeUnit) {
        CachePort cache = getActiveCache();
        if (cache == null) {
            return;
        }

        try {
            cache.put(key, value, timeout, timeUnit);
            logger.debug("Guardado en cache clave: {} con TTL: {} {}", key, timeout, timeUnit);
        } catch (Exception e) {
            logger.error("Error al guardar en cache la clave {}: {}", key, e.getMessage());
        }
    }

    public void put(String key, Object value) {
        put(key, value, defaultTtl, TimeUnit.SECONDS);
    }

    public void evict(String key) {
        CachePort cache = getActiveCache();
        if (cache == null) {
            return;
        }

        try {
            cache.evict(key);
            logger.debug("Evict del cache para clave: {}", key);
        } catch (Exception e) {
            logger.error("Error al hacer evict del cache para la clave {}: {}", key, e.getMessage());
        }
    }

    public void evictPattern(String pattern) {
        logger.info("Evict por patrón {} no implementado en esta versión", pattern);
    }

    public <T> T getOrCompute(String key, Class<T> type, Supplier<T> computeFunction, long timeout, TimeUnit timeUnit) {
        Optional<T> cached = get(key, type);
        if (cached.isPresent()) {
            return cached.get();
        }

        T value = computeFunction.get();
        if (value != null) {
            put(key, value, timeout, timeUnit);
        }
        return value;
    }

    public <T> T getOrCompute(String key, Class<T> type, Supplier<T> computeFunction) {
        return getOrCompute(key, type, computeFunction, defaultTtl, TimeUnit.SECONDS);
    }

    public String getCacheType() {
        return cacheType;
    }

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public long getDefaultTtl() {
        return defaultTtl;
    }
}

// === ARCHIVO: src/main/java/com/bank/loans/infrastructure/controller/LoanController.java ===
package com.bank.loans.infrastructure.controller;

import com.bank.loans.application.usecase.GetLoanUseCase;
import com.bank.loans.domain.model.Loan;
import com.bank.loans.infrastructure.cache.CacheManager;
import com.bank.loans.infrastructure.validator.LoanValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/loans")
@Validated
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    private static final String CACHE_PREFIX_LOAN = "loan:";
    private static final String CACHE_PREFIX_CUSTOMER_LOANS = "customer:loans:";
    private static final long CACHE_TTL_LOAN = 1800L;
    private static final long CACHE_TTL_CUSTOMER_LOANS = 900L;

    private final GetLoanUseCase getLoanUseCase;
    private final CacheManager cacheManager;
    private final LoanValidator loanValidator;

    @Autowired
    public LoanController(
            GetLoanUseCase getLoanUseCase,
            CacheManager cacheManager,
            LoanValidator loanValidator) {
        this.getLoanUseCase = getLoanUseCase;
        this.cacheManager = cacheManager;
        this.loanValidator = loanValidator;
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponse> getLoanById(
            @PathVariable @NotNull UUID loanId,
            @RequestHeader(value = "X-Cache-Bypass", defaultValue = "false") boolean bypassCache) {

        logger.info("Solicitando préstamo con ID: {}, bypassCache: {}", loanId, bypassCache);

        String cacheKey = CACHE_PREFIX_LOAN + loanId;

        if (!bypassCache && cacheManager.isCacheEnabled()) {
            Optional<LoanResponse> cached = cacheManager.get(cacheKey, LoanResponse.class);
            if (cached.isPresent()) {
                logger.info("Préstamo {} encontrado en caché", loanId);
                return ResponseEntity.ok(cached.get());
            }
        }

        Optional<Loan> loanOpt = getLoanUseCase.findById(loanId);
        if (loanOpt.isEmpty()) {
            logger.warn("Préstamo no encontrado: {}", loanId);
            return ResponseEntity.notFound().build();
        }

        Loan loan = loanOpt.get();
        LoanResponse response = LoanResponse.fromDomain(loan);

        if (!bypassCache && cacheManager.isCacheEnabled()) {
            cacheManager.put(cacheKey, response, CACHE_TTL_LOAN, TimeUnit.SECONDS);
            logger.info("Préstamo {} almacenado en caché", loanId);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<LoanResponse>> getLoansByCustomerId(
            @PathVariable @NotNull UUID customerId,
            @RequestParam(required = false) Integer limit) {

        logger.info("Solicitando préstamos para cliente: {}, limit: {}", customerId, limit);

        String cacheKey = CACHE_PREFIX_CUSTOMER_LOANS + customerId;

        if (cacheManager.isCacheEnabled()) {
            Optional<List<LoanResponse>> cached = cacheManager.get(cacheKey, LoanResponseList.class);
            if (cached.isPresent()) {
                logger.info("Préstamos del cliente {} encontrados en caché", customerId);
                List<LoanResponse> result = cached.get();
                return limit != null && limit < result.size()
                    ? ResponseEntity.ok(result.subList(0, limit))
                    : ResponseEntity.ok(result);
            }
        }

        List<Loan> loans = getLoanUseCase.findByCustomerId(customerId);
        List<LoanResponse> responseList = loans.stream()
            .map(LoanResponse::fromDomain)
            .toList();

        if (cacheManager.isCacheEnabled() && !responseList.isEmpty()) {
            cacheManager.put(cacheKey, responseList, CACHE_TTL_CUSTOMER_LOANS, TimeUnit.SECONDS);
            logger.info("Préstamos del cliente {} almacenados en caché", customerId);
        }

        if (limit != null && limit < responseList.size()) {
            responseList = responseList.subList(0, limit);
        }

        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody CreateLoanRequest request) {
        logger.info("Creando nuevo préstamo para cliente: {}", request.customerId());

        loanValidator.validateCreateRequest(request);

        Loan loan = getLoanUseCase.createLoan(
            request.customerId(),
            request.principal(),
            request.interestRate(),
            request.termMonths(),
            request.purpose()
        );

        String cacheKey = CACHE_PREFIX_LOAN + loan.id();
        if (cacheManager.isCacheEnabled()) {
            cacheManager.put(cacheKey, LoanResponse.fromDomain(loan), CACHE_TTL_LOAN, TimeUnit.SECONDS);
        }

        cacheManager.evict(CACHE_PREFIX_CUSTOMER_LOANS + request.customerId());
        logger.info("Préstamo creado: {}, caché invalidado para cliente {}", loan.id(), request.customerId());

        return ResponseEntity.status(HttpStatus.CREATED).body(LoanResponse.fromDomain(loan));
    }

    @PutMapping("/{loanId}")
    public ResponseEntity<LoanResponse> updateLoan(
            @PathVariable @NotNull UUID loanId,
            @Valid @RequestBody UpdateLoanRequest request) {

        logger.info("Actualizando préstamo: {}", loanId);

        Optional<Loan> existingOpt = getLoanUseCase.findById(loanId);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Loan updated = getLoanUseCase.updateLoan(loanId, request.principal(), request.status());

        String cacheKey = CACHE_PREFIX_LOAN + loanId;
        if (cacheManager.isCacheEnabled()) {
            cacheManager.evict(cacheKey);
            cacheManager.put(cacheKey, LoanResponse.fromDomain(updated), CACHE_TTL_LOAN, TimeUnit.SECONDS);
        }

        cacheManager.evict(CACHE_PREFIX_CUSTOMER_LOANS + existingOpt.get().customerId());
        logger.info("Préstamo actualizado: {}, caché invalidado", loanId);

        return ResponseEntity.ok(LoanResponse.fromDomain(updated));
    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable @NotNull UUID loanId) {
        logger.info("Eliminando préstamo: {}", loanId);

        Optional<Loan> existingOpt = getLoanUseCase.findById(loanId);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        getLoanUseCase.deleteById(loanId);

        String cacheKey = CACHE_PREFIX_LOAN + loanId;
        if (cacheManager.isCacheEnabled()) {
            cacheManager.evict(cacheKey);
        }

        cacheManager.evict(CACHE_PREFIX_CUSTOMER_LOANS + existingOpt.get().customerId());
        logger.info("Préstamo eliminado: {}, caché invalidado", loanId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health/cache")
    public ResponseEntity<CacheHealthResponse> getCacheHealth() {
        CacheHealthResponse health = new CacheHealthResponse(
            cacheManager.isCacheEnabled(),
            cacheManager.getCacheType(),
            cacheManager.getDefaultTtl()
        );
        return ResponseEntity.ok(health);
    }

    public record CreateLoanRequest(
        @NotNull UUID customerId,
        @NotNull java.math.BigDecimal principal,
        @NotNull java.math.BigDecimal interestRate,
        @NotNull Integer termMonths,
        String purpose
    ) {}

    public record UpdateLoanRequest(
        @NotNull java.math.BigDecimal principal,
        @NotNull String status
    ) {}

    public record LoanResponse(
        UUID id,
        UUID customerId,
        java.math.BigDecimal principal,
        java.math.BigDecimal interestRate,
        Integer termMonths,
        String status,
        String purpose,
        java.time.LocalDate createdAt,
        java.time.LocalDate updatedAt
    ) {
        public static LoanResponse fromDomain(Loan loan) {
            return new LoanResponse(
                loan.id(),
                loan.customerId(),
                loan.principal(),
                loan.interestRate(),
                loan.termMonths(),
                loan.status().name(),
                loan.purpose(),
                loan.createdAt(),
                loan.updatedAt()
            );
        }
    }

    public record LoanResponseList(List<LoanResponse> loans) {}

    public record CacheHealthResponse(boolean enabled, String type, long defaultTtl) {}
}

// === ARCHIVO: src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java ===
package com.bank.loans.infrastructure.validator;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class LoanValidator {

    private static final BigDecimal MIN_LOAN_AMOUNT = new BigDecimal("1000.00");
    private static final BigDecimal MAX_LOAN_AMOUNT = new BigDecimal("500000.00");
    private static final int MIN_TERM_MONTHS = 1;
    private static final int MAX_TERM_MONTHS = 360;
    private static final BigDecimal MIN_INTEREST_RATE = new BigDecimal("0.01");
    private static final BigDecimal MAX_INTEREST_RATE = new BigDecimal("0.50");
    private static final BigDecimal MAX_MONTHLY_PAYMENT_RATIO = new BigDecimal("0.60");

    public ValidationResult validate(Loan loan) {
        List<String> errors = new ArrayList<>();
        
        if (loan == null) {
            errors.add("El préstamo no puede ser nulo");
            return new ValidationResult(false, errors);
        }
        
        validateLoanId(loan.id(), errors);
        validateCustomerId(loan.customerId(), errors);
        validateAmount(loan.amount(), errors);
        validateTerm(loan.termMonths(), errors);
        validateInterestRate(loan.interestRate(), errors);
        validateStatus(loan.status(), errors);
        validateDates(loan.issueDate(), loan.dueDate(), errors);
        validateMonthlyPayment(loan.amount(), loan.termMonths(), loan.interestRate(), 
                               loan.monthlyIncome() != null ? loan.monthlyIncome() : BigDecimal.ZERO, errors);
        
        return new ValidationResult(errors.isEmpty(), errors);
    }

    private void validateLoanId(UUID loanId, List<String> errors) {
        if (loanId == null) {
            errors.add("El ID del préstamo es obligatorio");
        }
    }

    private void validateCustomerId(UUID customerId, List<String> errors) {
        if (customerId == null) {
            errors.add("El ID del cliente es obligatorio");
        }
    }

    private void validateAmount(BigDecimal amount, List<String> errors) {
        if (amount == null) {
            errors.add("El monto del préstamo es obligatorio");
            return;
        }
        
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("El monto del préstamo debe ser mayor a cero");
        }
        
        if (amount.compareTo(MIN_LOAN_AMOUNT) < 0) {
            errors.add(String.format("El monto mínimo del préstamo es %s", MIN_LOAN_AMOUNT));
        }
        
        if (amount.compareTo(MAX_LOAN_AMOUNT) > 0) {
            errors.add(String.format("El monto máximo del préstamo es %s", MAX_LOAN_AMOUNT));
        }
    }

    private void validateTerm(int termMonths, List<String> errors) {
        if (termMonths <= 0) {
            errors.add("El plazo del préstamo debe ser mayor a cero");
            return;
        }
        
        if (termMonths < MIN_TERM_MONTHS) {
            errors.add(String.format("El plazo mínimo del préstamo es %d meses", MIN_TERM_MONTHS));
        }
        
        if (termMonths > MAX_TERM_MONTHS) {
            errors.add(String.format("El plazo máximo del préstamos es %d meses", MAX_TERM_MONTHS));
        }
    }

    private void validateInterestRate(BigDecimal interestRate, List<String> errors) {
        if (interestRate == null) {
            errors.add("La tasa de interés es obligatoria");
            return;
        }
        
        if (interestRate.compareTo(BigDecimal.ZERO) <= 0) {
            errors.add("La tasa de interés debe ser mayor a cero");
        }
        
        if (interestRate.compareTo(MIN_INTEREST_RATE) < 0) {
            errors.add(String.format("La tasa mínima de interés es %.2f%%", 
                MIN_INTEREST_RATE.multiply(BigDecimal.valueOf(100))));
        }
        
        if (interestRate.compareTo(MAX_INTEREST_RATE) > 0) {
            errors.add(String.format("La tasa máxima de interés es %.2f%%", 
                MAX_INTEREST_RATE.multiply(BigDecimal.valueOf(100))));
        }
    }

    private void validateStatus(LoanStatus status, List<String> errors) {
        if (status == null) {
            errors.add("El estado del préstamo es obligatorio");
        }
    }

    private void validateDates(LocalDate issueDate, LocalDate dueDate, List<String> errors) {
        LocalDate today = LocalDate.now();
        
        if (issueDate == null) {
            errors.add("La fecha de emisión es obligatoria");
        } else if (issueDate.isBefore(today.minusDays(1))) {
            errors.add("La fecha de emisión no puede ser anterior a hoy");
        }
        
        if (dueDate == null) {
            errors.add("La fecha de vencimiento es obligatoria");
        } else if (issueDate != null && dueDate.isBefore(issueDate)) {
            errors.add("La fecha de vencimiento no puede ser anterior a la fecha de emisión");
        } else if (dueDate.isBefore(today)) {
            errors.add("El préstamo ya está vencido");
        }
    }

    private void validateMonthlyPayment(BigDecimal amount, int termMonths, 
            BigDecimal interestRate, BigDecimal monthlyIncome, List<String> errors) {
        if (amount == null || termMonths <= 0 || interestRate == null || monthlyIncome == null) {
            return;
        }
        
        BigDecimal monthlyPayment = calculateMonthlyPayment(amount, termMonths, interestRate);
        BigDecimal paymentRatio = monthlyPayment.divide(monthlyIncome, 4, java.math.RoundingMode.HALF_UP);
        
        if (paymentRatio.compareTo(MAX_MONTHLY_PAYMENT_RATIO) > 0) {
            errors.add(String.format("La cuota mensual (%.2f) excede el %.0f%% del ingreso mensual (%.2f)",
                monthlyPayment, 
                MAX_MONTHLY_PAYMENT_RATIO.multiply(BigDecimal.valueOf(100)),
                monthlyIncome));
        }
    }

    public BigDecimal calculateMonthlyPayment(BigDecimal principal, int termMonths, BigDecimal annualRate) {
        if (principal == null || termMonths <= 0 || annualRate == null) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), 6, java.math.RoundingMode.HALF_UP);
        
        if (monthlyRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(termMonths), 2, java.math.RoundingMode.HALF_UP);
        }
        
        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate);
        BigDecimal numerator = monthlyRate.multiply(onePlusR.pow(termMonths));
        BigDecimal denominator = onePlusR.pow(termMonths).subtract(BigDecimal.ONE);
        
        return principal.multiply(numerator).divide(denominator, 2, java.math.RoundingMode.HALF_UP);
    }

    public boolean canChangeStatus(Loan currentLoan, LoanStatus newStatus) {
        if (currentLoan == null || currentLoan.status() == null || newStatus == null) {
            return false;
        }
        
        return switch (currentLoan.status()) {
            case PENDING -> newStatus == LoanStatus.APPROVED || newStatus == LoanStatus.REJECTED;
            case APPROVED -> newStatus == LoanStatus.ACTIVE || newStatus == LoanStatus.REJECTED;
            case ACTIVE -> newStatus == LoanStatus.PAID || newStatus == LoanStatus.DEFAULTED || newStatus == LoanStatus.CANCELLED;
            case PAID, DEFAULTED, CANCELLED, REJECTED -> false;
        };
    }

    public record ValidationResult(boolean valid, List<String> errors) {
        public boolean hasErrors() {
            return !valid;
        }
        
        public String getErrorMessage() {
            return valid ? "" : String.join("; ", errors);
        }
    }
}

// === ARCHIVO: src/test/java/com/bank/loans/application/usecase/GetLoanUseCaseTest.java ===
package com.bank.loans.application.usecase;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import com.bank.loans.domain.port.CachePort;
import com.bank.loans.domain.port.LoanRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetLoanUseCase - Tests unitarios para la lógica de caché")
class GetLoanUseCaseTest {

    @Mock
    private LoanRepositoryPort loanRepositoryPort;

    @Mock
    private CachePort cachePort;

    @InjectMocks
    private GetLoanUseCase getLoanUseCase;

    private Loan sampleLoan;
    private UUID loanId;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        loanId = UUID.randomUUID();
        customerId = UUID.randomUUID();
        sampleLoan = new Loan(
            loanId,
            customerId,
            new BigDecimal("50000.00"),
            36,
            new BigDecimal("5.5"),
            LoanStatus.ACTIVE,
            LocalDate.now(),
            LocalDate.now().plusMonths(36)
        );
    }

    @Nested
    @DisplayName("Escenario: Caché habilitada y datos en caché")
    class CacheEnabledWithHit {

        @Test
        @DisplayName("Debe retornar datos del caché cuando están disponibles")
        void shouldReturnFromCacheWhenAvailable() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.of(sampleLoan));

            Optional<Loan> result = getLoanUseCase.execute(loanId, customerId);

            assertTrue(result.isPresent());
            assertEquals(loanId, result.get().id());
            assertEquals(customerId, result.get().customerId());
            verify(cachePort).get(anyString(), eq(Loan.class));
            verify(loanRepositoryPort, never()).findById(any());
            verify(loanRepositoryPort, never()).findByCustomerIdAndId(any(), any());
        }

        @Test
        @DisplayName("No debe invocar al repositorio cuando hay acierto de caché")
        void shouldNotCallRepositoryOnCacheHit() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.of(sampleLoan));

            getLoanUseCase.execute(loanId, customerId);

            verify(loanRepositoryPort, times(0)).findById(any());
            verify(loanRepositoryPort, times(0)).findByCustomerIdAndId(any(), any());
        }
    }

    @Nested
    @DisplayName("Escenario: Caché habilitada pero sin datos en caché")
    class CacheEnabledWithMiss {

        @Test
        @DisplayName("Debe consultar el repositorio cuando no hay datos en caché")
        void shouldQueryRepositoryOnCacheMiss() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.empty());
            when(loanRepositoryPort.findByCustomerIdAndId(customerId, loanId))
                .thenReturn(Optional.of(sampleLoan));

            Optional<Loan> result = getLoanUseCase.execute(loanId, customerId);

            assertTrue(result.isPresent());
            assertEquals(loanId, result.get().id());
            verify(loanRepositoryPort).findByCustomerIdAndId(customerId, loanId);
        }

        @Test
        @DisplayName("Debe almacenar en caché el resultado del repositorio")
        void shouldPopulateCacheOnRepositoryFetch() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.empty());
            when(loanRepositoryPort.findByCustomerIdAndId(customerId, loanId))
                .thenReturn(Optional.of(sampleLoan));

            getLoanUseCase.execute(loanId, customerId);

            verify(cachePort).put(anyString(), eq(sampleLoan), anyLong(), any(TimeUnit.class));
        }
    }

    @Nested
    @DisplayName("Escenario: Caché deshabilitada")
    class CacheDisabled {

        @Test
        @DisplayName("Debe consultar directamente el repositorio cuando la caché está deshabilitada")
        void shouldBypassCacheWhenDisabled() {
            when(cachePort.isEnabled()).thenReturn(false);
            when(loanRepositoryPort.findByCustomerIdAndId(customerId, loanId))
                .thenReturn(Optional.of(sampleLoan));

            Optional<Loan> result = getLoanUseCase.execute(loanId, customerId);

            assertTrue(result.isPresent());
            verify(cachePort, never()).get(anyString(), any(Class.class));
            verify(loanRepositoryPort).findByCustomerIdAndId(customerId, loanId);
        }
    }

    @Nested
    @DisplayName("Escenario: Préstamo no encontrado")
    class LoanNotFound {

        @Test
        @DisplayName("Debe retornar Optional.empty() cuando el préstamo no existe")
        void shouldReturnEmptyWhenLoanNotFound() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.empty());
            when(loanRepositoryPort.findByCustomerIdAndId(customerId, loanId))
                .thenReturn(Optional.empty());

            Optional<Loan> result = getLoanUseCase.execute(loanId, customerId);

            assertTrue(result.isEmpty());
            verify(cachePort, never()).put(anyString(), any(), anyLong(), any());
        }
    }

    @Nested
    @DisplayName("Escenario: Validación de parámetros")
    class ParameterValidation {

        @Test
        @DisplayName("Debe lanzar excepción cuando el loanId es nulo")
        void shouldThrowExceptionWhenLoanIdIsNull() {
            assertThrows(IllegalArgumentException.class, 
                () -> getLoanUseCase.execute(null, customerId));
        }

        @Test
        @DisplayName("Debe lanzar excepción cuando el customerId es nulo")
        void shouldThrowExceptionWhenCustomerIdIsNull() {
            assertThrows(IllegalArgumentException.class, 
                () -> getLoanUseCase.execute(loanId, null));
        }
    }

    @Nested
    @DisplayName("Escenario: Generación de clave de caché")
    class CacheKeyGeneration {

        @Test
        @DisplayName("Debe generar una clave de caché única por loan y customer")
        void shouldGenerateUniqueCacheKey() {
            when(cachePort.isEnabled()).thenReturn(true);
            when(cachePort.get(anyString(), eq(Loan.class))).thenReturn(Optional.of(sampleLoan));

            getLoanUseCase.execute(loanId, customerId);

            verify(cachePort).get(contains(loanId.toString()), eq(Loan.class));
            verify(cachePort).get(contains(customerId.toString()), eq(Loan.class));
        }
    }
}

// === ARCHIVO: src/test/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapterTest.java ===
package com.bank.loans.infrastructure.adapter;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RedisCacheAdapter - Tests unitarios para interacción con Redis")
class RedisCacheAdapterTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    private RedisCacheAdapter redisCacheAdapter;

    private Loan sampleLoan;
    private String cacheKey;
    private long cacheTimeout = 30L;
    private TimeUnit cacheTimeUnit = TimeUnit.MINUTES;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        redisCacheAdapter = new RedisCacheAdapter(redisTemplate, cacheTimeout, cacheTimeUnit);

        UUID loanId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        sampleLoan = new Loan(
            loanId,
            customerId,
            new BigDecimal("75000.00"),
            48,
            new BigDecimal("4.9"),
            LoanStatus.ACTIVE,
            LocalDate.now(),
            LocalDate.now().plusMonths(48)
        );
        cacheKey = "loan:" + customerId + ":" + loanId;
    }

    @Nested
    @DisplayName("Operación: get - Recuperar datos del caché")
    class GetOperation {

        @Test
        @DisplayName("Debe retornar el valor cuando existe en Redis")
        void shouldReturnValueWhenExistsInRedis() {
            when(valueOperations.get(cacheKey)).thenReturn(sampleLoan);

            Optional<Loan> result = redisCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isPresent());
            assertEquals(sampleLoan.id(), result.get().id());
            assertEquals(sampleLoan.customerId(), result.get().customerId());
            verify(valueOperations).get(cacheKey);
        }

        @Test
        @DisplayName("Debe retornar Optional.empty() cuando la clave no existe")
        void shouldReturnEmptyWhenKeyNotExists() {
            when(valueOperations.get(cacheKey)).thenReturn(null);

            Optional<Loan> result = redisCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
            verify(valueOperations).get(cacheKey);
        }

        @Test
        @DisplayName("Debe deserializar correctamente el tipo solicitado")
        void shouldDeserializeCorrectType() {
            when(valueOperations.get(cacheKey)).thenReturn(sampleLoan);

            Optional<Loan> result = redisCacheAdapter.get(cacheKey, Loan.class);

            assertInstanceOf(Loan.class, result.get());
        }
    }

    @Nested
    @DisplayName("Operación: put - Almacenar datos en caché")
    class PutOperation {

        @Test
        @DisplayName("Debe almacenar el valor en Redis con timeout configurado")
        void shouldStoreValueWithConfiguredTimeout() {
            redisCacheAdapter.put(cacheKey, sampleLoan, cacheTimeout, cacheTimeUnit);

            verify(valueOperations).set(cacheKey, sampleLoan, cacheTimeout, cacheTimeUnit);
        }

        @Test
        @DisplayName("Debe usar timeout personalizado cuando se especifica")
        void shouldUseCustomTimeoutWhenProvided() {
            long customTimeout = 60L;
            TimeUnit customUnit = TimeUnit.SECONDS;

            redisCacheAdapter.put(cacheKey, sampleLoan, customTimeout, customUnit);

            verify(valueOperations).set(cacheKey, sampleLoan, customTimeout, customUnit);
        }

        @Test
        @DisplayName("Debe permitir valores nulos")
        void shouldAllowNullValues() {
            redisCacheAdapter.put(cacheKey, null, cacheTimeout, cacheTimeUnit);

            verify(valueOperations).set(eq(cacheKey), isNull(), anyLong(), any(TimeUnit.class));
        }
    }

    @Nested
    @DisplayName("Operación: evict - Eliminar datos del caché")
    class EvictOperation {

        @Test
        @DisplayName("Debe eliminar la clave de Redis")
        void shouldDeleteKeyFromRedis() {
            when(redisTemplate.delete(cacheKey)).thenReturn(true);

            redisCacheAdapter.evict(cacheKey);

            verify(redisTemplate).delete(cacheKey);
        }

        @Test
        @DisplayName("Debe retornar true cuando la clave fue eliminada")
        void shouldReturnTrueWhenKeyDeleted() {
            when(redisTemplate.delete(cacheKey)).thenReturn(true);

            redisCacheAdapter.evict(cacheKey);

            verify(redisTemplate).delete(cacheKey);
        }
    }

    @Nested
    @DisplayName("Operación: isEnabled - Verificar estado del adaptador")
    class IsEnabledOperation {

        @Test
        @DisplayName("Debe retornar true cuando Redis está configurado")
        void shouldReturnTrueWhenRedisIsConfigured() {
            assertTrue(redisCacheAdapter.isEnabled());
        }

        @Test
        @DisplayName("Debe retornar true por defecto al inicializar el adaptador")
        void shouldReturnTrueByDefault() {
            RedisCacheAdapter adapter = new RedisCacheAdapter(redisTemplate, 30, TimeUnit.MINUTES);
            assertTrue(adapter.isEnabled());
        }
    }

    @Nested
    @DisplayName("Escenario: Manejo de errores de Redis")
    class ErrorHandling {

        @Test
        @DisplayName("Debe manejar excepciones de conexión gracefully")
        void shouldHandleConnectionExceptions() {
            when(valueOperations.get(cacheKey)).thenThrow(new RuntimeException("Redis connection failed"));

            Optional<Loan> result = redisCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Debe manejar errores al escribir en caché")
        void shouldHandleWriteErrors() {
            doThrow(new RuntimeException("Redis write error"))
                .when(valueOperations).set(anyString(), any(), anyLong(), any(TimeUnit.class));

            assertDoesNotThrow(() -> 
                redisCacheAdapter.put(cacheKey, sampleLoan, cacheTimeout, cacheTimeUnit));
        }

        @Test
        @DisplayName("Debe manejar errores al eliminar claves")
        void shouldHandleDeleteErrors() {
            when(redisTemplate.delete(cacheKey)).thenThrow(new RuntimeException("Redis delete error"));

            assertDoesNotThrow(() -> redisCacheAdapter.evict(cacheKey));
        }
    }

    @Nested
    @DisplayName("Escenario: Configuración del adaptador")
    class AdapterConfiguration {

        @Test
        @DisplayName("Debe usar los valores de timeout configurados en el constructor")
        void shouldUseConfiguredTimeoutValues() {
            long expectedTimeout = 45L;
            TimeUnit expectedUnit = TimeUnit.HOURS;

            RedisCacheAdapter adapter = new RedisCacheAdapter(redisTemplate, expectedTimeout, expectedUnit);

            assertTrue(adapter.isEnabled());
        }

        @Test
        @DisplayName("Debe permitir configuración de timeout cero")
        void shouldAllowZeroTimeout() {
            RedisCacheAdapter adapter = new RedisCacheAdapter(redisTemplate, 0L, TimeUnit.SECONDS);

            assertTrue(adapter.isEnabled());
        }
    }
}

// === ARCHIVO: src/test/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapterTest.java ===
package com.bank.loans.infrastructure.adapter;

import com.bank.loans.domain.model.Loan;
import com.bank.loans.domain.model.Loan.LoanStatus;
import net.spy.memcached.MemcachedClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemcachedCacheAdapter - Tests unitarios para interacción con Memcached")
class MemcachedCacheAdapterTest {

    @Mock
    private MemcachedClient memcachedClient;

    @Mock
    private Future<Boolean> future;

    private MemcachedCacheAdapter memcachedCacheAdapter;

    private Loan sampleLoan;
    private String cacheKey;
    private int expirationSeconds = 1800;

    @BeforeEach
    void setUp() {
        memcachedCacheAdapter = new MemcachedCacheAdapter(memcachedClient, expirationSeconds);

        UUID loanId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        sampleLoan = new Loan(
            loanId,
            customerId,
            new BigDecimal("100000.00"),
            60,
            new BigDecimal("3.99"),
            LoanStatus.PENDING,
            LocalDate.now(),
            LocalDate.now().plusMonths(60)
        );
        cacheKey = "loan_" + customerId + "_" + loanId;
    }

    @Nested
    @DisplayName("Operación: get - Recuperar datos del caché")
    class GetOperation {

        @Test
        @DisplayName("Debe retornar el valor cuando existe en Memcached")
        void shouldReturnValueWhenExistsInMemcached() {
            when(memcachedClient.get(cacheKey)).thenReturn(sampleLoan);

            Optional<Loan> result = memcachedCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isPresent());
            assertEquals(sampleLoan.id(), result.get().id());
            assertEquals(sampleLoan.customerId(), result.get().customerId());
            verify(memcachedClient).get(cacheKey);
        }

        @Test
        @DisplayName("Debe retornar Optional.empty() cuando la clave no existe")
        void shouldReturnEmptyWhenKeyNotExists() {
            when(memcachedClient.get(cacheKey)).thenReturn(null);

            Optional<Loan> result = memcachedCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
            verify(memcachedClient).get(cacheKey);
        }

        @Test
        @DisplayName("Debe manejar objetos con cast incorrecto gracefully")
        void shouldHandleIncorrectCast() {
            when(memcachedClient.get(cacheKey)).thenReturn("not a loan object");

            Optional<Loan> result = memcachedCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("Operación: put - Almacenar datos en caché")
    class PutOperation {

        @Test
        @DisplayName("Debe almacenar el valor en Memcached con expiración configurada")
        void shouldStoreValueWithConfiguredExpiration() {
            when(memcachedClient.set(eq(cacheKey), eq(expirationSeconds), any())).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.put(cacheKey, sampleLoan, expirationSeconds, TimeUnit.SECONDS);

            verify(memcachedClient).set(cacheKey, expirationSeconds, sampleLoan);
        }

        @Test
        @DisplayName("Debe usar la expiración por defecto cuando no se especifica")
        void shouldUseDefaultExpirationWhenNotSpecified() {
            when(memcachedClient.set(eq(cacheKey), eq(expirationSeconds), any())).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.put(cacheKey, sampleLoan, 0, TimeUnit.SECONDS);

            verify(memcachedClient).set(cacheKey, expirationSeconds, sampleLoan);
        }

        @Test
        @DisplayName("Debe convertir timeout a segundos para Memcached")
        void shouldConvertTimeoutToSeconds() {
            long minutesTimeout = 5L;
            int expectedSeconds = 300;
            when(memcachedClient.set(eq(cacheKey), eq(expectedSeconds), any())).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.put(cacheKey, sampleLoan, minutesTimeout, TimeUnit.MINUTES);

            verify(memcachedClient).set(cacheKey, expectedSeconds, sampleLoan);
        }
    }

    @Nested
    @DisplayName("Operación: evict - Eliminar datos del caché")
    class EvictOperation {

        @Test
        @DisplayName("Debe eliminar la clave de Memcached")
        void shouldDeleteKeyFromMemcached() {
            when(memcachedClient.delete(cacheKey)).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.evict(cacheKey);

            verify(memcachedClient).delete(cacheKey);
        }

        @Test
        @DisplayName("Debe manejar la eliminación de claves inexistentes")
        void shouldHandleDeletionOfNonExistentKeys() {
            when(memcachedClient.delete(cacheKey)).thenReturn(null);

            assertDoesNotThrow(() -> memcachedCacheAdapter.evict(cacheKey));
        }
    }

    @Nested
    @DisplayName("Operación: isEnabled - Verificar estado del adaptador")
    class IsEnabledOperation {

        @Test
        @DisplayName("Debe retornar true cuando Memcached está configurado")
        void shouldReturnTrueWhenMemcachedIsConfigured() {
            assertTrue(memcachedCacheAdapter.isEnabled());
        }

        @Test
        @DisplayName("Debe retornar true por defecto al inicializar el adaptador")
        void shouldReturnTrueByDefault() {
            MemcachedCacheAdapter adapter = new MemcachedCacheAdapter(memcachedClient, 1800);
            assertTrue(adapter.isEnabled());
        }
    }

    @Nested
    @DisplayName("Escenario: Manejo de errores de Memcached")
    class ErrorHandling {

        @Test
        @DisplayName("Debe manejar excepciones de conexión gracefully")
        void shouldHandleConnectionExceptions() {
            when(memcachedClient.get(cacheKey)).thenThrow(new RuntimeException("Memcached connection failed"));

            Optional<Loan> result = memcachedCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Debe manejar errores al escribir en caché")
        void shouldHandleWriteErrors() {
            when(memcachedClient.set(anyString(), anyInt(), any())).thenThrow(new RuntimeException("Memcached write error"));

            assertDoesNotThrow(() -> 
                memcachedCacheAdapter.put(cacheKey, sampleLoan, expirationSeconds, TimeUnit.SECONDS));
        }

        @Test
        @DisplayName("Debe manejar errores al eliminar claves")
        void shouldHandleDeleteErrors() {
            when(memcachedClient.delete(cacheKey)).thenThrow(new RuntimeException("Memcached delete error"));

            assertDoesNotThrow(() -> memcachedCacheAdapter.evict(cacheKey));
        }

        @Test
        @DisplayName("Debe manejar timeouts de operaciones")
        void shouldHandleOperationTimeouts() throws Exception {
            when(memcachedClient.get(cacheKey)).thenThrow(new java.util.concurrent.TimeoutException("Operation timeout"));

            Optional<Loan> result = memcachedCacheAdapter.get(cacheKey, Loan.class);

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("Escenario: Configuración del adaptador")
    class AdapterConfiguration {

        @Test
        @DisplayName("Debe usar los valores de expiración configurados en el constructor")
        void shouldUseConfiguredExpirationValues() {
            int customExpiration = 3600;
            MemcachedCacheAdapter adapter = new MemcachedCacheAdapter(memcachedClient, customExpiration);

            assertTrue(adapter.isEnabled());
        }

        @Test
        @DisplayName("Debe permitir configuración de expiración cero")
        void shouldAllowZeroExpiration() {
            MemcachedCacheAdapter adapter = new MemcachedCacheAdapter(memcachedClient, 0);

            assertTrue(adapter.isEnabled());
        }

        @Test
        @DisplayName("Debe convertir correctamente diferentes unidades de tiempo")
        void shouldConvertDifferentTimeUnits() {
            when(memcachedClient.set(eq(cacheKey), anyInt(), any())).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.put(cacheKey, sampleLoan, 1, TimeUnit.HOURS);

            verify(memcachedClient).set(cacheKey, 3600, sampleLoan);
        }
    }

    @Nested
    @DisplayName("Escenario: Serialización de objetos")
    class Serialization {

        @Test
        @DisplayName("Debe almacenar objetos serializables en Memcached")
        void shouldStoreSerializableObjects() {
            when(memcachedClient.set(eq(cacheKey), eq(expirationSeconds), any(Loan.class))).thenReturn(future);
            when(future.get(anyLong(), any(TimeUnit.class))).thenReturn(true);

            memcachedCacheAdapter.put(cacheKey, sampleLoan, expirationSeconds, TimeUnit.SECONDS);

            verify(memcachedClient).set(cacheKey, expirationSeconds, instanceOf(Loan.class));
        }
    }
}
```
