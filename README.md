# hyperskill-web-quiz-engine
A simple engine for creating and solving quizzes through HTTP API.

It uses an embedded H2 database to store all data in the file system.

# Description
At this stage, the service supports creating, getting, solving and deleting quizzes

## Register a user
To register a new user, you need to send a JSON with email and password via POST request.

The service returns 200, if the registration has been completed successfully.

If the email is already taken by another user, the service will return HTTP 400.

Here are some additional restrictions to the format of user credentials:

    an email must have a valid format (with @ and .);
    password must have at least five characters.

If any of them are not satisfied, the service will also return HTTP 400.

All the following operations needs a registered user to be successfully completed.

## Create a quiz
To create a new quiz, you need to send a JSON via POST request with the following keys:

    title: string, required;
    text: string, required;
    options: an array of strings, it's required, and should contain at least 2 items;
    answer: an array of indexes of correct options, it's optional since all options can be wrong.

## Get a specific quiz
To get a specific quiz, you need to specify its id in url.
```
POST http://localhost:8889/api/quizzes/1
```
Response in JSON:
```
{"id":1,"title":"The Java Logo","text":"What is depicted on the Java logo?","options":["Robot","Tea leaf","Cup of coffee","Bug"]}
```

## Get all quizzes (with paging)
The number of stored quizzes can be very large. In this regard, obtaining all quizzes is performed page by page: 10 quizzes at once. Here is an example:
```
GET http://localhost:8889/api/quizzes
```
Response in JSON:
```
{
"totalPages":1, "totalElements":3, "last":true, "first":true, "sort":{ }, "number":0, 
"numberOfElements":3, "size":10, "empty":false, "pageable": { },
"content":[
  {"id":102,"title":"Test 1","text":"Text 1","options":["a","b","c"]},
  {"id":103,"title":"Test 2","text":"Text 2","options":["a", "b", "c", "d"]},
  {"id":202,"title":"Test 3","text":"Text 3","options":["a","b","c","d"]}
  ]
}
```
The first page is 0 since pages start from zero, just like our quizzes.

## Solve a quiz
To solve a quiz, you need to pass a JSON-array with option indices in request body
```
POST http://localhost:8889/api/quizzes/1/solve
```
The response contains a boolean value ```success``` which returns true or false, depending on the answer.
If the specified quiz does not exist, the server returns HTTP 404.

## Get all completions of quizzes (with paging)
The API has an operation to get all quiz completions of specific user. A response is separated by pages, since the service may return a lot of data.
```
GET  http://localhost:8889/api/quizzes/completed
```
Here is a response example:
```
{
  "totalPages":1,
  "totalElements":5,
  "last":true,
  "first":true,
  "empty":false,
  "content":[
    {"id":103,"completedAt":"2019-10-29T21:13:53.779542"},
    {"id":102,"completedAt":"2019-10-29T21:13:52.324993"},
    {"id":101,"completedAt":"2019-10-29T18:59:58.387267"},
    {"id":101,"completedAt":"2019-10-29T18:59:55.303268"},
    {"id":202,"completedAt":"2019-10-29T18:59:54.033801"}
  ]
}
```
Since it is allowed to solve a quiz multiple times, the response may contain duplicate quizzes, but with the different completion date. All the completions are sorted from recent to oldest.

## Delete a quiz
To delete a quiz, a creator should specify the id of the quiz in url:
```
DELETE  http://localhost:8889/api/quizzes/1
```
If the operation was successful, the service returns ```HTTP 204 (No content).```


