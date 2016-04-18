package test.kafkaMQ.common;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Constants implements Serializable  {
	//public static String hostList = "192.168.93.128,192.168.93.129,192.168.93.130";
	//public static String hostList = "192.168.93.128";
	public static String hostList = "192.168.1.36,192.168.1.37,192.168.1.38";
	//public static String hostList = "192.168.1.179,192.168.1.180,192.168.1.181";
	public static String zookeeperPort = "2181";
	public static String brokerPort = "9092";
	public static String topic = "qchtest20160415";
	public static String partitionerClass = "test.kafkaMQ.producer.partiton.SimplePartitioner";
	public static String groupId = "test_group_20160418";
	public static long producerCount = 100;
	public static int poolSize = 5;
}