# 개요
안드로이드 학습을 위해 개발된 Android 앱 입니다.
  * tmdb api를 사용하여 데이터를 가져와 보여주고 로컬 데이터 베이스를 활용해 즐겨찾기 추가 기능이 구현되어있습니다.
  * MVVM, Clean Architecture, Multi-Module 구조와 nowinandroid 프로젝트의 구조를 참고하여 개발되었습니다.


# 기술 스택 및 라이브러리
* **Kotlin**
  * 안드로이드 공식 프로그래밍 언어
* **Coroutines**
  * 비동기 작업을 간편하게 처리할 수 있는 경량 스레드 모델, 비동기 코드를 순차적으로 작성할 때 사용
* **Hilt**
  * 안드로이드 전용 의존성 주입 라이브러리
* **Flow**
  * 값을 방출하는 비동기 데이터 스트림
* **StateFlow**
  * 초기값, 마지막 값을 보유한 중복된 데이터를 발행하지 않는 데이터 스트림, 화면에 표시할 데이터로 사용
* **SharedFlow**
  * 초기값, 마지막 값을 보유하지 않고 중복된 데이터를 발행하는 스트림, 화면에 발생될 이벤트에 사용
* **Recrofit2, OkHttp3**
  * REST API를 통한 비동기 네트워크 처리에 활용
* **Gson**
  * json 직렬화/역직렬화 라이브러리, 네트워크 요청, 응답을 파싱할 때 사용
* **ViewBinding**
  * XML 레이아웃을 코드에서 간편하게 참조하기 위해 사용
* **AAC-ViewModel**
  * 회면 회전 시 데이터가 유실되지 않도록 하며 View에 표시할 데이터 및 상태 관리에 사용
* **AAC-Navigation**
  * 화면 이동 처리를 위해 사용
* **AAC-Paging3**
  * 여러 페이지로 되어있는 데이터를 효율적으로 요청 및 리스트에 보여주기 위해 사용
* **AAC-Room**
  * 쿼리 작성 없이 인터페이스를 통해 데이터 베이스를 조작
* **Glide**
  * 이미지 로딩 및 캐싱 처리 라이브러리


# 아키텍처

* **MVVM:**
  * 데이터(Model)와, 화면(View)을 분리하고 둘 사이를 ViewModel로 UI와 비즈니스 로직을 분리
* **Clean Architecture:**
  * 관심사에 따라 계층 구조로 분리하여, 확장성과 유지보수성 증가
* **Multi-Module:**
  * 각 모듈에 대한 독립적인 개발 및 관리 가능, 역할의 분배가 용이해지며 증분 빌드로 빌드 시간 단축 가능

**개발 환경**
* Android Studio, kotlin


**프로젝트 구조**
* **app:** 메인 애플리케이션 모듈
  * feature 모듈에 대한 네비게이션 호스트 역할
 
    
* **core:data:** 데이터 관련 로직 (Repository, DataSource)
  * 로컬 데이터베이스, api 호출 등의 데이터 처리 역할
* **core:domain:** 비즈니스 로직 (UseCase)
  * 타 모듈들과의 의존성이 없으며 비즈니스 로직(usecase, repository-interface) 역할
* **core:ui:** UI 관련 로직 (Activity, Fragment, ViewModel)
  * 공통 UI 레이아웃 파일, 공통 UI 이벤트 리스너, 공통 어댑터 등이 포함된 feature에서 사용할 UI의 베이스 역할
* **core:navigation** 네비게이션 로직
  * 딥링크를 통한 기능 모듈간의 전환하는 네비게이션 역할
* **feature:home**
  * 현재 트렌드 컨텐츠와 인기 컨텐츠를 보여주고, 시작 화면에 사용
* **feature:discover**
  * 페이징 처리 된 항목들을 전체 컨텐츠를 보여주는 화면
* **feature:search**
  * 페이징 처리 된 검색 결과를 보여주는 화면
* **feature:favorites**
  * 즐겨찾기 추가 된 컨텐츠를 보여주는 화면
* **feature:detail**
  * 특정 컨텐츠의 상세 정보를 보여주는 화면
