#### java-was-2023 

# <was 만들기>
현재 상황: 진척이 너무 없어서 약간 서두를 필요가 있으므로 힌트 영상 적극적으로 참조 -> 일단 완성 코드 작성에 집중

<3단계> 
- 테스트코드 작성 전(무엇을 테스트해야 할지 고민 중)
- 학습: MIME 타입
- localhost:8080/index.html 하면 따라오는 get요청들: /static에 있는 /js와 /css(.css와 .js 파일들) + favicon.ico 
> /css/bootstrap.min.css 
> /css/styles.css  
> /js/jquery-2.2.0.min.js
> /js/bootstrap.min.js
> /js/scripts.js
> favicon.ico
- [문제 발견]: url을 "localhost:8080"으로만 딱 치면 같은 request를 하는 쓰레드가 무려 13개 이상 콘솔에 나옴(????) 원인 모름
- 브루니의 가르침: index.html에 link된 src가 있으면 그 주소로 자동 재요청이 간다(!!!) 

<경과>
- 1번 코딩: 일단 작동이 되게(response로 해당 파일들이 왔고 favicon도 보임) 만들었는데 요구사항을 다시 보니 이게 아니었던 듯(헤더의 content-type으로 해야 할 듯한)


