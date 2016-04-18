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

		long events = Constants.producerCount;
 
        Properties props = CommonUtil.getProperties(false);
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
 
        for (long nEvents = 0; nEvents < events; nEvents++) {
        	long runtime = new Date().getTime();
            String ip = "192.168.3." + nEvents;
			String msg = runtime + ",www.example.com," + ip; 
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(Constants.topic, msg);
			producer.send(data);
    		System.out.println("SyncProduce send data! ip=" + ip + " msg=" + msg);
        }

        producer.close();
    }
}
