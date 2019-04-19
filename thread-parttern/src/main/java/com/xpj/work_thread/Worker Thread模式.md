Worker Thread模式：工人线程会逐个取回工作并进行处理。所有工作全部完成后，工人线程会等待新的工作到来。

Worker Thread模式中的角色：
* Client（委托者）：创建表示工作请求的Request角色并将其传递给Channel角色。示例中，由ClientThread扮演此角色。
* Channel（通信线路）：Channel角色接收来自于Client的Request，并将其传递给Worker。实例中，由Channel扮演此角色。
* Worker（工人）：Worker从Channel中不停获取Request，并进行工作。示例中，有WorkerThread扮演此角色。
* Request（请求）：表示工作的角色。