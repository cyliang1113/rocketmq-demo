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
        DefaultMQProducer producer = new DefaultMQProducer("producer_group_demo_1");
        producer.setNamesrvAddr("ant01:9876");
//        producer.setVipChannelEnabled(false);
        producer.start();
        for (int i = 0; i < 5; i++) {
            Message msg = new Message("topic_demo_1" ,"AAbb",
                    ("Hello RocketMQ " + i + ", " + System.currentTimeMillis()).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            log.info(sendResult.toString());
        }
        producer.shutdown();

    }
}
