# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Optimización de rendimiento con Redis y Memcached en un sistema de alta concurrencia**.

| | |
|---|---|
| Tema | optimización de desempeño con caché: redis y memcached |
| Nivel | senior-l2 |
| Chapter | Backend |
| Especialidad | Java |
| Stack | Java / Spring Boot 3.5 |
| Patron arquitectonico | hexagonal/clean con capas de dominio, aplicación e infraestructura |
| Tiempo estimado | 15 horas |

## Receta del stack

Esqueleto obligatorio:

- `pom.xml en la raiz`
- `clase con @SpringBootApplication`
- `application.yml en src/main/resources`
- `capa de dominio con entidades y puertos`
- `capa de aplicacion con casos de uso`
- `capa de infraestructura con adaptadores y @RestController`

Trampas conocidas:

- TODA `<version>` del pom va con tres segmentos: la del parent (ej. `3.5.6`) y la de cada dependencia que la lleve (ej. Resilience4j `2.2.0`). `3.4` y `2.0` no existen como artefacto y el build muere resolviendo dependencias.
- Las dependencias que el parent POM gestiona van SIN `<version>`: `spring-boot-starter-web`, `-data-jpa`, `-validation`, `-test`, etc.
- Resilience4j publica un artefacto por linea de Spring Boot. Con Spring Boot 3 va `resilience4j-spring-boot3` con version de tres segmentos (ej. `2.2.0`, no `2.0`). `resilience4j-spring-boot2` es de Spring Boot 2 y rompe el arranque.
- Si usas anotaciones de validacion (`@NotNull`, `@Size`, `@Positive`) declara `spring-boot-starter-validation`: el starter web no las trae.
- El `spring-boot-maven-plugin` tiene que estar en `<build><plugins>` o no se empaqueta ejecutable.
- Spring Boot 3 usa `jakarta.*`, nunca `javax.*`.
- Cada archivo empieza con su `package` y con un `import` por cada clase del proyecto que viva en otro paquete. Usar `PaymentService` desde `infrastructure` sin `import com.x.application.PaymentService` no compila.

Dependencias:

- org.springframework.boot:spring-boot-starter-web 3.5.6
- org.springframework.boot:spring-boot-starter-data-jpa n/a
- org.springframework.boot:spring-boot-starter-validation n/a
- org.springframework.boot:spring-boot-starter-cache n/a
- org.springframework.boot:spring-boot-starter-data-redis n/a
- com.google.code.simple-spring-memcached:spring-cache 4.2.0
- com.google.code.simple-spring-memcached:spymemcached 2.12.3
- org.postgresql:postgresql n/a
- org.springframework.boot:spring-boot-starter-test n/a
- org.mockito:mockito-core n/a

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Implementación de caché básica**: Configuración de Redis y lógica de caché implementada para los datos de préstamos más solicitados.
- **Fase 2 — Estrategias de invalidación de caché**: Estrategia de invalidación de caché implementada y verificada.
- **Fase 3 — Optimización de rendimiento con Memcached**: Configuración de Memcached y lógica de caché implementada para los datos de préstamos.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Referencias colgando (86)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/bank/loans/LoansApplication.java` — `com.bank.loans.infrastructure.config.CacheProperties`
      El import com.bank.loans.infrastructure.config.CacheProperties usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/bank/loans/LoansApplication.java` — `com.bank.loans.application.port.out.CachePort`
      El import com.bank.loans.application.port.out.CachePort usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/bank/loans/LoansApplication.java` — `com.bank.loans.application.service.LoanService`
      El import com.bank.loans.application.service.LoanService usa un paquete propio del proyecto pero ningun archivo generado declara ese tipo. Falta generar la clase/interfaz, o el import esta mal escrito.
- [ ] `src/main/java/com/bank/loans/LoansApplication.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/application/usecase/GetLoanUseCase.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapter.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.core.JsonProcessingException pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapter.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.core.JsonProcessingException pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapter.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/infrastructure/config/RedisConfig.java` — `com.fasterxml.jackson`
      El import com.fasterxml.jackson.annotation.JsonTypeInfo pertenece a com.fasterxml.jackson, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/infrastructure/config/RedisConfig.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/infrastructure/cache/CacheManager.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/bank/loans/LoansApplication.java` — `CacheManager.initializeCacheStrategies`
      Se invoca `initializeCacheStrategies` sobre `CacheManager`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getId`
      Se invoca `getId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getCustomerId`
      Se invoca `getCustomerId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getAmount`
      Se invoca `getAmount` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getInterestRate`
      Se invoca `getInterestRate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getTermMonths`
      Se invoca `getTermMonths` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getStatus`
      Se invoca `getStatus` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getApprovedAt`
      Se invoca `getApprovedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getCreatedAt`
      Se invoca `getCreatedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.getUpdatedAt`
      Se invoca `getUpdatedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setId`
      Se invoca `setId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setCustomerId`
      Se invoca `setCustomerId` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.customerId`
      Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setAmount`
      Se invoca `setAmount` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.amount`
      Se invoca `amount` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setInterestRate`
      Se invoca `setInterestRate` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.interestRate`
      Se invoca `interestRate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setTermMonths`
      Se invoca `setTermMonths` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.termMonths`
      Se invoca `termMonths` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setStatus`
      Se invoca `setStatus` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.status`
      Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setApprovedAt`
      Se invoca `setApprovedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.approvedAt`
      Se invoca `approvedAt` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setCreatedAt`
      Se invoca `setCreatedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.createdAt`
      Se invoca `createdAt` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `LoanEntity.setUpdatedAt`
      Se invoca `setUpdatedAt` sobre `LoanEntity`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java` — `Loan.updatedAt`
      Se invoca `updatedAt` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/application/usecase/GetLoanUseCase.java` — `Loan.isPresent`
      Se invoca `isPresent` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/application/usecase/GetLoanUseCase.java` — `Loan.get`
      Se invoca `get` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/application/usecase/GetLoanUseCase.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `MemcachedProperties.setHost`
      Se invoca `setHost` sobre `MemcachedProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `MemcachedProperties.setPort`
      Se invoca `setPort` sobre `MemcachedProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `MemcachedProperties.setExpiration`
      Se invoca `setExpiration` sobre `MemcachedProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `MemcachedProperties.setOpTimeout`
      Se invoca `setOpTimeout` sobre `MemcachedProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java` — `MemcachedProperties.setConnectionTimeout`
      Se invoca `setConnectionTimeout` sobre `MemcachedProperties`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `GetLoanUseCase.findById`
      Se invoca `findById` sobre `GetLoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `GetLoanUseCase.findByCustomerId`
      Se invoca `findByCustomerId` sobre `GetLoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.customerId`
      Se invoca `customerId` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `LoanValidator.validateCreateRequest`
      Se invoca `validateCreateRequest` sobre `LoanValidator`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `GetLoanUseCase.createLoan`
      Se invoca `createLoan` sobre `GetLoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.principal`
      Se invoca `principal` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.interestRate`
      Se invoca `interestRate` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.termMonths`
      Se invoca `termMonths` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.purpose`
      Se invoca `purpose` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `GetLoanUseCase.updateLoan`
      Se invoca `updateLoan` sobre `GetLoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `UpdateLoanRequest.status`
      Se invoca `status` sobre `UpdateLoanRequest`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `GetLoanUseCase.deleteById`
      Se invoca `deleteById` sobre `GetLoanUseCase`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.customerId`
      Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.principal`
      Se invoca `principal` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.interestRate`
      Se invoca `interestRate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.termMonths`
      Se invoca `termMonths` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.status`
      Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.purpose`
      Se invoca `purpose` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.createdAt`
      Se invoca `createdAt` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java` — `Loan.updatedAt`
      Se invoca `updatedAt` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.customerId`
      Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.amount`
      Se invoca `amount` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.termMonths`
      Se invoca `termMonths` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.interestRate`
      Se invoca `interestRate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.status`
      Se invoca `status` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.issueDate`
      Se invoca `issueDate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.dueDate`
      Se invoca `dueDate` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java` — `Loan.monthlyIncome`
      Se invoca `monthlyIncome` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapterTest.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapterTest.java` — `Loan.customerId`
      Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapterTest.java` — `Loan.id`
      Se invoca `id` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapterTest.java` — `Loan.customerId`
      Se invoca `customerId` sobre `Loan`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `pom.xml` — `com.google.code.simple-spring-memcached:spring-cache@4.2.0`
      com.google.code.simple-spring-memcached:spring-cache declara la version 4.2.0, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.
- [ ] `pom.xml` — `com.google.code.simple-spring-memcached:spymemcached@2.12.3`
      com.google.code.simple-spring-memcached:spymemcached declara la version 2.12.3, pero Maven Central respondio que esa version no existe. Es una version inventada: reemplazala por una version publicada real, o si no se conoce con certeza, usa el mecanismo centralizado del ecosistema (BOM/parent/platform/version catalog) y no declares una version individual.

### Presentes (19)

- `pom.xml`
- `src/main/java/com/bank/loans/LoansApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/bank/loans/domain/model/Loan.java`
- `src/main/java/com/bank/loans/domain/port/LoanRepositoryPort.java`
- `src/main/java/com/bank/loans/domain/port/CachePort.java`
- `src/main/java/com/bank/loans/infrastructure/repository/LoanJpaRepository.java`
- `src/main/java/com/bank/loans/application/usecase/GetLoanUseCase.java`
- `src/main/java/com/bank/loans/infrastructure/adapter/LoanJpaRepositoryAdapter.java`
- `src/main/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapter.java`
- `src/main/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapter.java`
- `src/main/java/com/bank/loans/infrastructure/config/RedisConfig.java`
- `src/main/java/com/bank/loans/infrastructure/config/MemcachedConfig.java`
- `src/main/java/com/bank/loans/infrastructure/cache/CacheManager.java`
- `src/main/java/com/bank/loans/infrastructure/controller/LoanController.java`
- `src/main/java/com/bank/loans/infrastructure/validator/LoanValidator.java`
- `src/test/java/com/bank/loans/application/usecase/GetLoanUseCaseTest.java`
- `src/test/java/com/bank/loans/infrastructure/adapter/RedisCacheAdapterTest.java`
- `src/test/java/com/bank/loans/infrastructure/adapter/MemcachedCacheAdapterTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/bank/loans`
- `src/main/java/com/bank/loans/domain`
- `src/main/java/com/bank/loans/domain/model`
- `src/main/java/com/bank/loans/domain/port`
- `src/main/java/com/bank/loans/application`
- `src/main/java/com/bank/loans/application/usecase`
- `src/main/java/com/bank/loans/infrastructure`
- `src/main/java/com/bank/loans/infrastructure/adapter`
- `src/main/java/com/bank/loans/infrastructure/config`
- `src/main/java/com/bank/loans/infrastructure/repository`
- `src/main/java/com/bank/loans/infrastructure/cache`
- `src/main/resources`
- `src/test/java/com/bank/loans`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con capas de dominio, aplicación e infraestructura**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Backend, Especialidad Desarrollador, Tecnología Java, Senior
- Brecha que el reto ataca: Implementa prácticas de caché utilizando al menos una herramienta como Redis o Memcached. Domina estrategias de invalidación, patrones de caché (cache-aside, write-through, write-behind), y optimización de rendimiento en aplicaciones distribuidas.
- Mision: Candidato con experiencia senior en backend, trabajando en equipos que requieren optimización de performance en sistemas de alta concurrencia.

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
