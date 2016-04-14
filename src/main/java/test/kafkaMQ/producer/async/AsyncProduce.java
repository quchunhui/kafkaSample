package test.kafkaMQ.producer.async;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import test.kafkaMQ.common.Constants;
import test.kafkaMQ.thead.RunDemo;
import test.kafkaMQ.utilities.CommonUtil;

public class AsyncProduce {
	public static void main(String[] args) {
		System.out.println("AsyncProduce start!");

        Properties props = getProperties();

        if (args != null && args.length > 0) {
    		Executor executor = Executors.newFixedThreadPool(1);
    		for (int i = 0; i < 1; i++) {
    			executor.execute(new RunDemo(props));
    		}
        } else {
        	sendDataNoThread(props);
        }

		System.out.println("AsyncProduce end!");
	}

	private static Properties getProperties() {
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

		return props;
	}

	private static void sendDataNoThread(Properties props) {
		ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
        Random rnd = new Random();
 
        for (long i = 0; i < 1000; i++) {
        	KeyedMessage<String, String> data = CommonUtil.getSendData(rnd, i);
            producer.send(data);
        }

        producer.close();
	}
}