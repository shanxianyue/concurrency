Producer-Consumer模式
在Producer-Consumer模式中，Producer和Consumer可能都有多个。
当两者都只有一个时，我们称之为Pipe模式。

Producer-Consumer模式中的登场角色
* Data
Data角色有Producer角色生成，供Consumer角色使用。示例程序中，由String类（蛋糕）扮演该角色。
* Producer（生产者）
Producer角色生成Data角色，并将其传递给Channel角色。示例程序中，由MakerThread扮演该角色。
* Consumer（消费者）
Cconsumer角色从Channel角色获取Data角色并使用。示例程序中，由EaterThread扮演该角色。
* Channel（通道）
保管从Producer获取的Data角色，响应Consumer角色的请求，传递Data角色。
