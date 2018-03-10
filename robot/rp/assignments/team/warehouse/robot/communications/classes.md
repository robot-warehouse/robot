##Robot Bluetooth classes

### The `RobotManager` class

This should be used to send/receive information from the server. Instantiate with:

```java
RobotManager manager = new RobotManager();
```

This will wait for an incoming bluetooth connection(e.g. from the server). To begin communications, call ``` manager.start()```. This will start a thread which constantly listens for commands from the server, and another thread which sends commands to the server.

To get any orders that have been sent to the server, use `manager.getOrders()`. 