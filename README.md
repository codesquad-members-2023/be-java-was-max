#### java-was-2023 

# 학습계획 다시 

<was 만들기>
현재 상황: 진척이 너무 없어서 약간 서두를 필요가 있으므로 힌트 영상 적극적으로 참조 -> 일단 완성 코드 작성에 집중

<2단계 설계>
- 1단계에서는 line splited[1]이 반드시 경로 파일(/index.html)이 올 거라고 생각하고 코드가 그랬는데 
- index.html에서 [회원가입]누르면 자동으로 form.html으로 이동(하는 이유는 1단계에서 설정을 했기 때문에... -> form.html 보면 href = /user/form.html로 되어 있다 -> request를 또 보내는 듯)
- 힌트에 따라 아무래도 거기 회원가입 폼에 내용 채워서 버튼 누르면
/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net
(splited[1]이 이렇게 될 듯 한데) 
- (=>) < byte[] body = Files.readAllBytes(new File("src/main/resources/templates" + path).toPath()); >
이 코드에 해당하는 저런 파일 이름은 없기 때문에(에러가 날지도?) -> 일단 저거에서 기존 코드하고 구분하게 처리 필요해 보임(: if 라든가?)
- model.user 클래스에 저장한다 요구사항: (user 도메인 객체를 생성하는 것과는 무관하게 그냥 user 클래스 변수에 지정만 하면 되는 듯한)
=> setter를 만들어서 그냥 하면 될 것으로 보임 + getter를 이용한 logger 출력을 

<경과>
- 2단계 요구사항대로 User 정보를 넣은 것처럼 보임(직접 함)
