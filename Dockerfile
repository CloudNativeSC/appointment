# JDK 21 기반 이미지 사용 (Spring Boot 3.x 호환)
FROM eclipse-temurin:21-jdk-jammy

# 컨테이너 내부 작업 디렉토리 설정
WORKDIR /app

# Gradle 빌드 결과 JAR 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

# 환경변수 설정 (기본 포트, docker-compose나 run 명령어에서 오버라이드 가능)
ENV SERVER_PORT=8083

# 컨테이너에서 노출할 포트
EXPOSE ${SERVER_PORT}

# 컨테이너 실행 시 JAR 실행 명령어
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${SERVER_PORT}"]
