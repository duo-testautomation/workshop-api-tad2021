# Masterclass API-Testing
>Welcome to our workshop API-testing. During this workshop you will escape from
an escape room, followed by exploring the API that is used by the room and finally
write a script that will solve the escape room within 5 seconds. 
After this workshop you will  know how to explore an API and how to use test automation 
to script the API. You will end up with a project that you can use for starting to test 
your own API application.

## Rest Assured 

In this workshop we will use Rest Assured to write the script for a fast escape. 
In this file the most important topics for this workshop are covered. For (a lot)
more info about Rest Assured you can visit the [Rest Assured User Guide](https://github.com/rest-assured/rest-assured/wiki/Usage).


## Start 

To solve the escape room using a script, you first have to escape manually. The URL 
where you can attempt to solve the room will be given to you at the start of the
workshop. In this starter project we will use:
 - the programming language JAVA
 - the build tool MAVEN 
 - the library Rest Assured

### How to set a baseURL
You can use the following code once at the start of your test to set a base url that will be used for every request you make:
`RestAssured.baseURI = "www.yourbaseuri.com";`

### Base Rest Assured syntax
You can use an easy to read BBD like syntax which we strongly recommend. This syntaxt consists of 3 parts.
A `given().`, a `when().` and a `then().` part. 
In the *given()* part you setup your request, this is where you can set things paramaters and headers.
In the *when()* part you make the actual request and in the *then()* part you can do the validation(s).
All parts are optional, so you dont always have to use them all.

### How to make a request to the API
The 4 most used methods are **GET, POST, PUT** and **DELETE**. 
You can make those request using the following code:
 - `get("yourUrl")`
 - `post("yourUrl")`
 - `put("yourUrl")`
 - `delete("yourUrl")`

### How to extract values, statuscode or headers from the reponse
For example, when you make a GET request to /name
and the reponse looks like this:
```json
{
  "firstName": "John",
  "lastName": "Smith",
  "age": 25
}
```
you can extract the value *firstname* like this:
```java
Response response =
when().
    get("/name").
then().
extract().
    response();

String foundFirstName = response.jsonPath().get("firstName");
```
If there is a Content-Type response header you can extract it like this:

`String headerName = response.getHeader("Content-Type");`

If you want to extract the statuscode you can do it like this:

`int statusCode = response.getStatusCode();`

## Specifying Request Data

### Path parameters
You can use path parameters like this:

```java
given().
    pathParam("hotelId", "My Hotel").
    pathParam("roomNumber", 23).
when().
    post("/reserve/{hotelId}/{roomNumber}").
then().
    // validate whatever you want
```

### Query parameters
You can use path parameters like this:

```java
given().    
       queryParam("queryParamName", "value2").
when().
       get("/something");
```
### Headers
You can use headers like this:

```java
given().
        header("MyHeader", "Something").
when().
       get("/something");
```

