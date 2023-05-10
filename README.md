# HallymFestival2023 -Backend-

<div align="center">
    <img width="329" alt="image" src="https://avatars.githubusercontent.com/u/122857055?s=400&u=386f18f709e3269a0f8fa4078f62f53320576dfd&v=4">

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FVoluntain-SKKU%2FHallymFestival2023-Backend-&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)

</div>

# Hallym Spring Festival Web Application v0.01
> **한림대학교 대동제** <br/> **개발기간: 2023.03 ~ 2023.05** <br/> 
> **운영기간: 2023.05.09 ~ 2023.05.18**

## Deployment url

> **개발 버전** : [https://hallymfestival.com](https://hallymfestival.com/) <br>

## Back-End Team ✨

|                                   이동헌                                     |                                       박주영                                        |             김미진                                                                  |                                                                                     
|:--------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------:|
| <img width="160px" src="https://avatars.githubusercontent.com/u/80760160?v=4" /> | <img width="160px" src="https://avatars.githubusercontent.com/u/52206904?v=4" /> | <img width="160px" src="https://avatars.githubusercontent.com/u/112682489?v=4"/> |
|                 [@Sirius506775](https://github.com/Sirius506775)                 |                      [@mythpoy](https://github.com/mythpoy)                      |                    [@mijin0721](https://github.com/mijin0721)                    |
|                                한림대학교 빅데이터전공  4학년                                 |   한림대학교 콘텐츠IT전공 3학년   |                                 한림대학교 빅데이터전공 4학년                                 | 
|                                항상 잠이 부족한 팀장                                 |  능력 좋은 부팀장   |                                성실의 아이콘 최고의 팀원                                  | 

## Description



| ![KakaoTalk_20230508_181533057](https://user-images.githubusercontent.com/80760160/237024960-e6d4f2f6-1b5d-4bf1-b273-11d95a31937b.png) | ![KakaoTalk_20230508_181533057_03](https://user-images.githubusercontent.com/80760160/237025873-e8691847-ae49-44b2-8df3-4846b4c7e0be.png) | ![KakaoTalk_20230508_181533057_04](https://user-images.githubusercontent.com/80760160/237025874-dc4ddf98-db27-4cbe-bbf1-bf0fc2ad4f8d.png) |                                                                                     
|:-----------------------------------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------:|
| ![KakaoTalk_20230508_181533057_05](https://user-images.githubusercontent.com/80760160/237025876-8196f939-bfbc-4b17-a871-099d45f55e56.png) | ![KakaoTalk_20230508_181533057_02](https://user-images.githubusercontent.com/80760160/237025869-b3a1af4b-51ea-4faa-96dd-0202429c89ab.png) | ![KakaoTalk_20230508_181533057_06](https://user-images.githubusercontent.com/80760160/237025881-97e4c110-3ba4-461f-8957-198d98427f0c.png) | 


    
## Stacks 🐈

### Environment
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)


### Development
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Naver Cloud Platform](https://img.shields.io/badge/Naver%20Cloud%20Platform-%2303C75A.svg?style=for-the-badge&logo=NAVER&logoColor=white)

## Dependencies
- Spring Data JPA
- Spring Security
- lombok
- Log4J2
- Querydsl
- Thumbnailator
- Gson
- Json Web Token
- Opencsv
- Jackson-databind
- Modelmapper

## Package Structure
```
├── com.hallym.festival 
│ ├── domain 
│ |    ├── booth
│ |    |    ├── controller
│ |    |    ├── dto
│ |    |    ├── entity
│ |    |    ├── repository
│ |    |    └── service
| |    |
| |    ├── comment
| |    ├── commentReport
| |    ├── likes
| |    ├── menu
| |    ├── notice
| |    ├── totalvisit
| |    ├── users
| |    ├── visitComment
| |    └── visitCommentReport
| ├── grobal
| |    ├── baseEntity
│ |    ├── config 
│ |    ├── exception
│ |    ├── ip
│ |    └── sercurity
└── FestivalApplciation.java
```
## System Architecture
![System architecture](https://github.com/Hallym-LIKELION/HallymFestival2023-Backend-/assets/80760160/9f078aa7-ecc9-48bc-ad0b-6e4340145f26)


## Compatible Device Specifications
저희 축제페이지 기기 사양은 다음과 같습니다. 

- Android: Android 7 이상 (2017 Galaxy S8 이상)
- iPhone: iOS 14 이상 (2015 iPhone 6s 이상)
- Windows: Windows 7 이상
- Mac:OS X El Capitan (2015) 이상

> 브라우저는 각 OS에서 최신 버전 사용을 권장하며, 아이폰 안드로이드 웹 모두 크롬 환경을 권장합니다.
