# CS504-running-information-analysis-service
**running-information-analysis-service** is a RESTful service in spring boot, using *[Maven](https://maven.apache.org/)* as build tool.

**Feature List:**
- Upload runningInfo
- delete all runningInfo
- delete runningInfo by runningId
- find runningInfo by runningId
- get all runningInfo with sort and pagination. Page number, page size, sort direction, and sort property can be customized.
- get all runningInfo that belong to one user. Result is sorted by timestamp in descending order (i.e. the most recent one is on top). 

## Requirements 
* Java Platform (JDK) 8
* Apache Maven
* Docker
* Docker Compose 

## Installation and Quick Start
#### 1. Download project file
```
git clone https://github.com/xujiahaha/CS504-running-information-analysis-service.git
cd CS504-running-information-analysis-service
```
#### 2. Use Docker to run MySQL server
> Configure MySQL image version in `docker-compose.yml` (Default version is 5.6). Run following code in terminal.
```
docker-compose up -d
```
#### 3. Login MySQL database inside the Docker container
> Run `docker ps` to check the container name. Replace the `containerName` with your container name in following code before running it. The default password for root user is `rootpassword`. 

```
docker exec -ti containerName mysql -uroot -p
```
> Create table RUNNING_ANALYSIS in database runningInfoAnalysis_db if not exist
```
mysql> SHOW DATABASES;
mysql> USE runningInfoAnalysis_db;
mysql> SHOW TABLES;
mysql> CREATE TABLE RUNNING_ANALYSIS (id BIGINT(20) AUTO_INCREMENT, runningId VARCHAR(50), latitude DOUBLE, longitude DOUBLE, runningDistance DOUBLE, totalRunningTime DOUBLE, timestamp TIMESTAMP, healthWarningLevel INT, heartRate INT, userName VARCHAR(30), userAddress VARCHAR(50), PRIMARY KEY(id));
mysql> EXIT;
```
#### 4. Build and run Spring Boot application
> Change directory to `/your/path/CS504-running-information-analysis-service` before running the following commands.
```
mvn clean install
java -jar ./target/running-information-analysis-service-1.0.0.BUILD-SNAPSHOT.jar
```
#### 5. Upload runningInfo.json data
> Run the command uhder the directory `/your/path/CS504-running-information-analysis-service`.
```
curl -H "Content-Type: application/json" localhost:8080/runningInfo -d @runningInfo.json
```

## API Overview
> Check API References for detailed information

| Method | URL | Description | 
|--------|--------|-------------|
| POST | /runningInfo | upload a list of runningInfo | 
| DELETE | runningInfo/purge | delete all runningInfo | 
| DELETE | runningInfo/{runningId} | delete one runningInfo by runningId |
| GET | /runningInfo/{runningId} | get one runningInfo by runningId | 
| GET | /runningInfo | get all runningInfo with pagination and sort | 
| GET | /{username}/runningInfo | get one user's runningInfo with pagination (sort by timestamp) | 


## API References
### 1. Upload runningInfo
&nbsp;&nbsp;&nbsp;&nbsp;URL: `/runningInfo`

&nbsp;&nbsp;&nbsp;&nbsp;HTTP Method: **POST**

&nbsp;&nbsp;&nbsp;&nbsp;Success Response: 201 (CREATE) 

Data Example:
```
[
  {
    "runningId": "7c08973d-bed4-4cbd-9c28-9282a02a6032",
    "latitude": "38.9093216",
    "longitude": "-77.0036435",
    "runningDistance": "39492",
    "totalRunningTime": "2139.25",
    "heartRate": 0,
    "timestamp": "2017-04-01T18:50:35Z",
    "userInfo": {
      "username": "ross0",
      "address": "504 CS Street, Mountain View, CA 88888"
    }
  }
]
```

### 2. Delete all runningInfo 
&nbsp;&nbsp;&nbsp;&nbsp;URL: `/runningInfo/purge`

&nbsp;&nbsp;&nbsp;&nbsp;HTTP Method: **DELETE** 

&nbsp;&nbsp;&nbsp;&nbsp;Success Response: 200 (OK) 

### 3. Delete runningInfo by runningId
&nbsp;&nbsp;&nbsp;&nbsp;URL: `/runningInfo/{runningId}`

&nbsp;&nbsp;&nbsp;&nbsp;URL Params: runningId **(required)** 

&nbsp;&nbsp;&nbsp;&nbsp;HTTP Method: **DELETE** 

&nbsp;&nbsp;&nbsp;&nbsp;Success Response: 200 (OK) 

Example: 
```
/runningInfo/fb0b4725-ac25-4812-b425-d43a18c958bb
```

### 4. Find runningInfo by runningId

&nbsp;&nbsp;&nbsp;&nbsp;URL: `/runningInfo/{runningId}`

&nbsp;&nbsp;&nbsp;&nbsp;Required URL Params: runningId

&nbsp;&nbsp;&nbsp;&nbsp;HTTP Method: **GET** 

&nbsp;&nbsp;&nbsp;&nbsp;Success Response: 200 (OK)

&nbsp;&nbsp;&nbsp;&nbsp;Error Response: 404 (Not Found)

Example: 
```
/runningInfo/07e8db69-99f2-4fe2-b65a-52fbbdf8c32c
```

### 5. Find all runningInfo with sort and pagination
&nbsp;&nbsp;&nbsp;&nbsp;URL: `/runningInfo`

&nbsp;&nbsp;&nbsp;&nbsp;HTTP Method: **GET** 

&nbsp;&nbsp;&nbsp;&nbsp;Success Response: 200 (OK)

Optional URL Params: <br />

| Param | Optional | Description | Default Value | Example |
|--------|--------|-------------|---------------|---------|
| page | yes | set page number | 0 | page=1|
| size | yes | set page size | 2 | size=5 |
| sortDir | yes | set sort direction (asc or desc) | desc | sortDir=asc |
| sortBy | yes | set sort property | heartRate | sortBy=totalRunningTime |

Example: 
```
/runningInfo
/runningInfo?page=1
/runningInfo?page=1&size=4
/runningInfo?page=1&size=4&sortDir=asc
/runningInfo?page=1&size=4&sortDir=asc&sortBy=totalRunningTime
```

Sample Response:
```
[
    {
        "runningId": "07e8db69-99f2-4fe2-b65a-52fbbdf8c32c",
        "timestamp": "2017-06-15T05:02:13Z",
        "totalRunningTime": 3011.23,
        "heartRate": 198,
        "userName": "ross1",
        "userAddress": "504 CS Street, Mountain View, CA 88888",
        "healthWarningLevel": "HIGH"
    },
    {
        "runningId": "fb0b4725-ac25-4812-b425-d43a18c958bb",
        "timestamp": "2017-06-15T05:02:13Z",
        "totalRunningTime": 123,
        "heartRate": 189,
        "userName": "ross4",
        "userAddress": "504 CS Street, Mountain View, CA 88888",
        "healthWarningLevel": "HIGH"
    }
]
```

### 6. Find all runningInfo belongs to one user with most recent one on top
&nbsp;&nbsp;&nbsp;&nbsp;URL: `/{username}/runningInfo`

&nbsp;&nbsp;&nbsp;&nbsp;HTTP Method: **GET** 

&nbsp;&nbsp;&nbsp;&nbsp;Required URL Params: runningId

&nbsp;&nbsp;&nbsp;&nbsp;Success Response: 200 (OK)

Optional URL Params: <br />

| Param | Optional | Description | Default Value | Example |
|--------|--------|-------------|---------------|---------|
| page | yes | set page number | 0 | page=1|
| size | yes | set page size | 2 | size=5 |

Example: 
```
/ross2/runningInfo
/ross2//runningInfo?page=1
/ross2//runningInfo?page=1&size=4
```

Sample Response:
```
[
    {
        "runningId": "2f3c321b-d239-43d6-8fe0-c035ecdff232",
        "timestamp": "2017-06-15T05:02:13Z",
        "totalRunningTime": 85560.23,
        "heartRate": 129,
        "userName": "ross2",
        "userAddress": "504 CS Street, Mountain View, CA 88888",
        "healthWarningLevel": "HIGH"
    },
    {
        "runningId": "2f3c321b-d239-43d6-8fe0-c035ecdff231",
        "timestamp": "2017-06-15T05:01:41Z",
        "totalRunningTime": 85431.23,
        "heartRate": 179,
        "userName": "ross2",
        "userAddress": "504 CS Street, Mountain View, CA 88888",
        "healthWarningLevel": "HIGH"
    }
]
```

 


