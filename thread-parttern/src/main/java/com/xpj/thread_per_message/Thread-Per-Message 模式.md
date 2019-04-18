Thread-Per-Message模式：为每个命令或请求新分配一个线程。

Thread-Per-Message模式中的角色：
* Client(委托人)：Client角色会想Host角色发出请求。示例中，Main扮演该角色
* Host：Host角色收到请求，会新创建一个线程。新创建的线程将使用Helper角色来
        处理请求。示例中，Host扮演该角色。
* Helper：实际处理请求的角色。示例中，Helper扮演该角色。

Thread-Per-Message模式优点与适用场景：
* 提高响应性，缩短延迟时间
 （由于启动新的线程也会花费时间，所以想要提高响应性时，
  是否使用Thread-Per-Message模式取决于“handle操作花费的时间”
  和“线程启动花费时间”之间的均衡）
* 适用于操作顺序没有要求时
* 适用于不需要返回值时，如果需要获取操作结果，可以使用Future模式
