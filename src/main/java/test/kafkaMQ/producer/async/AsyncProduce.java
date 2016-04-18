package test.kafkaMQ.producer.async;

import java.util.*;
import java.util.concurrent.ExecutorService;
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

        Properties props = CommonUtil.getProperties(true);

        if (args != null && args.length > 0) {
        	System.out.println("WordCountTopology Linux!");
        	//产生一个ExecutorService对象，这个对象带有一个大小为poolSize的线程池，
        	//若任务数量大于poolSize，任务会被放在一个queue里顺序执行。
        	ExecutorService executor = Executors.newFixedThreadPool(Constants.poolSize);
    		for (int i = 0; i < Constants.poolSize; i++) {
    			executor.execute(new RunDemo(props));
    		}
        } else {
        	System.out.println("WordCountTopology VMware!");
        	sendDataNoThread(props);
        }

		System.out.println("AsyncProduce end!");
	}

	private static void sendDataNoThread(Properties props) {
		ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);
        Random rnd = new Random();
 
        for (long i = 0; i < Constants.producerCount; i++) {
        	KeyedMessage<String, String> data = CommonUtil.getSendData(rnd, i);
            producer.send(data);
    		System.out.println("sen success!");
        }

        producer.close();
	}
}