package test.kafkaMQ.producer.sync;

import java.util.*;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import test.kafkaMQ.common.Constants;
import test.kafkaMQ.utilities.CommonUtil;

public class SyncProduce {
	public static void main(String[] args) {
		System.out.println("SyncProduce start!");

		long events = Constants.ProducerMaxCount;
 
        Properties props = new Properties();
        //消费者获取消息元信息
        props.put("metadata.broker.list", CommonUtil.joinHostPort(Constants.hostList, Constants.brokerPort));
        //message的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //分区的策略，默认是取模
        props.put("partitioner.class", Constants.partitionerClass);
		//消息的确认模式
        props.put("request.required.acks", "1");

        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
 
        for (long nEvents = 0; nEvents < events; nEvents++) {
        	long runtime = new Date().getTime();  
            String ip = "192.168.3." + nEvents;
			String msg = runtime + ",www.example.com," + ip; 
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(Constants.topic, ip, msg);
    		System.out.println("SyncProduce send data! ip=" + ip + " msg=" + msg);
			producer.send(data);
        }

        producer.close();
    }
}
