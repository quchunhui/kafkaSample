package test.kafkaMQ.producer.async;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import test.kafkaMQ.thead.RunDemo;
import test.kafkaMQ.utilities.CommonUtil;

public class AsyncProduce {
	public static void main(String[] args) {
		System.out.println("AsyncProduce start!");

        Properties props = CommonUtil.getProperties(true);

        if (args != null && args.length > 0) {
    		Executor executor = Executors.newFixedThreadPool(10);
    		for (int i = 0; i < 10; i++) {
    			executor.execute(new RunDemo(props));
    		}
        } else {
        	sendDataNoThread(props);
        }

		System.out.println("AsyncProduce end!");
	}

	private static void sendDataNoThread(Properties props) {
		ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
        Random rnd = new Random();
 
        for (long i = 0; i < 10000; i++) {
        	KeyedMessage<String, String> data = CommonUtil.getSendData(rnd, i);
            producer.send(data);
        }

        producer.close();
	}
}