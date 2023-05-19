import co.edu.uptc.presenter.Demo;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Demo demo = new Demo(true);
        demo.start();
        Demo demo2 = new Demo(false);
        demo2.start();
    }
}