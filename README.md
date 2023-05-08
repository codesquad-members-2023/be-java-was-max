# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
---
첫 번째로 해야 하는건?   
먼저 브라우저에서 보내는 요청이 index.html이란 걸 알아야지 서버에서 index.html이 요청을 통해서 파일을 읽고 응답으로 보낼 수 있다.   
-> 아하. url을 입력했을 때 해당 파일을 읽어 응담을 보낸다는 말   
=> 이 부분을 구현해야 한다!   

> 사용자가 보내는 모든 요청(HTTP request message)를 모두 읽는다.
그 요청의 첫 행에서 요청 경로를 추출한다.
경로에 해당하는 파일을 읽어서 응답으로 보낸다.

### RequestHandler
* 브라우저에서 서버로 요청을 보내는 모든 데이터는 InputStream에 담겨있다.
  * InputStream에서 데이터를 읽어들이는게 쉽지 않아 자바에서는 InputStream을 버퍼드리더로 변환해 한 라인씩 활용한다.
  * InputStream을 버퍼드리더로 변환하는 api는 무엇인지 확인해보자.
    * (참고) [JAVA [자바] - 입력 뜯어보기 [Scanner, InputStream, BufferedReader]](https://st-lab.tistory.com/41)
* 서버 -> 클라이언트로 보내는 모든 응답은 OutStream에 실어서 보낸다.

https://www.rfc-editor.org/rfc/rfc2616