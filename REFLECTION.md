# Lab 2 Reflection Questions

## Q1 - Validation: @Valid on DTO vs Entity

**DTO (Data Transfer Object):**
- Used at the HTTP boundary
- Controls what clients can send
- HTTP validation rules

**Entity:**
- Used for DB invariants
- Controls what gets persisted
- Database constraints

Separation of concerns they diverge over time

## Q2 - Location Header on 201 Created

```
HTTP/1.1 201 Created
Location: /products/42
```

Mandated by RFC 9110 (HTTP Semantics). Tells the client where the new resource lives without a second round-trip.

## Q3 - @ControllerAdvice vs @ExceptionHandler

**@ExceptionHandler:**
- Inside one controller
- Local scope only

**@ControllerAdvice:**
- Separate class
- Global applies to all controllers
- Local wins over global for same exception type

## Q4 - @Transactional on Test Class

**With @Transactional:**
- Each test rolls back
- Database resets automatically between tests
- Test isolation guaranteed

**Without it:**
- Writes commit
- Tests become order-dependent and flaky

## Q5 - RFC 9457  Problem Details

```json
{ 
  "type": "…/not-found",
  "title": "Not found",
  "status": 404,
  "detail": "Product 99 missing" 
}
```

Machine readable type URI + human detail. Clients can branch on the type. Spring Boot 3+ uses this by default via ProblemDetail.

## Q6 - MockMvc vs Mockito

**Mockito (unit test):**
- One class, all deps mocked
- Fast
- Tests logic branches

**MockMvc (integration test):**
- Full MVC stack
- Tests HTTP contract, validation, serialization

Use both  they cover different failure modes
