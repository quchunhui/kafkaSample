package test.kafkaMQ.consumer.group;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import test.kafkaMQ.common.Constants;
import test.kafkaMQ.utilities.CommonUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GroupConsumer extends Thread {
    private String topic;
	private ConsumerConnector consumer;
    private ExecutorService executor;

    /**
     * Main
     * @param args
     */
	public static void main(String[] args) {
		System.out.println("GroupConsumer start!");

		if(args.length < 1){
			System.out.println("Please assign partition number.");
		}
		
		//指定zookeeper的地址
        String zooKeeper = CommonUtil.joinHostPort(Constants.hostList, Constants.zookeeperPort);
        System.out.println(zooKeeper);
        //消费组的编号
        String groupId = Constants.groupId;
        //要读取的数据主题
        String topic = Constants.topic;
        //要启动的线程数
        int threads = 1/* Integer.parseInt(args[0]) */;
		System.out.println("GroupConsumer threads count=" + threads);

		GroupConsumer example = new GroupConsumer(zooKeeper, groupId, topic);
        example.run(threads);
 
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException ie) {
        }
        example.shutdown();
    }

	/**
	 * init consumer and topic
	 * @param a_zookeeper
	 * @param a_groupId
	 * @param a_topic
	 */
	public GroupConsumer(String a_zookeeper, String a_groupId, String a_topic) {
		this.consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(a_zookeeper, a_groupId));
        this.topic = a_topic;
	}

	/**
	 * create an config for consumer
	 * @param a_zookeeper
	 * @param a_groupId
	 * @return
	 */
	private static ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
        Properties props = new Properties();

        //zookeeper集群
        props.put("zookeeper.connect", a_zookeeper);
        //决定该Consumer归属的唯一组ID
        props.put("group.id", a_groupId);
        //zookeeper的心跳超时时间，查过这个时间就认为是无效的消费者
        props.put("zookeeper.session.timeout.ms", "40000");
        //zookeeper的等待连接时间
        props.put("zookeeper.sync.time.ms", "2000");
        //自动提交的时间间隔
        props.put("auto.commit.interval.ms", "1000");
 
        return new ConsumerConfig(props);
    }

	/**
	 * run method
	 * @param a_numThreads
	 */
    public void run(int a_numThreads) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(this.topic, new Integer(a_numThreads));

        //根据map获取所有的主题对应的消息流
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = this.consumer.createMessageStreams(topicCountMap);
        //获取某个主题的消息流
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(this.topic);
 
        //建立一个指定容量（a_numThreads）的线程池
        this.executor = Executors.newFixedThreadPool(a_numThreads);
 
        //开启消费者进程，读取主题下的流
        int threadNumber = 0;
        for (KafkaStream<byte[], byte[]> stream : streams) {
    		System.out.println("Consumer Start. threadNumber=" + threadNumber);
        	this.executor.submit(new ConsumerLal(stream, threadNumber));
            threadNumber++;
        }
    }

	public void shutdown() {
        if (consumer != null) {
        	consumer.shutdown();
        }

        if (executor != null) {
        	executor.shutdown();
        }

        try {
            if (!executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS)) {
                System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted during shutdown, exiting uncleanly");
        }
	}
}

class ConsumerLal implements Runnable {
    private KafkaStream<byte[], byte[]> m_stream;
    private int m_threadNumber;
 
    public ConsumerLal(KafkaStream<byte[], byte[]> a_stream, int a_threadNumber) {
        m_threadNumber = a_threadNumber;
        m_stream = a_stream;
    }
 
    public void run() {
		System.out.println("ConsumerLal Start. m_threadNumber=" + m_threadNumber);
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        while (it.hasNext()){
            System.out.println("GroupConsumer Thread " + m_threadNumber + ": " + new String(it.next().message()));
        }
        System.out.println("Shutting down Thread: " + m_threadNumber);
    }
}