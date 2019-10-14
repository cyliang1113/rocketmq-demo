import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class ConsumerDemo {

    @Test
    public void consumerTest1() throws Exception {

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("demo_consumer_group_2");

        // Specify name server addresses.
        consumer.setNamesrvAddr("172.18.66.23:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("demo_topic_1", "*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                synchronized (this) {
                    log.info(Thread.currentThread().getName() + ", " + msgs);
                    for (MessageExt msg : msgs) {
                        byte[] body = msg.getBody();
                        String s = new String(body);
                        log.info(s);
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        //Launch the consumer instance.
        consumer.start();
        log.info("Consumer Started.");
        LockSupport.park();
    }
}
