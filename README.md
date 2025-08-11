# _Maps_
![github](https://img.shields.io/github/stars/gadelhati/maps-back "Github")
![github all releases](https://img.shields.io/github/downloads/gadelhati/maps-back/total?label=Downloads&style=social)

![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17.5-blue?logo=postgresql)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-2025.1.1.1-000000?logo=intellijidea)
![Java](https://img.shields.io/badge/Java-17-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen?logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6.2.2-brightgreen?logo=spring)

### Necessary Tech stack:
|     Name     | Source |              File name version | Link for download                                                                                                                                                                                                                                                                           |
|:------------:|:------:|-------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|  `intellij`  |  IDE   |                  idealC-2025.2 | https://www.jetbrains.com/pt-br/idea/download/download-thanks.html?platform=windows&code=IIC                                                                                                                                                                                                |
|    `java`    |  JDK   |         jdk-17_windows-x64_bin | https://download.oracle.com/java/17/archive/jdk-17_windows-x64_bin.exe                                                                                                                                                                                                                      |
| `postgresql` |  SGBD  |  postgresql-17.5-3-windows-x64 | https://sbp.enterprisedb.com/getfile.jsp?fileid=1259622&_gl=1*1swsrzi*_gcl_au*MTQ5MTE0OTUzNC4xNzU0OTI3Nzgx*_ga*R0ExLjEuR0ExLjEuR0ExLjEuR0ExLjEuR0ExLjEuR0ExLjEuR0ExLjEuR0ExLjEuMTI0NTY5NzM4MS4xNzU0OTI3Nzgx*_ga_ND3EP1ME7G*czE3NTQ5Mjc3ODAkbzEkZzEkdDE3NTQ5MjgwNTUkajE0JGwwJGgyMTA2NzUzNjU3 |
|  `dbeaver`   |        | dbeaver-ce-25.1.4-x86_64-setup | https://dbeaver.io/files/dbeaver-ce-latest-x86_64-setup.exe                                                                                                                                                                                                                                 |

## Description
A versatile platform designed for the consolidation of georeferenced data, enabling its use in spatial analysis, monitoring, decision-making, and real-time interdisciplinary applications.

### Roadmap
#### in development
- [ ] 

#### in concept
- [ ] streaming message between api: [HTTP request] OpenFeign, [Queue] Apache Kafka ou RabbitMQ
- [ ] implements continuous integration, CI/CD with GitHub Actions
- [ ] implements dependency management

## Summary
* [How to work with this project](#how-to-work-with-this-project)
* [Configuration](#configuration)
  - [application-properties](#application-properties)
  - [JDBC URL on h2](#jdbc-url-on-h2)
  - [Dependencies](#dependencies)
* [Deploy](#deploy)
* [Links](#links)
* [Git tips](#git-tips)
* [HTTP Status code list](#http-status-code-list)
* [Developers](#developers)
* [Licence](#licence)

## How to work with this project

create this project
> [https://start.spring.io/](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.0.2&packaging=war&jvmVersion=19&groupId=br.eti.gadelha&artifactId=maps&name=maps&description=Gadelha's%20Spring%20Boot%20Project&packageName=br.eti.gadelha.maps&dependencies=lombok,h2,security,data-jpa,postgresql,actuator,validation)

type in intellij terminal tab
```
# clone the project
git clone https://github.com/gadelhati/maps-back

# install dependencies
mvn dependency:copy-dependencies

# run project
mvn spring-boot:run

# how to stop application on localhost
netstat -a -n -o
tskill "NÚMERO DO PID"

# how to create file war
mvn clean package
# ...two files with the extension .war will be created, the one with the shortest name will be used.

# how to select page on get request
{{maps-local}}/user/?page=2&size=5
{{maps-local}}/user/?sort=name,desc
```

## Configuration
### application properties
Open `src/main/resources/application.properties`

```properties
spring.application.name = maps
server.servlet.context-path = /maps

spring.datasource.url= jdbc:postgresql://localhost:5432/maps
spring.datasource.username= postgres
spring.datasource.password= password
spring.datasource.driver-class-name = org.postgresql.Driver
spring.datasource.platform = postgres
spring.datasource.initialization-mode = always

spring.jpa.show-sql = false
spring.jpa.properties.hibernate.dialect = org.hibernate.spatial.dialect.postgis.PostgisPG95Dialect
spring.jpa.properties.hibernate.default_schema = maps
spring.jpa.hibernate.ddl-auto= create

```
### JDBC URL on h2:
```
2021-04-18 21:44:01.317  INFO 7560 --- [  restartedMain] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-ui'. Database available at 'jdbc:h2:mem:testdb'
```
in case:
```
jdbc:h2:mem:testdb
```
### Dependencies
```
Configure your pom.xml file
```

## Deploy
### Deploy on Tomcat Server
Type in your linux server
```
service tomcat stop
rm /opt/tomcat/webapps/<old_version>.war
rm -Rfv /opt/tomcat/webapps/<old_version>
cp /home/<user>/<application_name>.war /opt/tomcat/webapps/
chown tomcat:tomcat /opt/tomcat/webapps/<application_name>.war
chmod 755 <application_name>
service tomcat start
```
## Links
### API download link

> [https://github.com/gadelhati/maps-back](https://github.com/gadelhati/maps-back)

### API running locally

> [http://localhost:8080/maps](http://localhost:8080/maps)
### Endpoint
These are the paths to services:
- [x] [CREATE](http://localhost:8080/maps/user) - path to item creation;
- [x] [RETRIEVE](http://localhost:8080/maps/user/id) - path to retrieve of an item by id;
- [x] [RETRIEVE](http://localhost:8080/maps/user/search) - path to retrieve of an item by search or all items without source;
- [x] [UPDATE](http://localhost:8080/maps/user/id) - path to update an item;
- [x] [DELETE](http://localhost:8080/maps/user/id) - path to delete an item;
- [x] [DELETE_ALL](http://localhost:8080/maps/user) - path to delete all items;

## Git tips

### Commit types
* feature: Um novo recurso para a aplicação, e não precisa ser algo grande, mas apenas algo que não existia antes e que a pessoa final irá acessar.
* fix: Correções de bugs
* docs: Alterações em arquivos relacionados à documentações
* style: Alterações de estilização, formatação etc
* refactor: Um codigo de refatoração, ou seja, que foi alterado, que tem uma mudança transparente para o usuário final, porém uma mudança real para a aplicação
* perf: Alterações relacionadas à performance
* test: Criação ou modificação de testes
* chore: Alterações em arquivos de configuração, build, distribuição, CI, ou qualquer outra coisa que não envolva diretamente o código da aplicação para o usuário final

type in terminal
```
# initialize git repository, create git folder
git init

# clone the project and build locally
git clone https://github.com/gadelhati/maps-back

# add all files on the staging area
git add .

# shows tracked files on the staging
git status

# packs tracked files on the staging
git commit -m "[ID]<type_of_commit>:<message>"

# shows commit history
git log

# define main branch
git branch -M main

# add remote repository, don't forget "Git Credential Manager Core"
git remote add origin https://*.git

# sends changes to the repository
git push -u origin <branch_name>

# update branch
git pull

# create new branch
git checkout -b <branch_name>

# delete a local branch
git branch -d <branch_name>

# delete a remote branch
git push --delete origin <branch_name>

# show all branch
git branch

# upload a branch
git push -u <branch_name>

# update branch
git rebase main

# upload your changes
git push -f

# list tag
git tag

# list tag by key
git tag -l "v1.8.5*"

# create a tag
git tag -a v1.4 -m "my version 1.4"

# show a tag
git show v1.4

# creating tags later
git log --pretty=oneline
git tag -a v1.2 <UUID>
```

## HTTP Status code list
> [HHTP Status Code](https://httpstatuses.com/)

## Developers
> [Gadelha TI](https://github.com/gadelhati)
> [Lucas](https://github.com/lucassmartins)
> [Augusto](https://github.com/augustmat)
> [Diego](https://github.com/diegoferreirapinto)

## Licence
> [MIT License](https://choosealicense.com/licenses/mit/)
```
MIT License

Copyright (c) 2020 Jason Watmore

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
