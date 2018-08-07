package netty.exercise.ch08;

import netty.exercise.ch08.junit.TelnetServerConfigV2;
import netty.exercise.ch08.junit.TelnetServerV3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TelnetServerBySpring2 {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TelnetServerConfigV2.class);
        ((AnnotationConfigApplicationContext) context).registerShutdownHook();

        TelnetServerV3 server = context.getBean(TelnetServerV3.class);
        try {
            server.start();
        } catch (InterruptedException e) {
            ((AnnotationConfigApplicationContext) context).close();
        }
    }
}
