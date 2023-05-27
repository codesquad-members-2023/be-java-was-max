# java-was-2023

Java Web Application Server 2023

## 프로젝트 정보 

### 프로그램의 전반적인 흐름(RequestHandler 클래스를 참고하시면 편합니다)
1. inputStream을 통해 http 요청을 받고 이를 Request 객체로 변환합니다.
2. 이 request 객체를 mainService에 넘겨줍니다
3. mainService에서 request를 분석하여 알맞은 service를 생성하고 필드로 할당합니다.
4. 만들어진 service에서 request를 분석하여 알맞은 메서드를 실행하고 이에 대한 결과로 response를 만들어 반환합니다.
5. 이 response는 toBytes()메서드를 이용하여 바이트의 배열로 변경하고 outputstream을 통해 응답을 전송합니다.

### 각 객체에 대한 설명
- Request: 소켓의 inputstream을 받아 http request의 정보를 담은 객체, RequestLine, RequestHeaders, Body으로 구성
  - RequestLine: method, url, protocol로 구성
  - RequestHeaders: map을 가지고 있는 객체로 header에 관한 정보를 저장
  - body: request의 body를 저장
- RequestMaker: inputStream을 입력 받아 requst 객체를 생성
- MainService: request를 입력받아 분석하여 적합한 service 객체를 생성하고 필드로 저장, service에 메세지를 보내는 역할
- Service(interface): 실질적인 비즈니스 로직을 담당하는 클래스
  - UserService(): User에 관련된 서비스(지금은 유저를 db에 저장하는 기능 밖에 없습니다)
- Response: http 응답의 정보를 담고 잇는 객체, StatusLine, ResponseHeaders, Body로 구성
  - StatusLine: response의 첫줄에 관한 정보, 리퀘스트를 어떻게 처리했는지에 관한 정보
  - ResponseHeaders: map을 가지고 있는 객체로 header에 관한 정보를 저장
  - body: 응답 내용을 bytep[]로 저장
