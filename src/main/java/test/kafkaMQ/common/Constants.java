package test.kafkaMQ.common;

public class Constants {
	public static String hostList = "192.168.93.128,192.168.93.129,192.168.93.130";
	//public static String hostList = "192.168.93.128";
	//public static String hostList = "192.168.1.36,192.168.1.37,192.168.1.38";
	public static String zookeeperPort = "2181";
	public static String brokerPort = "9092";
	public static String topic = "qchlocaltest20160414";
	public static String partitionerClass = "test.kafkaMQ.producer.partiton.SimplePartitioner";
	public static String groupId = "test_group";
	public static long ProducerMaxCount = 200001;
	public static int poolSize = 1;
}