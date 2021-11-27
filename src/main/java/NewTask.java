import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
    private static StringBuilder getRandomNumberOfDots() {
        int randomNum = (int) (Math.random() * 10);
        StringBuilder dots = new StringBuilder().append(".");

        for (int i=0; i < randomNum; i++ ) {
            dots.append(".");
        }

        return dots;
    }

    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            String QUEUE_NAME  = "task_queue";
            String mensagem = "Gabriel Borsero Estrela Bernardo" + getRandomNumberOfDots();

            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            // ​(exchange, routingKey, mandatory, immediate, props, byte[] body)
            String message = String.join(" ", mensagem);

            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}


