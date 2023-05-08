# 웹 서버 1단계

## WebServer.java

- 미션을 구현하기 전, 먼저 기존 코드를 해석하는 것이 우선이겠다고 생각한다.
- 특히 `args`에 대한 코드가 난해하여 도통 이해되질 않았다.
  - 이는 `String[] args`에 대하여 차근차근 접근함으로써 해결해나갈 수 있었다.

```
java WebServer "포트번호"
```

- 위와 같은 명령어를 입력하여 서버를 실행시킨다고 가정한다.

<br>

```java
int port = 0;
if (args == null || args.length == 0) {
    port = DEFAULT_PORT;
} else {
    port = Integer.parseInt(args[0]);
}
```

- `int port = 0`
  - 포트 번호를 0으로 설정한다.
- `if (args == null || args.length == 0) port = DEFAULT_PORT;`
  - `args`는 위 명령어에서 `java WebServer` 이후에 들어오는 입력들을 배열화시킨 것이라고 이해하면 된다.
  - 입력이 들어오지 않았을 때는 기본 포트 번호인 `8080`으로 지정한다.
- `else port = Integer.parseInt(args[0]);`
  - 입력 받은 포트 번호는 입력의 첫번째에 있으니 `args[0]`에 해당된다.
  - 즉, 포트 번호가 입력되면 기본 포트인 `8080`이 아닌 입력된 포트로 지정해주겠다는 의미한다.

<br>

- 처음 자바를 배울 때, `args`에 대한 것은 개념만 이해하고 넘어갔었지만 이렇게 사용할 수 있는 것을 보니 신기하다.
- 하지만 항상 포트 번호에는 `args[0]`이 저장되는 것인지, 아니면 하나만 값이 들어와서 저장되는 것인지가 궁금하다.
  - 이에 대한 궁금증은 앞으로 업데이트 할 수 있으면 좋겠다..!