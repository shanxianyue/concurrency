### wait和sleep的区别
1. sleep是Thread的方法，而wait是Object的方法
2. sleep不会释放锁，而wait会释放锁并把该锁放入等待锁队列
3. 使用sleep不依赖锁，而wait依赖
4. sleep不需要被唤醒，而wait需要 
