# MSA (Spring Cloud)


### MSA - Docker 구성
#### Bridge network 생성
    1. network 생성
	> docker network create --gateway 172.10.0.1 --subnet 172.10.0.0/16 msa-network

	aa75e483ef0658e4daf7c284f4fc08e80ebaff1a8a2ec8474790a7355da6bf71
    ----------------------------------------------------------------------

    2. network 확인
	> docker network inspect msa-network
	[
		{
			"Name": "msa-network",
			"Id": "aa75e483ef0658e4daf7c284f4fc08e80ebaff1a8a2ec8474790a7355da6bf71",
			"Created": "2024-10-02T01:34:52.110282602Z",
			"Scope": "local",
			"Driver": "bridge",
			"EnableIPv6": false,
			"IPAM": {
				"Driver": "default",
				"Options": {},
				"Config": [
					{
						"Subnet": "172.10.0.0/16",
						"Gateway": "172.10.0.1"
					}
				]
			},
			"Internal": false,
			"Attachable": false,
			"Ingress": false,
			"ConfigFrom": {
				"Network": ""
			},
			"ConfigOnly": false,
			"Containers": {},
			"Options": {},
			"Labels": {}
		}
	]

#### Rabbitmq 설치
	1) 설치
	c:\>docker run -d --name rabbitmq --network msa-network -p 15672:15672 -p 5672:5672 -p 15671:15671 -p 5671:5671 -p 4369:4369 -
	e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest rabbitmq:management
	
    -----------------
	Unable to find image 'rabbitmq:management' locally
	management: Pulling from library/rabbitmq
	8666b435d033: Download complete
	45842602427a: Download complete
	dafa2b0c44d2: Download complete
	2ad525572aae: Download complete
	dc90a98e5e04: Download complete
	0ae94e6aef35: Download complete
	953a6cb82879: Download complete
	37a05209e70f: Download complete
	c01adf267ddc: Download complete
	14495391f6cb: Download complete
	Digest: sha256:5e94ed8046a7ff9d44cf2bb9e41a6331353ed3fc92b3c7881237efbba16afbbb
	Status: Downloaded newer image for rabbitmq:management
	c7e51bba50f49724f3523a344ac4ea7bb11c859e70c975ee84e651ac9b5300b9
    -----------------

	2) 확인
	c:\>docker ps
    -----------------
	CONTAINER ID   IMAGE                 COMMAND                   CREATED       STATUS       PORTS
	c7e51bba50f4   rabbitmq:management   "docker-entrypoint.s…"   2 hours ago   Up 2 hours   0.0.0.0:4369->4369/tcp, 0.0.0.0:5671-5672->5671-5672/tcp, 15691-15692/tcp, 0.0.0.0:15671-15672->15671-15672/tcp, 25672/tcp   rabbitmq	
    -----------------

	접속 : http://127.0.0.1:15672   guest/guest

#### Config-Service 설치
	1) Dockerfile
		FROM openjdk:17-ea-11-jdk-slim
		VOLUME /tmp
		COPY apiEncryptKey.jks	apiEncryptKey.jks
		COPY target/config-service-1.0.jar ConfigService.jar
		ENTRYPOINT ["java","-jar","ConfigService.jar"]

	2) image build 
		docker build -t domsamo/config-service:1.0 .

	3) hub site에 push
		docker push domsamo/config-service:1.0	

	3) 실행
		docker run -d -p 8888:8888 --network msa-network \
 		-e "spring.rabbitmq.host=rabbitmq" \
		-e "spring.profiles.active=default" \
  		--name config-service domsamo/config-service:1.0

#### Discovery-Serivce 설치
	1) Dockerfile
		FROM openjdk:17-ea-11-jdk-slim
		VOLUME /tmp
		COPY target/discoveryservice-1.0.jar DiscoveryService.jar
		ENTRYPOINT ["java","-jar","DiscoveryService.jar"]

	2) image build 
		docker build -t domsamo/discovery-service:1.0 .

	3) hub site에 push
		docker push domsamo/discovery-service:1.0	

	4) 실행
		docker run -d -p 8761:8761 --network msa-network \
		-e "spring.cloud.config.uri=http://config-service:8888" \
		--name discovery-service domsamo/discovery-service:1.0

	5) 확인
		http://127.0.0.1:8761

#### mysql 설치
    1. Dockerfile 생성
        -----------------
        FROM mysql:8.1.0
        ENV MYSQL_ROOT_PASSWORD=1q2w3e4r!
        ENV MYSQL_DATABASE=msadb
        ENV MYSQL_USER=fbc
        ENV MYSQL_PASSWORD=1q2w3e4r!
        ENV character-set-server=utf8
        ENV collation-server=utf8_general_ci
        ENV default-character-set=utf8
        ENV default-collation=utf8_general_ci
        EXPOSE 3306
        -----------------
    2. Docker build
        docker build . -t domsamo/fbc-mysql:1.0
    3. Docker mysql 실행
        docker run -d -p 3306:3306 --network msa-network --name mysql domsamo/fbc-mysql:1.0
    4. root 권한 추가
        docker exec -it mysql /bin/bash
        -----------------
        mysql -uroot -p
        grant all privileges on *.* to 'root'@'%';
        flush privileges;
        -----------------

#### Kafka 설치
    https://github.com/wurstmeister/kafka-docker 참조
    
    1. kafka project git clone
        git clone https://github.com/wurstmeister/kafka-docker.git

    2. kafka-docker/docker-compose-single-broker.yml 파일 수정
        -----------------
        version: '3'
        services:
          zookeeper:
            image: wurstmeister/zookeeper
            ports:
              - "2181:2181"
            networks:
              msa-network:
                ipv4_address: 172.10.0.100
          kafka:
          # build: .
            image: wurstmeister/kafka
            ports:
              - "9092:9092"
            environment:
              KAFKA_ADVERTISED_HOST_NAME: 172.10.0.101
              KAFKA_CREATE_TOPICS: "test:1:1"
              KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
            volumes:
              - /var/run/docker.sock:/var/run/docker.sock
            depends_on:
              - zookeeper
            networks:
              msa-network:
                ipv4_address: 172.10.0.101
        networks:
          msa-network:
            external: true
            name: msa-network
        -----------------
        3. docker-compose 실행
            kafka-docker>docker-compose -f docker-compose-single-broker.yml up -d

#### Zipkin 서버 실행
    docker run -d -p 9411:9411 --network msa-network --name zipkin openzipkin/zipkin

#### Prometheus
    https://hub.docker.com/u/prom 참조
    -----------------
    docker rn -d -p 9090:9090 \
    --network msa-netowrk --name prometheus \
    -v /path/to/prometheus.yml:/etc/prometheus/prometheus.yml \
    prom/prometheus

#### Grafana
    https://grafana.com/grafana/download?pg=get&plcmt=selfmanaged-box1-cta1&platform=docker
    -----------------
    docker run -d -p 3000:3000 \
    --network msa-netowrk --name grafana \
    grafana/grafana


## Config Serivce

### 대칭 암호화 (Symeetric Encryption)
    bootstrap.yal
    - encrypt.key 설정

    POST : confi-service-url/encrypt
    POST : confi-service-url/decrypt


### 비대칭 암호화
    1. KeyStore 생성(Private-key)
        c:\Temp\keystore>keytool -genkeypair -alias apiEncryptionKey -keyalg RSA -dname "CN=domsamo, OU=API Developtment, O=domsamo.co
        .kr, L=Seoul, C=KR" -keypass "test1234" -keystore apiEncryptionKey.jks -storepass "test1234"
        
        Warning:
        JKS 키 저장소는 고유 형식을 사용합니다. "keytool -importkeystore -srckeystore apiEncryptionKey.jks -destkeystore apiEncryptionKey.jks -deststoretype pkcs12"를 사용하는 산업 표준 형식인 PKCS12로 이전하는 것이 좋습니다.
        
        * 정보 확인  
        c:\Temp\keystore>keytool -list -keystore apiEncryptionKey.jks -v
        키 저장소 비밀번호 입력:
        ==================================================================
        키 저장소 유형: JKS
        키 저장소 제공자: SUN
        
        키 저장소에 1개의 항목이 포함되어 있습니다.
        
        별칭 이름: apiencryptionkey
        생성 날짜: 2024. 11. 8
        항목 유형: PrivateKeyEntry
        인증서 체인 길이: 1
        인증서[1]:
        소유자: CN=domsamo, OU=API Developtment, O=domsamo.co.kr, L=Seoul, C=KR
        발행자: CN=domsamo, OU=API Developtment, O=domsamo.co.kr, L=Seoul, C=KR
        일련 번호: 752280b0
        적합한 시작 날짜: Fri Nov 08 14:19:47 KST 2024 종료 날짜: Thu Feb 06 14:19:47 KST 2025
        인증서 지문:
        SHA1: BE:3F:BB:98:31:9B:FA:40:BA:33:4F:08:60:82:E2:2D:0B:1F:DD:3C
        SHA256: 1E:BD:AC:EC:EE:D3:CC:20:2E:E4:9C:62:78:2A:CD:F5:4F:BD:C5:8D:3B:1A:B1:66:E1:40:98:F5:AB:83:6E:B0
        서명 알고리즘 이름: SHA256withRSA
        주체 공용 키 알고리즘: 2048비트 RSA 키
        버전: 3
        
        확장:
        
        #1: ObjectId: 2.5.29.14 Criticality=false
        SubjectKeyIdentifier [
        KeyIdentifier [
        0000: 0C 64 3E 20 2B 1A EE 70   42 C7 8E D1 D9 CF 46 14  .d> +..pB.....F.
        0010: F4 5A DA AF                                        .Z..
        ]
        ]
                
        *******************************************
        *******************************************
        Warning:
        JKS 키 저장소는 고유 형식을 사용합니다. "keytool -importkeystore -srckeystore apiEncryptionKey.jks -destkeystore apiEncryptionKey.jks -deststoretype pkcs12"를 사용하는 산업 표준 형식인 PKCS12로 이전하는 것이 좋습니다.
        ==================================================================
    
    2. 인증서 생성
        c:\Temp\keystore>keytool -export -alias apiEncryptionKey -keystore apiEncryptionKey.jks -rfc -file trustServer.cer
        키 저장소 비밀번호 입력:
        ==================================================================
        인증서가 <trustServer.cer> 파일에 저장되었습니다.
    
    3. 공개키 생성(Public key)
        c:\Temp\keystore>keytool -import -alias trustServer -file trustServer.cer -keystore publicKey.jks
        키 저장소 비밀번호 입력:
        새 비밀번호 다시 입력:
        ==================================================================
        
        소유자: CN=domsamo, OU=API Developtment, O=domsamo.co.kr, L=Seoul, C=KR
        발행자: CN=domsamo, OU=API Developtment, O=domsamo.co.kr, L=Seoul, C=KR
        일련 번호: 752280b0
        적합한 시작 날짜: Fri Nov 08 14:19:47 KST 2024 종료 날짜: Thu Feb 06 14:19:47 KST 2025
        인증서 지문:
        SHA1: BE:3F:BB:98:31:9B:FA:40:BA:33:4F:08:60:82:E2:2D:0B:1F:DD:3C
        SHA256: 1E:BD:AC:EC:EE:D3:CC:20:2E:E4:9C:62:78:2A:CD:F5:4F:BD:C5:8D:3B:1A:B1:66:E1:40:98:F5:AB:83:6E:B0
        서명 알고리즘 이름: SHA256withRSA
        주체 공용 키 알고리즘: 2048비트 RSA 키
        버전: 3
        
        확장:
        
        #1: ObjectId: 2.5.29.14 Criticality=false
        SubjectKeyIdentifier [
        KeyIdentifier [
        0000: 0C 64 3E 20 2B 1A EE 70   42 C7 8E D1 D9 CF 46 14  .d> +..pB.....F.
        0010: F4 5A DA AF                                        .Z..
        ]
        ]
        
        이 인증서를 신뢰합니까? [아니오]:  y
        인증서가 키 저장소에 추가되었습니다.
        ==================================================================
    4. bootstrap.yal
        - encrypt.key-store 설정

    5. user-service.yml
        POST : confi-service-url/encrypt
        POST : confi-service-url/decrypt

### Kafka

#### Console 테스트(Zookeeper + Kafka Standalone)

    1. Zookeeper server 시작
        c:\Dev\kafka_2.13-3.8.0>.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
    2. Kafka server 시작
        c:\Dev\kafka_2.13-3.8.0>.\bin\windows\kafka-server-start.bat .\config\server.properties
    3. Topic 생성
        c:\Dev\kafka_2.13-3.8.0>.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic quickstart-events --partitions 1
    4. Topic 리스트 확인
        c:\Dev\kafka_2.13-3.8.0>.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list
        ==================================================================
        __consumer_offsets
        connect-configs
        connect-offsets
        connect-status
        quickstart-events
        ==================================================================
    5. Topic 정보 확인        
        c:\Dev\kafka_2.13-3.8.0>.\bin\windows\kafka-topics.bat --describe --topic quickstart-events --bootstrap-server localhost:9092
    6. Producer 실행
        c:\Dev\kafka_2.13-3.8.0>.\bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic quickstart-events
    7. Consumer 실행
        c:\Dev\kafka_2.13-3.8.0>.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic quickstart-events --from-beginning

#### Kafka Connect
    1. download
        http://packages.confluent.io/archive/6.1/confluent-community-6.1.0.tar.gz
    2. log4j 설정경로 변경(.\bin\windows\connect-distributed.bat)
        set KAFKA_LOG4J_OPTS=-Dlog4j.configuration=file:%BASE_DIR%/config/tools-log4j.properties
    ->    set KAFKA_LOG4J_OPTS=-Dlog4j.configuration=file:%BASE_DIR%/etc/kafka/connect-log4j.properties
        
    2. 실행
        c:\Dev\confluent-6.1.0>.\bin\windows\connect-distributed.bat .\etc\kafka\connect-distributed.properties


#### JDBC Connector
    1. download
    https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc
    2. Kafka Connect plugin 정보 추가 (etc/kafka/connnect-distributed.properties)
        plugin.path=\c:\\Dev\\confluentinc-kafka-connect-jdbc-10.7.6\\lib
    3. Kafka Connect classpath 추가(.\bin\windows\kafka-run-class.bat)
        ----------------------------
        rem Classpath addition for LSB style path
        if exist %BASE_DIR%\share\java\kafka\* (
        call :concat %BASE_DIR%\share\java\kafka\*
        )
        
        rem Classpath addition for release
        -----------------------------
    4. mysql jar 복사
        .\share\java\kafka\mysql-connector-j-8.0.32.jar

 ### Kafka Source Connect 테스트
    1. Source Connect 등록
        echo '
        {
            "name" : "my-source-connect",
            "config" : {
                "connector.class" : "io.confluent.connect.jdbc.JdbcSourceConnector",
                "connection.url":"jdbc:mysql://localhost:3306/msadb",
                "connection.user":"root",
                "connection.password":"1q2w3e4r!",
                "mode": "incrementing",
                "incrementing.column.name" : "id",
                "table.whitelist":"users",
                "topic.prefix" : "my_topic_",
                "tasks.max" : "1"
            }
        }' | curl -X POST -d @- http://localhost:8083/connectors --header "content-Type:application/json"

    2. Kafka Connect 목록확인
        - curl http://localhost:8083/connectors | jq
    3. Kafka Connect 목록확인
        - curl http://localhost:8083/connectors/my-source-connect/status | jq
    4. Topic 목록 확인
        c:\Dev\kafka_2.13-3.8.0>.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list
    5. Kafka Source Connector 테스트
        - mysql user 테이블에 insert query 실행
        insert into users(user_id, name) values ('test2', 'TEST ADMIN');
        - console에서 consumer를 실행하여 모니터링
        c:\Dev\kafka_2.13-3.8.0>.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic my_topic_users --from-beginning
        ---------------------
        {"schema":{
            "type":"struct",
            "fields":[
                {"type":"int32","optional":false,"field":"id"},
                {"type":"string","optional":true,"field":"user_id"},
                {"type":"string","optional":true,"field":"pwd"},
                {"type":"string","optional":true,"field":"name"},
                {"type":"int64","optional":false,"name":"org.apache.kafka.connect.data.Timestamp","version":1,"field":"created_at"}
            ],
            "optional":false,
            "name":"users"},
        "payload":{"id":2,"user_id":"admin","pwd":"0","name":"Administrator","created_at":1732598179000}}
        ---------------------
## Order Service
### Kafka Sink Connector 등록
    {
        "name" : "my-order-sink-connect",
        "config" : {
            "connector.class" : "io.confluent.connect.jdbc.JdbcSinkConnector",
            "connection.url":"jdbc:mysql://localhost:3306/msadb",
            "connection.user":"root",
            "connection.password":"1q2w3e4r!",
            "auto.create": "true",
            "auto.evolve": "true",
            "delete.enabled" : "false",
            "tasks.max" : "1",
            "topics" : "orders"
        }
    }


connector 목록 조회
curl -X GET "http://localhost:8083/connectors/"
connector 상세 정보 조회
curl -X GET "http://localhost:8083/connectors?expand=status&expand=info"
connector config 조회
GET 으로도 동일하게 동작함
curl -X PUT "http://localhost:8083/connectors/{connector_name}/config
특정 connector 상태 조회
curl -X GET "http://localhost:8083/connectors/{connector_name}/status"
connector 재시작
※ task는 재시작되지 않음
curl -X POST "http://localhost:8083/connectors/{connector_name}/restart"
connector 일시중지 (pause)
비동기 방식이므로 상태 조회시 바로 PAUSE 를 리턴하지 않을 수 있음
curl -X PUT "http://localhost:8083/connectors/{connector_name}/pause"
connector 복귀 (resume)
pause 상태인 connector 를 복귀시킨다.
비동기 방식이므로 상태 조회시 바로 RUNNING을 리턴하지 않을 수 있음
connector 삭제
curl -X DELETE "http://localhost:8083/connectors/{connector_name}
Task
connector의 task 목록 조회
curl -X GET "http://localhost:8083/connectors/{connector_name}/tasks"
connector 의 task 상태 조회
curl -X GET "http://localhost:8083/connectors/{connector_name}/tasks/{task_id}/status"
connector 의 task 재시작
※ connector 가 RUNNING, task 가 FAIL 일 경우 사용
curl -X POST "http://localhost:8083/connectors/{connector_name}/tasks/{task_id}/restart"
Topic
connector topic 조회
curl -X GET "http://localhost:8083/connectors/{connector_name}/topics"
connector topic reset
curl -X PUT "http://localhost:8083/connectors/{connector_name}/topics/reset"