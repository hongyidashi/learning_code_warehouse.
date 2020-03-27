package java.lang;

/**
 * @Auther: panhongtong
 * @Date: 2020/3/27 11:09
 * @Description: 验证双亲委派机制，沙箱安全
 */
public class String {
    public static void main(String[] args) {
        System.out.println("验证双亲委派机制");
//        错误: 在类 java.lang.String 中找不到 main 方法, 请将 main 方法定义为:
//        public static void main(String[] args)
//        否则 JavaFX 应用程序类必须扩展javafx.application.Application
    }
}
