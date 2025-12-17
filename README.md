# Introduction

The task set was to develop a credit scoring server application based on a set of requirements (described below).

The data returned from the get credit score also shows the details on how the credit score was calculated.

Adding/Setting data to the customer influences the credit score ie if the customer has missed payments on a loan the
credit score will be lowered.

## Base Requirements (taken from assignment sheet)

- Develop a Spring Boot service that evaluates customers' creditworthiness and generates dynamic credit scores.

## Extended requirements (taken from assignment sheet)

- Explainability: With the score provide clear, understandable
  explanations behind the calculations to increase transparency for
  users.
- Adaptive Modelling: Dynamically adjust credit scoring models based
  on real-world data, such as changes in market conditions, or evolving
  customer behaviours.

The extended generative AI requirement was not implemented at this time.

## Project basics

This is a Gradle with Spring Boot Project using Java 25. Source, configuration, unit and integration tests are all under
the src directory. The Rest APIs are using reactive libraries.

The project has one Rest API which queries the score using a customer id. There is also a set of Rest APIs which adds
data to the system with a reset API that deletes all the data.

The customer ID has been hardcoded to 1 - this is just for demonstration purposes.

### Set data API

The set data apis can

- Add a bank account
- Add a loan account
- Set whether the customer is registered to vote
- Set whether the customer has any convictions

### Get credit score API

The get credit score API returns

- The credit score for the customer based on the data submitted. The value is always between 0 and 1000
- The data set which the credit score was based on

## Design

There are four services that mimic the Bank account system, Loan system, Electorial system and Convictions system. These
return a raw credit score between 0 and 100 as well as data on what the score was based.

The main ScoreService then takes these individual raw scores and then applies weighting to them to calculate the final
credit
score. The weights are set in the application.properties file

org.demo.intelligentcreditscoring.weights.hasConvictionsWeight=1.0

org.demo.intelligentcreditscoring.weights.isRegisteredToVoteWeight=1.0

org.demo.intelligentcreditscoring.weights.loanWeight=4.0

org.demo.intelligentcreditscoring.weights.bankWeight=4.0

The final credit score produced will be between 0 and 1000

The final credit score and the values it was based on are all returned from the ScoreService to the Rest API.

## Technologies used

- Gradle 9.21
- Spring Boot 4.0.0
- Spring Framework 7.0.1
- AssertJ (Assertions library)
- Mockito
- H2 In Memory database
- Hibernate
- JUnit 5

## How to build

`./gradlew build`

## How to run

This will start the application on port 8080

`./gradlew bootRun`

## Testing

### Unit Test

Unit tests are executed from the IDE or command line. Test annotated with @DataJpaTest are database tests. Tests
annotated with @WebFluxTest test the Rest APIS.

### Integration Test

IntelligentCreditScoringApplicationTests.java is the only integration test and tests the Rest APIs with the in memory H2
database.

## Running the demo

The following script can be executed to run the demo

`./credit_scoring_demo.bash`

There are two options at the start

- Interactive: This will allow the user to send specific data and query the final score.
- Automatic: This runs all the APIs one after another with default data. Including the reset one at the end.

## Improvements

### Algorithm

The following improvements could be made to the algorithm

- Should missed payments on certain loans have more of a negative impact on the score? ie should a missed mortgage
  payment be more important than a standard loan payment
- if the customer has no bank account should a credit score of zero be returned?
- In terms of a bank account the numberOfTimesOverdrawn could be time-limited to a year for example
- Should a high number of loans have a negative impact on a credit score?
- Should having no loans have a negative impact on a score?
- Could a calculation based on the number of loans be used to check whether the account is going to be overdrawn?
- Add customer income: with a high income could have a positive impact on the credit score and vice versa

### Technology

The following improvements could be made

- Generative AI: The data could be fed to ChatGPT for example to calculate the credit score or to advise how to
  increase the score based on a set of data
- Docker: The application could be built as a docker image for deployment
- Document the APIs: The APIs could be documented using [Spring Rest Docs](https://spring.io/projects/spring-restdocs)
- Security on the APIs: the APIs should only be accessed by authenticated and authorised users. This should be
  implemented using [Spring Security](https://spring.io/projects/spring-security)

### Other

- End-to-end tests: [Karate API](https://www.karatelabs.io/) could be added for CI tests
- Negative tests should be added ie invalid parameters should return a error code in an http response
- Code formatter and style checker to ensure it meets code standards