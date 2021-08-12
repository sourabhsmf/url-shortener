# URL Shortener 
@Build Badges@

[![Java CI with Maven](https://github.com/sourabhsmf/url-shortener/actions/workflows/maven.yml/badge.svg?branch=master)](https://github.com/sourabhsmf/url-shortener/actions/workflows/maven.yml)

[![Docker](https://github.com/sourabhsmf/url-shortener/actions/workflows/docker-publish.yml/badge.svg?branch=master)](https://github.com/sourabhsmf/url-shortener/actions/workflows/docker-publish.yml)
## Generates a shortened version of the url supplied. 
## PS - No redirects

### maven build 
> `mvn package`
### Run jar artifact using 
> `java -jar urlshortenerapp.jar`

### Docker build image locally 
> `docker build . -t urlshortenerapp:latest`

### Docker pull
> `docker pull ghcr.io/sourabhsmf/url-shortener:master`

### Run docker container
> `docker run -p 8080:8080 --name app urlshortenerapp:latest`

### Swagger at
> http://localhost:8080/swagger-ui/

