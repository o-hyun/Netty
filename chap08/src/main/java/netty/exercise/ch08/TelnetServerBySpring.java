package netty.exercise.ch08;

import netty.exercise.ch08.spring.TelnetServerV2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TelnetServerBySpring {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(TelnetServerV2.class);
        ((AnnotationConfigApplicationContext) context).registerShutdownHook();

        TelnetServerV2 server = context.getBean(TelnetServerV2.class);
        try {
            server.start();
        } catch (InterruptedException e) {
            ((AnnotationConfigApplicationContext) context).close();
        }
    }
}
