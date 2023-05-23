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
> 그 요청의 첫 행에서 요청 경로를 추출한다.
> 경로에 해당하는 파일을 읽어서 응답으로 보낸다.

### RequestHandler

* 브라우저에서 서버로 요청을 보내는 모든 데이터는 InputStream에 담겨있다.
    * InputStream에서 데이터를 읽어들이는게 쉽지 않아 자바에서는 InputStream을 버퍼드리더로 변환해 한 라인씩 활용한다.
    * InputStream을 버퍼드리더로 변환하는 api는 무엇인지 확인해보자.
        * (참고) [JAVA [자바] - 입력 뜯어보기 [Scanner, InputStream, BufferedReader]](https://st-lab.tistory.com/41)
* 서버 -> 클라이언트로 보내는 모든 응답은 OutStream에 실어서 보낸다.

https://www.rfc-editor.org/rfc/rfc2616

## 리뷰 피드백(1~3단계)

### TODO : 1차 리팩토링

- [x] MediaType -> ContentType 클래스명 변경
- [x] contentType -> mimeType 필드명 변경
- [x] response200header(),responseBody() 분리 #Response 객체
- [x] parsing 메서드 분리 #Request 객체
- [x] join()에 queryMap 직접 넘기기
- [x] Request, Response 클래스명 수정하기
* 회원가입시 Is a directory 오류 해결하기
  * 파일을 저장할 때 파일이 아니라 디렉토리가 대상이 됐을 때 발생한다고 한다. 
* Connect가 계속 이뤄지는 이유 찾기 
  * 리소스를 가져올 때 마다 새로운 스레드를 생성하는 것 같다. 그런데 회원가입시에는 왜 계속 생성이 될까?



* private 메서드를 테스트 하는 것은 좋지 않다. private 메서드를 포함하고 있는 public 메서드를 테스트하면 동일한 테스트 효과가 있다.
      * 리플렉션을 활용하는 방법도 있지만 좋은 방법은 아니라고 한다.
* logger에 static 키워드를 붙여야 할까?


### 공부할 것
- [ ] return null -> 예외처리
- [ ] thread.run() VS. thread.start()
- [ ] File 클래스 // 지안 블로그 참고
