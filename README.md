# SNSBackofficeServer
SNS 백오피스 서버 구현 연습


<details>
<summary>조원 및 역할 배분</summary>

<br>

* 김영규
  * 초기 구성
  * 백오피스 (사용자)
* 김민성
  * ERD
  * 좋아요
  * 소셜로그인
* 김지현
  * API 명세
  * 팔로우
  * 백오피스 (게시글, 댓글)
  
</details>


<details>
<summary>필수 구현 기능</summary>

<br>

-  **사용자 인증 기능**
    - 회원가입 기능
        - username, password를 Client에서 전달받기
        - username은  `최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`로 구성되어야 한다.
        - password는  `최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자`로 구성되어야 한다.
        - DB에 중복된 username이 없다면 회원을 저장하고 Client 로 성공했다는 메시지, 상태코드 반환하기
        - 회원 권한 부여하기 (ADMIN, USER) - ADMIN 회원은 모든 게시글, 댓글 수정 / 삭제 가능            
    - 로그인 및 로그아웃 기능
        - username, password를 Client에서 전달받기
        - DB에서 username을 사용하여 저장된 회원의 유무를 확인하고 있다면 password 비교하기
        - 로그인 성공 시, 로그인에 성공한 유저의 정보와 JWT를 활용하여 토큰을 발급하고, 
        발급한 토큰을 Header에 추가하고 성공했다는 메시지, 상태코드 와 함께 Client에 반환하기
-  **프로필 관리**
    - 프로필 수정 기능
        - 이름, 한 줄 소개와 같은 기본적인 정보를 볼 수 있어야 하며 수정할 수 있어야 합니다.
        - 비밀번호 수정 시에는 비밀번호를 한 번 더 입력받는 과정이 필요합니다.
        - 최근 3번안에 사용한 비밀번호는 사용할 수 없도록 제한합니다.
-  **게시물 CRUD 기능 (배달앱일 경우 : 주문 CRUD 기능)**
    - 게시물 작성, 조회, 수정, 삭제 기능
        - 게시물 조회를 제외한 나머지 기능들은 전부 인가(Authorization) 개념이 적용되어야 하며 이는 JWT와 같은 토큰으로 검증이 되어야 할 것입니다.
        - 예컨대, 내가 작성한 글을 남이 수정하거나 삭제할 수는 없어야 하고 오로지 본인만 수정/삭제 할 수 있어야겠죠?
        - 전체 게시글 정보를 조회하는 기능도 필요합니다.
    - 배달앱일 경우 : 주문 작성, 조회, 수정, 삭제 기능
        - 배달앱의 경우 주문이 게시글이 될것이고 주문목록이 게시판이 될 것 입니다. 주문한 사람만 메뉴를 수정/삭제할 수 있어야겠죠?
-  **댓글 CRUD 기능 (배달앱일 경우 : 리뷰 CRUD 기능)**
    - 댓글 작성, 조회, 수정, 삭제 기능
        - 사용자는 게시물에 댓글을 작성할 수 있고 본인의 댓글은 수정 및 삭제를 할 수 있어야 합니다.
        - 또한, 게시물과 마찬가지로 댓글 조회를 제외한 나머지 기능들은 인가(Authorization)개념이 적용되어야 합니다.
    - 배달앱일 경우 : 리뷰 작성, 조회, 수정, 삭제 기능
        - 배달앱의 경우 주문자 들만 주문건에 대해서 리뷰를 생성 할 수 있어야 합니다.
        - 주문자만 해당 리뷰를 수정/삭제할 수 있어야 합니다.

  </details>
  
### API 명세
[SNSBackofficeServer API document(postman)](https://documenter.getpostman.com/view/30923517/2s9YeLYUeT)   
* 미리보기
![image](https://github.com/hana2set/SNSBackofficeServer/assets/97689567/85852ad0-4586-474c-ac5c-79d3692d0521)

> 참고: [postman collection 파일](https://github.com/hana2set/SNSBackofficeServer/blob/main/SNS%20%EB%B0%B1%EC%97%94%EB%93%9C%20%EC%84%9C%EB%B2%84.postman_collection.json)
    
### ERD 설계

* 설계
  
  ![erd 초기](https://github.com/hana2set/SNSBackofficeServer/assets/97689567/05697166-d4da-470a-83ec-ee41b98a520a)
  
* 작업 후

![erd 완성](https://github.com/hana2set/SNSBackofficeServer/assets/97689567/0c20b321-cd73-4501-9d93-86a720793cc9)




### 와이어프레임

![Untitled](https://github.com/hana2set/SNSBackofficeServer/assets/97689567/356e685c-2373-4e27-9926-8f884fe11c49)
