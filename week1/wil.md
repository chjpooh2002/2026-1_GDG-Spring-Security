## 인증 / 인가

우리가 보안을 생각할 때는 크게 2가지 단계를 거쳐 판단한다.

- **인증(Authentication)**: 이 데이터가 DB에 있는지 검증하는 절차  
- **인가(Authorization)**: 인증된 이후, 해당 사용자가 접근할 수 있는 권한이 있는지 판단

예를 들어, 관리자 페이지는 승인된 몇 명의 관리자만 접근할 수 있으므로  
인가 절차에서 관리자 외의 접근을 차단한다.

---

## 서블릿 필터 (Servlet Filter)

Spring Security는 **서블릿 필터**라는 기술을 사용한다.

서블릿 필터란  
요청(Request)과 응답(Response)을 가로채서 전처리/후처리를 수행하는 장치이다.

예:
- 요청 로깅
- 인코딩 설정

흐름: HTTP 요청 → Filter Chain → 인증 → 인가 → Controller

- 대부분의 처리는 **Filter에서 수행**
- Controller에 도달하기 전에 이미 "접근 가능 여부"가 결정됨

---

## DTO를 Record 타입으로 설계할 때의 이점

- 생성자, getter, equals, hashCode, toString이 자동 생성되어 코드가 간결하다
- 불변 객체(Immutable)이기 때문에 값이 한 번 생성되면 변경되지 않는다  
  → Spring에서 DTO는 요청/응답 전달용이므로 안정성이 높다
- 선언 자체로 "데이터 전달 객체"라는 의도가 명확하여 협업에 유리하다
- getter 방식이 기존 `getUsername()`이 아니라 `username()` 형태로 변경된다