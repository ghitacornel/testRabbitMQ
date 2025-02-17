package simple;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;

public class Receive {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("user");
        connectionFactory.setPassword("pass");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        };

        CancelCallback cancelCallback = (consumerTag) -> {
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
