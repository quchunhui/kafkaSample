package test.kafkaMQ.producer.async;

import java.util.*;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import test.kafkaMQ.common.Constants;
import test.kafkaMQ.utilities.CommonUtil;

public class AsyncProduce {
	public static void main(String[] args) {
		System.out.println("AsyncProduce start!");
		
        long events = Long.MAX_VALUE;
        Random rnd = new Random();
 
        Properties props = new Properties();
        //消费者获取消息元信息
        props.put("metadata.broker.list", CommonUtil.joinHostPort(Constants.hostList, Constants.brokerPort));
        //message的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //分区的策略，默认是取模
        props.put("partitioner.class", Constants.partitionerClass);
        //确定messages是否同步提交
		props.put("producer.type", "async");

		//消息的确认模式。异步机制中，下面的确认机制的参数就不需要设置了，即时设置了也没有用。
        //props.put("request.required.acks", "1");
 
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
 
        for (long nEvents = 0; nEvents < events; nEvents++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + rnd.nextInt(255);
            String msg = runtime + ",www.example.com," + ip;
            KeyedMessage<String, String> data = new KeyedMessage<String, String>(Constants.topic, ip, msg);
            producer.send(data);
        }

        producer.close();
    }
}
