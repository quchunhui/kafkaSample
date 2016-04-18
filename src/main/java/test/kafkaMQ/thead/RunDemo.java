package test.kafkaMQ.thead;

import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import test.kafkaMQ.common.Constants;
import test.kafkaMQ.utilities.CommonUtil;

public class RunDemo implements Runnable {
	private Properties _pros = null;
	private Random _randrom = new Random();
	
	public RunDemo(Properties pros) {
		this._pros = pros;
	}
	
	public void run() {
		long start = System.currentTimeMillis();
		System.out.println("RumDemo start at:" + start);

		ProducerConfig config = new ProducerConfig(_pros);
        Producer<String, String> producer = new Producer<String, String>(config);

		for (long i = 0; i < Constants.producerCount; i++) {
			producer.send(CommonUtil.getSendData(_randrom, i));
		}

		producer.close();

		long end = System.currentTimeMillis();
		System.out.println("RunDemo run time:" + (end - start));
	}
}