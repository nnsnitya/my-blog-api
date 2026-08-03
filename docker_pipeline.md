### CI/CD pipeline -- network creation & deployment apps
- mvn clean package
- docker build -t my-blog-api .

#### Creating network to communicate inside docker
- docker network create blog-network

#### Connecting to the network
- docker network connect blog-network mysql-outside-use
- docker network connect blog-network my_blog_redis
- docker network connect blog-network my-blog-api

#### To run our image in same network
- docker run -d --name my-blog-api --network blog-network -p 9090:9090 -e SPRING_PROFILES_ACTIVE=dev my-blog-api:latest

### To handle(view) the docker network
- docker network ls
- docker network rm blog-network
- docker network inspect blog-network
  `//To see which containers are connected`

#### To remove network
*By remove the containers first*
- docker rm -f my-blog-api
- docker rm -f mysql-outside-use
- docker rm -f my_blog_redis
- docker network rm blog-network

*or By disconnecting containers from the network*
- docker network disconnect blog-network my-blog-api
- docker network disconnect blog-network mysql-outside-use
- docker network disconnect blog-network my_blog_redis
- docker network rm blog-network

#### debug
- jar tf target/my-blog-api.jar | grep application
- jar tf target/my-blog-api.jar | findstr application

