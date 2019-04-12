运行Singleton1 Singleton2，分析结果不一样的原因
public class StaticInit{
    static{
        i++; //编译报错 因为static是按顺序执行
    }
    private static int i = 3;
}