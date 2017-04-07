# On your terminal, cd into your project folder, then:

```
	cd TestServer
	make
	make run
```		

# The server will launch and print:

```
	Waiting for client to connect...
```

# Then go to IntelliJ and run the main there.
# After the client connects to the server, the terminal witht the server will print

```
	Client connected
	Enter strings to send to client from here on:
```

# Then you must send the messages in testMessages.txt in the same order they appear
# in the file, as these follow networking protocol v1.1.

# Once you reach the first 'MAKE YOUR MOVE IN GAME A WITHIN 1.5 SECONDS:...' message,
# then you can repeat this message as many times since we're in a match.