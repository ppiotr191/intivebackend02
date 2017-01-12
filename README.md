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
# Users

1) Get All Users
```
	curl http://localhost:8080/users
```
2) Get user
```
	curl http://localhost:8080/users/1
```
3) Get user movies
```
	curl http://localhost:8080/users/1/movies
```
4) Add user
```
curl -i -X POST -H "Content-Type:application/json" -d '{  "firstName" : "Jan",  "lastName" : "Kowalski" }' http://localhost:8080/users
```
5) Add to cart (first numbers is movie id, second - amount of movies)
```
curl -i -X POST -H "Content-Type:application/json" -d '{ "1" : "1",  "2": "3", "3" : "5" }' http://localhost:8080/users/1/add_to_cart
```
6) Remove from cart (first numbers is movie id, second - amount of movies)
```
curl -i -X DELETE -H "Content-Type:application/json" -d '{ "1" : "1",  "2": "3", "3" : "5" }' http://localhost:8080/users/1/remove_from_cart
```
7) Get movies from user cart
```
curl http://localhost:8080/users/1/movies_in_cart
```
8)  Finalize order (return price in response)
```
curl http://localhost:8080/users/1/finalize_order
```
9) Return movies (first numbers is movie id, second - amount of movies)
```
curl -i -X POST -H "Content-Type:application/json" -d '{ "1" : "1",  "2": "3", "3" : "5" }' http://localhost:8080/users/1/return_movies
```

# Movies

1) Get All movies : 
```
	curl http://localhost:8080/movies
```
2) Get All movies with specific category (id : 1 - newest, id : 2 - hits, id : 3 - others ) :
```
	curl http://localhost:8080/movies/category/1
```
3) Get All available movies :
```
	curl http://localhost:8080/movies/available
```

4) Get movie :
``` 
	curl http://localhost:8080/movies/1
```
5) Add movie with specific category (Path variable : id : 1 - newest, id : 2 - hits, id : 3 - others ):
```
	curl -i -X POST -H "Content-Type:application/json" -d '{  "name" : "Zootopia",  "type" : "Animated movie", "price" : 20.00, "amount" : 10 }' http://localhost:8080/movies/category/1
```
6) Edit Movie
```
	curl -X PUT -H "Content-Type:application/json" -d '{ "name": "Zootopia", "type": "Anime", "price" : 10.00, "amount" : 20 }' http://localhost:8080/movies/1
```
7) Delete Movie
```
	curl -X DELETE http://localhost:8080/movies/1
```
8) Assign actor to movie
```
	curl -X POST http://localhost:8080/movies/1/add_actor/1
```	








