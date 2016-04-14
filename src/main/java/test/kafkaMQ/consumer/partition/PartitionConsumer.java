package test.kafkaMQ.consumer.partition;

import kafka.api.FetchRequest;
import kafka.api.FetchRequestBuilder;
import kafka.api.PartitionOffsetRequestInfo;
import kafka.common.ErrorMapping;
import kafka.common.TopicAndPartition;
import kafka.javaapi.*;
import kafka.javaapi.consumer.SimpleConsumer;
import test.kafkaMQ.common.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartitionConsumer {
	private List<String> m_replicaBrokers = new ArrayList<String>();
	
	/**
	 * PartitionConsumer
	 */
    public PartitionConsumer() {
        m_replicaBrokers = new ArrayList<String>();
    }

    /**
     * main
     * @param args
     */
	public static void main(String args[]) {
		System.out.println("PartitionConsumer start!");

		if (args.length < 1) {
			System.out.println("Please assign partition number.");
		}
		
        //最大读取数
        long maxReads = Long.MAX_VALUE;
        //端口号
		int port = 9092;
		//主题名
        String topic = Constants.topic;

        //kafka集群地址
		List<String> seeds = new ArrayList<String>();
		String hosts = Constants.hostList;
		String[] hostArr = hosts.split(",");
		for (int index = 0; index < hostArr.length; index++){
			seeds.add(hostArr[index].trim());
		}

		//运行线程来获取每个分区的数据
		PartitionConsumer example = new PartitionConsumer();
        int partLen = 2/* Integer.parseInt(args[0]) */;
		for (int index = 0; index < partLen; index++) {
	        try {
	            example.run(maxReads, topic, index, seeds, port);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	/**
	 * run
	 * @param a_maxReads
	 * @param a_topic
	 * @param a_partition
	 * @param a_seedBrokers
	 * @param a_port
	 * @throws Exception
	 */
    public void run(long a_maxReads, String a_topic, int a_partition, List<String> a_seedBrokers, int a_port) throws Exception {
    	// 如果Leader不在的时候，需要去寻找Leader
        PartitionMetadata metadata = this.findLeader(a_seedBrokers, a_port, a_topic, a_partition);
        if (metadata == null) {
            System.out.println("Can't find metadata for Topic and Partition. Exiting");
            return;
        }
        if (metadata.leader() == null) {
            System.out.println("Can't find Leader for Topic and Partition. Exiting");
            return;
        }
        //重新选举后的Leader地址
        String leadBroker = metadata.leader().host();

        //计算上次的偏移量
        String clientName = "Client_" + a_topic + "_" + a_partition;
        SimpleConsumer consumer = new SimpleConsumer(leadBroker, a_port, 100000, 64 * 1024, clientName);
        long readOffset = this.getLastOffset(consumer, a_topic, a_partition, kafka.api.OffsetRequest.EarliestTime(), clientName);
 
        //开启消费进程
        int numErrors = 0;
        while (a_maxReads > 0) {
            if (consumer == null) {
                consumer = new SimpleConsumer(leadBroker, a_port, 100000, 64 * 1024, clientName);
            }

            //发送读取请求并得到消息集
            FetchRequest req = new FetchRequestBuilder()
                    .clientId(clientName).addFetch(a_topic, a_partition, readOffset, 100000).build();
            FetchResponse fetchResponse = consumer.fetch(req);

            //请求失败处理
            if (fetchResponse.hasError()) {
                numErrors++;
                if (numErrors > 5) {
                	break;
                }

                //偏移量无效时，重新获得偏移量
                short code = fetchResponse.errorCode(a_topic, a_partition);
                if (code == ErrorMapping.OffsetOutOfRangeCode()) {
                    readOffset = getLastOffset(consumer, a_topic, a_partition, kafka.api.OffsetRequest.LatestTime(), clientName);
                }

                continue;
            }

            numErrors = 0;
        }

        if (consumer != null) {
        	consumer.close();
        }
    }
 
    /**
     * 取得上次的偏移量
     * @param consumer
     * @param topic
     * @param partition
     * @param whichTime
     * @param clientName
     * @return
     */
    public long getLastOffset(SimpleConsumer consumer, String topic, int partition, long whichTime, String clientName) {
        TopicAndPartition topicAndPartition = new TopicAndPartition(topic, partition);
        Map<TopicAndPartition, PartitionOffsetRequestInfo> requestInfo = new HashMap<TopicAndPartition, PartitionOffsetRequestInfo>();
        requestInfo.put(topicAndPartition, new PartitionOffsetRequestInfo(whichTime, 1));

        OffsetRequest request = new kafka.javaapi.OffsetRequest(
        		requestInfo, kafka.api.OffsetRequest.CurrentVersion(), clientName);
        OffsetResponse response = consumer.getOffsetsBefore(request);
 
        if (response.hasError()) {
            System.out.println("Error fetching data Offset Data the Broker. Reason: " + response.errorCode(topic, partition) );
            return 0;
        }

        long[] offsets = response.offsets(topic, partition);
        return offsets[0];
    }
 

    /**
     * Leader不在时，选举新的Leader
     * @param a_seedBrokers
     * @param a_port
     * @param a_topic
     * @param a_partition
     * @return
     */
    private PartitionMetadata findLeader(List<String> a_seedBrokers, int a_port, String a_topic, int a_partition) {
        PartitionMetadata returnMetaData = null;
        loop:
        for (String seed : a_seedBrokers) {
            SimpleConsumer consumer = null;
            try {
                consumer = new SimpleConsumer(seed, a_port, 100000, 64 * 1024, "leaderLookup");
                List<String> topics = Collections.singletonList(a_topic);
                TopicMetadataRequest req = new TopicMetadataRequest(topics);
                kafka.javaapi.TopicMetadataResponse resp = consumer.send(req);
 
                List<TopicMetadata> metaData = resp.topicsMetadata();
                for (TopicMetadata item : metaData) {
                    for (PartitionMetadata part : item.partitionsMetadata()) {
                        if (part.partitionId() == a_partition) {
                            returnMetaData = part;
                            break loop;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error communicating with Broker [" + seed + "] to find Leader for [" + a_topic
                        + ", " + a_partition + "] Reason: " + e);
            } finally {
                if (consumer != null) consumer.close();
            }
        }
        if (returnMetaData != null) {
            m_replicaBrokers.clear();
            for (kafka.cluster.Broker replica : returnMetaData.replicas()) {
                m_replicaBrokers.add(replica.host());
            }
        }
        return returnMetaData;
    }
}