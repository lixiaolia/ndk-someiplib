# NDK-SOMEIPLIB
SOMEIP Java API.

## Environment
- Android Studio
- CMake 3.x (for boost-cmake)

## Dependencies
The following dependencies are handled with git submodules:

- vsomeip : (https://github.com/COVESA/vsomeip.git).
- boost-cmake: Used CMake adapted boost (https://github.com/Orphis/boost-cmake).

Initialize the dependencies with the following command:
```
git submodule update --init
```

## First time build

```bash
cd ndk-vsomeip
./gradlew build
```

Depending on your machine, this will take some minutes to compile the native libraries.


## Run Demo App in Android Studio
1. Run Android Studio
2. Then open ndk-vsomeip
3. Build and run
4. Expected App output in Logcat:
   
```
D/MainActivity: vsomeipBaseDir: /data/user/0/com.lxl.someipdemo/cache/vsomeip
    Os.getenv("VSOMEIP_BASE_PATH"): /data/user/0/com.lxl.someipdemo/cache/vsomeip/
D/DemoService: [onCreate] Demo Service Started.
D/DemoService: onBind()
I/.lxl.someipdem: Parsed vsomeip configuration in 0ms
    Configuration module loaded.
I/.lxl.someipdem: Initializing vsomeip application "Hello".
    Instantiating routing manager [Host].
I/.lxl.someipdem: create_local_server Routing endpoint at /data/user/0/com.lxl.someipdemo/cache/vsomeip/vsomeip-0
    Service Discovery enabled. Trying to load module.
I/.lxl.someipdem: Service Discovery module loaded.
I/.lxl.someipdem: Application(Hello, 0100) is initialized (11, 100).
    OFFER(0100): [1234.5678:0.0] (true)
I/.lxl.someipdem: Listening at /data/user/0/com.lxl.someipdemo/cache/vsomeip/vsomeip-100
I/.lxl.someipdem: REGISTER EVENT(0100): [1234.5678.2001:is_provider=true]
D/DemoService: SomeIP Service start.
I/.lxl.someipdem: Starting vsomeip application "Hello" (0100) using 2 threads I/O nice 255
W/.lxl.someipdem: Error binding NETLINK socket: Permission denied
I/.lxl.someipdem: Network interface "n/a" state changed: up
    Route "n/a" state changed: up
D/DemoClient: [onCreate] Demo Client Started.
D/DemoClient: onBind()
I/.lxl.someipdem: Configuration module loaded.
I/.lxl.someipdem: Initializing vsomeip application "World".
I/.lxl.someipdem: Instantiating routing manager [Proxy].
    Client [ffff] is connecting to [0] at /data/user/0/com.lxl.someipdemo/cache/vsomeip/vsomeip-0
I/.lxl.someipdem: udp_server_endpoint_impl: SO_RCVBUF is: 262144
I/.lxl.someipdem: udp_server_endpoint_impl: SO_RCVBUF (Multicast) is: 262144
I/.lxl.someipdem: Port configuration missing for [1234.5678]. Service is internal.
    SOME/IP routing ready.
I/.lxl.someipdem: shutdown thread id from application: 0100 (Hello) is: 7f30cee98cf0 TID: 6194
I/.lxl.someipdem: Application(World, ffff) is initialized (11, 100).
I/.lxl.someipdem: Watchdog is disabled!
    io thread id from application: 0100 (Hello) is: 7f30cf1d1cf0 TID: 6192
I/.lxl.someipdem: vSomeIP 3.1.20.2 | (default)
D/DemoClient: SomeIP Client start...
I/.lxl.someipdem: main dispatch thread id from application: 0100 (Hello) is: 7f30cef96cf0 TID: 6193
I/.lxl.someipdem: io thread id from application: 0100 (Hello) is: 7f30cdbc3cf0 TID: 6196
I/.lxl.someipdem: Starting vsomeip application "World" (ffff) using 2 threads I/O nice 255
I/.lxl.someipdem: io thread id from application: ffff (World) is: 7f30cdac5cf0 TID: 6197
I/.lxl.someipdem: Listening at /data/user/0/com.lxl.someipdemo/cache/vsomeip/vsomeip-101
    Client 101 (World) successfully connected to routing  ~> registering..
I/.lxl.someipdem: Application/Client 0101 is registering.
I/.lxl.someipdem: io thread id from application: ffff (World) is: 7f30cd7bfcf0 TID: 6200
I/.lxl.someipdem: main dispatch thread id from application: 0101 (World) is: 7f30cd9bbcf0 TID: 6198
I/.lxl.someipdem: shutdown thread id from application: 0101 (World) is: 7f30cd8bdcf0 TID: 6199
I/.lxl.someipdem: Client [100] is connecting to [101] at /data/user/0/com.lxl.someipdemo/cache/vsomeip/vsomeip-101
I/.lxl.someipdem: Application/Client 101 (World) is registered.
I/.lxl.someipdem: REGISTERED_ACK(0101)
I/.lxl.someipdem: REGISTER EVENT(0101): [1234.5678.2001:is_provider=0:reliability=3]
D/DemoClientListener: onAvailability: serviceId: 1234, instanceId: 5678, isAvailability: false
I/.lxl.someipdem: REQUEST(0101): [1234.5678:255.4294967295]
I/.lxl.someipdem: Client [101] is connecting to [100] at /data/user/0/com.lxl.someipdemo/cache/vsomeip/vsomeip-100
I/.lxl.someipdem: ON_AVAILABLE(0101): [1234.5678:0.0]
I/.lxl.someipdem: SUBSCRIBE(0101): [1234.5678.2000:ffff:0]
I/.lxl.someipdem: SUBSCRIBE ACK(0100): [1234.5678.2000.ffff]
D/DemoClientListener: onAvailability: serviceId: 1234, instanceId: 5678, isAvailability: true
I/.lxl.someipdem: Background young concurrent copying GC freed 15967(1755KB) AllocSpace objects, 1(20KB) LOS objects, 90% free, 2538KB/26MB, paused 478us total 100.694ms
D/MainActivity: onDemoServiceConnected()
    onDemoClientConnected()
I/.lxl.someipdem: vSomeIP 3.1.20.2 | (default)
I/.lxl.someipdem: vSomeIP 3.1.20.2 | (default)
D/DemoService: sendSampleEvent()
D/DemoClientListener: onMessage: serviceId: 1234, instanceId: 5678, methodId: 2001, msgBytes: 10 20 30 
D/DemoClient: sendSampleRequest()
D/DemoServiceListener: onMessage: serviceId: 1234, instanceId: 5678, methodId: 1002, clientId: 101, msgBytes: 00 01 02 03 04 05 06 07 08 09 
I/.lxl.someipdem: vSomeIP 3.1.20.2 | (default)
I/.lxl.someipdem: vSomeIP 3.1.20.2 | (default)
D/MainActivity: onStop()
D/DemoClient: onUnbind()
I/.lxl.someipdem: Stopping vsomeip application "World" (0101).
I/.lxl.someipdem: Application/Client 0101 is deregistering.
I/.lxl.someipdem: Application/Client 101 (World) is deregistered.
I/.lxl.someipdem: Client [101] is closing connection to [100]
D/DemoClient: onDestroy()
D/DemoService: onUnbind()
I/.lxl.someipdem: Stopping vsomeip application "Hello" (0100).
I/.lxl.someipdem: Client [100] is closing connection to [101]
D/DemoService: SomeIP Service stop.
D/DemoService: onDestroy()
```
