## Plan for today:

  * 20' Server Architectures

  * 20' Multithreading

  * 10' Debugging (log4j)

  * 10' Ant

  * Before we start: There is probably not _only one_ way to build a
    system, so there is no _correct way_ to build a server. It depends
    on many things, and in this session we are going to see some ideas.

  * Try to keep everything simple!

## Client-Server Model

* Two broad classes:
    * Iterative
    * Concurrent

* Iterative Server:
    1. Wait for a request
    2. Process request
    3. Send response back
    4. Goto 1.

    > Problems?

* Concurrent Server:
    1. Wait for a request
    2. Start a new _server_ to handle the request (process, thread, task etc)
      1. Process the request
      2. Send response back
    3. Goto 1.
     
* OS-, Application-, Protocol- dependent:
  * What primitives does OS support?
  * How lightweight are they?
  * What support for synchronization?
  * TCP vs. UDP
  * Does the application need it?

* Question: What's the overhead of creating a new thread?
  * Improvement: Why not have a set of static threads around?
  * But, if n threads busy, and n + 1 request comes up?
  * Answer: you need to queue jobs as well
  * But let's see everything in detail

* Design Idea: Let's talk about 1,2,3,4 as if they were independent
  * Then, when we have context, let's discuss multithreading

## Web Server Architecture

* Students during my office hours asked a lot about the project
  architecture. Modular design so you can test independently, add on
  during MS2, and potentially use during final Project.

* One way to think of the web server is a state machine, in which, based
  on current state and existing data, we move to the next node (until a
  terminating state).

* RequestParser (might be more complex than you think -- or simpler!)
  * HTTP is a flat, stateless protocol (we add state in MS2)
  * Initial header says a lot:
    * GET/HEAD/POST..
    * Is it valid?
    * Does file exist?
    * Is file accessible?
    * HTTP version
    * more..
  * If there is any capture error code and stop

* FileParser
  * Mapping between file extentions and Type
  * Length
  * Need to send binary?

* Response (either file or error)
  * Construct headers
  * CRLF  //static final byte[] CLRF = {(byte) '\r', (byte) '\n'};
  * Readfile from input stream and write it to the output stream
    > While doing the copy, check for shutdown!
  * Send response 

* Special URLs
  * Control Panel
  * Shutdown
  * (others in MS2)

* Utilities package, with classes:
  * CLI arguments (port, root directory, optional code, return error)
    > throw errors, username etc.
  * Logging (OFF, FATAL, ERROR, WARN, INFO, DEBUG, TRACE)
    > people often use the singleton pattern
  * Configuration (shutdown, timeout, 
    > write once, read globally

* Use _Javadoc_ -- example:

`
    /***
     * It parses  command line arguments assigning  specified properties
     * to the server. Properties  include thread number, listening port,
     * debug level  and root folder.  It handles both the  short version
     * described in the project specification as well as a longer format
     * described in the README file.
     *
     * When I implemented MS2 I updated: The default to short version is
     * now to handle 3 arguments as  described in the specs. The 3rd arg
     * is the location  of the web.xml. Note that by  default, we assume
     * that it is in the servlets folder of the server's root folder.
     *
     * @param args The array of command line arguments
     * @return true if there were arguments, false if not.
     * @throws java.io.IOException
     */
`

* Sockets
  * TCP/IP API (How to access TCP)
  * TCP: Connection-oriented, Transport-layer protocol (Application, Transport, Network, Link)
  * OS handles socket details, and even OS leaves the details to the Network stack
  * Java World: import java.net..; Socket, ServerSocket
  * Example: ServerExample

  // Java pseudo-code
  Server server = new ServerSocket(port);
  Socket client = server.accept();
  client.getInputStream(); ..

## Threads

* Threads vs. Processes?
  * process independent, thread shares a context
  * process keep more information (no shared state, separate address space), threads share memory
  * process communicate via OS mechanisms, threads share state (good and bad)
  * result: an order of magnitude faster context switch

  _Obviously_ there are many design choices in between (OS-dependent)

* kernel threads vs. user (green) threads
  * Is the kernel scheduler aware of the threads?
  * Or, are threads available of the cores?
  * Kernel threads take longer to swapped
  * User threads are scheduled in user space
  * Java threads are kernel scheduled, for true parallelism

* Simplest possible model. One thread per connection
  *  Let's dive into code

* Let's talk about synchronization:
  * Shared memory between threads can cause problems
  * Race condition: when outcome depends on execution
  * Synchronization: coordination of access to avoid such problems
  * But wrong synchronization can cause deadlocks, livelocks etc.

* Deadlocks -- Coffman pinpointed 4 conditions:
  * Mutual Exclusion
  * Hold and Wait or Resource Holding
  * No Preemption
  * Circular Wait

* Next model. Shared queue of tasks, synchronized
  * threads wait() (~sleep queue on event from the OS)
  * if added, everyone receives event (more sophisticated options available)
  * Example code

* More reading
  * Yes, you are implementing a Web Server in J2ME http://www.oracle.com/technetwork/systems/index-155545.html
  * Concurrent Programming in Java, by Doug Lea, the de-facto book for concurrency in Java (and, to some extend, beyond)

# Logging

* Folks _will_ transition to the debugging phase soon

* Log4j is one logging solution, 
  * Idea: log everything into files and then study them
  * Sort of replay
  * Serializes messages

* Global logging level
  *
  
# Ant

# Software Engieering
  * Modularize, Componentize!
  * Write Tests early, ideally _before_ you write code!
  * Use logging everywhere and control it globally (for ease)!
  * Commit often!
