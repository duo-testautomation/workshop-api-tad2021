import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;


public class Escapi {

    String apikey = "f9f23ad1-04ac-43d8-8bfd-4014f7949c03";
    int sumSolution;
    Towers towersSolution;
    String solution1Header;
    String solution2Header;
    int specialistNumber;
    int escapeCode;

    @Before
    public void setup() {
        baseURI = "http://localhost:80/duo";
    }

    @Test
    public void ecapeTheApi() {
        start();
        towersGet();
        combinationLock();
        towersPost();
        toolSupport();
        escapeTheRoom();
    }

    private void start() {
        Response startCallResponse =
                given().
                        // logs the request in the console
                                log().all().
                        header("apikey", apikey).
                        when().
                        // combines the baseURI with /api and makes a GET request
                                get("/start").
                        then().
                        // logs the response in the console
                                log().all().
                        // validates that that the statuscode from the respons is 200
                                statusCode(201).
                        // checks if the body contains a gender node with value female
                                extract()
                        // this will save the response in the exampleRequest variable
                        .response();
        // extract the needed values from the response
        int number1 = startCallResponse.jsonPath().get("sum.number1");
        int number2 = startCallResponse.jsonPath().get("sum.number2");
        if (startCallResponse.jsonPath().get("divide")) {
            sumSolution = number1 / number2;
        } else if (startCallResponse.jsonPath().get("add")) {
            sumSolution = number1 + number2;
        } else if (startCallResponse.jsonPath().get("multiply")) {
            sumSolution = number1 * number2;
        } else {
            sumSolution = number1 - number2;
        }
        // prints output to the console
        System.out.println("The solution should be: " + sumSolution);
    }

    private void towersGet() {
        Response towersGetRespons =
                given().
                        log().all().
                        header("apikey", apikey).
                        when().
                        get("/towers").
                        then().
                        log().all().
                        statusCode(200).
                        extract()
                        .response();
        // create tower answer
        if (towersGetRespons.jsonPath().get("towers.alphabetic")) {
            towersSolution = new Towers(1, 0, 2, 3, 4);
        } else if (towersGetRespons.jsonPath().get("towers.height")) {
            towersSolution = new Towers(3, 1, 4, 0, 2);
        } else {
            towersSolution = new Towers(1, 4, 0, 2, 3);
        }
    }

    private void combinationLock() {
        Response combinationLockRespons =
                given().
                        log().all().
                        header("apikey", apikey).
                        queryParam("solution", sumSolution).
                 when().
                        get("/combination_lock").
                 then().
                        log().all().
                        statusCode(200).
                 extract()
                        .response();

        solution1Header = combinationLockRespons.header("solution1");
        System.out.println("The solutionHeader for puzzle 1: " + solution1Header);
    }

    private void towersPost() {
        Response towersPostResponse =
                given().
                        log().all().
                        header("apikey", apikey).
                        header("solution1", solution1Header).
                        contentType("application/json").
                        body(towersSolution).
                when().
                        post("/towers").
                then().
                        log().all().
                        statusCode(200).
                extract()
                        .response();
        if (!towersPostResponse.asString().contains("Playwright")) {
            specialistNumber = 587426; //Jurian
        } else if (!towersPostResponse.asString().contains("Postman")) {
            specialistNumber = 32843; // David
        } else if (!towersPostResponse.asString().contains("Cypress")) {
            specialistNumber = 7346337; //Reinder
        } else if (!towersPostResponse.asString().contains("Python")) {
            specialistNumber = 6278456; //Martijn
        } else {
            specialistNumber = 527786; //Jarsto
        }
        solution2Header = towersPostResponse.header("solution2");
        System.out.println("The solutionHeader for puzzle 1: " + solution2Header);
    }


    private void toolSupport() {
        Response toolsupport =
                given().
                        log().all().
                        header("apikey", apikey).
                        header("solution2", solution2Header).
                        pathParam("specialistNumber", specialistNumber).
                when().
                        get("/tool_support/call_specialist/{specialistNumber}").
                then().
                        log().all().
                        statusCode(200).
                extract()
                        .response();

        escapeCode = toolsupport.jsonPath().get("escapecode");
        System.out.println("The escapecode is: " + escapeCode);
    }

    private void escapeTheRoom() {
        given().
                log().all().
                header("apikey", apikey).
                pathParam("escapecode", escapeCode).
        when().
                delete("/remove_lock/{escapecode}").
        then().
                log().all().
                statusCode(200);
    }

}
