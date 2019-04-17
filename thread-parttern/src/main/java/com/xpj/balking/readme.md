Balking设计模式：
多个线程监控某个共享变量，A线程监控到共享变量发生变化后即将出发某个动作，
但是此时发现另一个线程B已经针对该变量的变化开始了行动，因此线程A放弃了准备开始的工作。

Balking模式中有以下角色：
* GuardedObject(被防护的对象)
GuardedObject角色是一个拥有被防护的方法(guardedMethod)的类。
当线程执行guardedMethod方法时，若守护条件成立，则执行实际的处理。
而当守护条件不成立时，则不执行实际的处理，直接返回。守护条件的成立与否，会随着GuardedObject角色的状态变化而变化。
除了guardedMethod外，GuardedObject角色还可能有改变状态的方法(stateChangingMethod)。

示例中，Data就是一个GuardedObject。save方法对应的是guardedMethod，change方法对应的是stateChangingMethod。守护条件是“changed字段为true”

可使用Balking模式的情况：
* 并不需要执行时
* 不需要等待守护条件成立时
    守护条件不成立时，想要立即返回并进行下一个操作，就可以使用Balking模式
* 守护条件仅在第一次成立时
    例：只执行一次初始化处理的类。
balk结果的表示方式：
* 忽略balk 
    直接返回，不做任何操作。示例中采用就是此种方式
* 通过返回值来表示balk
* 通过异常来表示balk的发生