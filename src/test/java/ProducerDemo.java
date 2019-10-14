import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.junit.Test;

@Slf4j
public class ProducerDemo {

    @Test
    public void producerTest1() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("demo_producer_group_1");
        producer.setNamesrvAddr("172.18.66.23:9876");
//        producer.setVipChannelEnabled(false);
        producer.start();
        for (int i = 0; i < 5; i++) {
            Message msg = new Message("demo_topic_1" ,"Tag_1",
                    ("Hello RocketMQ " + i + ", " + System.currentTimeMillis()).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            log.info(sendResult.toString());
        }
        producer.shutdown();

    }
}
