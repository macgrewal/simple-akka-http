# Simple Akka HTTP examples

These examples have been taken from [the Akka HTTP introduction](https://doc.akka.io/docs/akka-http/current/introduction.html)
documentation as of 31st July 2022.


## Updates

Some minor changes have been made:

1. Added logging dependencies to remove _some_ console warnings.
2. Removed warnings highlighted by IntelliJ
   * Added type annotations (makes it easier to read also).
   * Refactored out repeating code into `ServerOnPort8080`.
3. Renamed the `object` name for the JSON spray example
4. Added this `README.md` for my own understanding.


## Samples

### [Hello world example](src/main/scala/HttpServerRoutingMinimal.scala)

#### To run
```
sbt 'runMain com.macgrewal.HttpServerRoutingMinimal'
```

#### To test
```
curl localhost:8080/hello
```


### [JSON marshalling example](src/main/scala/HttpServerWithJsonMarshalling.scala)

#### To run
```
sbt 'runMain com.macgrewal.HttpServerWithJsonMarshalling'
```

#### To test

To insert data:
```
curl -H "Content-Type: application/json" -X POST -d '{
   "items": [
      {
         "name": "item #1",
         "id": 1
      },
      {
         "name": "item #2",
         "id": 2
      },
      {
         "name": "item #100",
         "id": 100
      }
   ]
}' localhost:8080/items
```

To retrieve data:
```
curl localhost:8080/item/1
curl localhost:8080/item/2
curl localhost:8080/item/100
```


### [Streaming example](src/main/scala/HttpServerStreamingRandomNumbers.scala)

#### To run
```
sbt 'runMain com.macgrewal.HttpServerStreamingRandomNumbers'
```

#### To test

To start streaming data:
```
curl --limit-rate 50b localhost:8080/random
```

To stop streaming data press `^C` (ctrl + c)


### [Actor integration example](src/main/scala/HttpServerWithActorInteraction.scala)

#### To run
```
sbt 'runMain com.macgrewal.HttpServerWithActorInteraction'
```

#### To test

To view submitted data (bids):
```
curl localhost:8080/auction
```

To submit data (a bid):
```
curl -X PUT "http://localhost:8080/auction?bid=99&user=jo%20bloggs"
curl -X PUT "http://localhost:8080/auction?bid=100&user=mac%20grewal"
```


## Learnings

The common thing in all examples is that each app creates:
1. an actor system
2. an execution context (to deal with mapping on futures)
3. a route to map requests to functionality
4. a server that accepts HTTP requests on port 8080
   (or whatever port you choose) and hands them off to the route 
   created in step #3


