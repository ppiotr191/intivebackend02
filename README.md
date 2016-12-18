# intivebackend01

Test task for intive

Running application : 
```
./mvnw spring-boot:run
```

Rest api usage :

# Actors

1) Get All actors : 
```
	curl http://localhost:8080/actors
```
2) Get actor : 
```
	curl http://localhost:8080/actors/1
```
3) Add actor :
```
	curl -i -X POST -H "Content-Type:application/json" -d '{  "firstName" : "Johnny",  "lastName" : "Depp" }' http://localhost:8080/actors
```
4) Edit Actor
```
	curl -X PUT -H "Content-Type:application/json" -d '{ "firstName": "Johnny", "lastName": "Cage" }' http://localhost:8080/actors/1
```
5) Delete Actor
```
	curl -X DELETE http://localhost:8080/actors/1
```	

# Movies

1) Get All movies : 
```
	curl http://localhost:8080/movies
```
2) Get movie :
``` 
	curl http://localhost:8080/movies/1
```
3) Add movie :
```
	curl -i -X POST -H "Content-Type:application/json" -d '{  "name" : "Zootopia",  "type" : "Animated movie" }' http://localhost:8080/movies
```
4) Edit Movie
```
	curl -X PUT -H "Content-Type:application/json" -d '{ "name": "Zootopia", "type": "Anime" }' http://localhost:8080/movies/1
```
5) Delete Actor
```
	curl -X DELETE http://localhost:8080/movies/1
```
6) Assign actor to movie
```
	curl -X POST http://localhost:8080/movies/1/add_actor/1
```	








