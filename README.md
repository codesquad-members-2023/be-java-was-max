#### java-was-2023 
# 1주차 학습계획 

### 1단계 요구사항
- [ㅇ] fork를 한다
- [] 정적인 html 파일 응답
> http://localhost:8080/index.html 로 접속했을 때 src/main/resources/templates 디렉토리의 index.html 파일을 읽어 클라이언트에 응답한다.
- [] HTTP Request 내용 출력
> 서버로 들어오는 HTTP Request의 내용을 디버거를 이용해 출력한다.
- [] 프로젝트의 동작 원리 파악
- [] 유지보수에 좋은 구조에 대해 고민하고 코드를 개선 -> 나중에 리팩토링


#### 경과
- 힌트 영상 보기 -> 막힐 때 보기
- localhost:8080에서 Hello World 뜨는것 확인

#### 미션 수행 계획
1. index.html이 뜨게 한다: http request를 보내서 -> response로 index.html을 주게 만든다? -> 'request에서 index.html을 요청'(+있는지 확인해서 보내기.... ) -> 보낸 Http requst message의 header를 읽어서 -> 거기서 또 이 요청 파일 부분을 읽어서(!!) -> 그 해당 파일을 찾아서 보내준다(!!)
2. BufferedReader.ReadLine으로 한 줄씩 읽을 수가 있다(by pobi) -> java inputstream bufferedreader 검색
3. 월요일: 오늘은 공식문서 읽는날
