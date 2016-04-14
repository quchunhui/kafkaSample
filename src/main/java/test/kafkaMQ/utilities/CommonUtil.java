package test.kafkaMQ.utilities;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import com.fasterxml.jackson.databind.ObjectMapper;

import kafka.producer.KeyedMessage;
import test.kafkaMQ.beans.RequestBean;
import test.kafkaMQ.common.Constants;

public class CommonUtil {
	public static String joinHostPort(String hostList, String port) {
		String result = "";
		
		String[] hostArr = hostList.split(",");
		for (int index = 0; index < hostArr.length; index++){
			result += hostArr[index] + ":" + port;
			if (index != (hostArr.length - 1)) {
				result += ",";
			}
		}

		return result;
	}

	public static Properties getProperties(boolean isAsync) {
        Properties props = new Properties();

        //消费者获取消息元信息
        props.put("metadata.broker.list", CommonUtil.joinHostPort(Constants.hostList, Constants.brokerPort));
        //message的序列化类
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        //分区的策略，默认是取模
        props.put("partitioner.class", Constants.partitionerClass);
        if (isAsync) {
            //确定messages是否同步提交
    		props.put("producer.type", "async");
        } else {
    		//消息的确认模式。异步机制中，下面的确认机制的参数就不需要设置了，即时设置了也没有用。
            props.put("request.required.acks", "1");
        }

		return props;
	}

	public static KeyedMessage<String, String> getSendData(Random randrom, long i) {

		String str1 = "11" + ((randrom.nextInt(20) + 10) * 100);
		String str11 = str1.substring(0, str1.length() - 2) + "0" + (1 + randrom.nextInt(5));
		String str2 = "11" + ((randrom.nextInt(20) + 10) * 100);
		String str22 = str2.substring(0, str1.length() - 2) + "0" + (1 + randrom.nextInt(5));
		String date = RangeTimeUtil.randomDate("2016-03-01 00:00:00", "2016-03-23 00:00:00");
		String[] enterprises = {"EMS","STO","YUNDA","ZTO","ZJS","SF","YTO"};

		RequestBean ro = new RequestBean();
		ro.setLogisticProviderID(enterprises[randrom.nextInt(7)]);
		ro.setMailNo(RandomUtil.generateMixString(12));
		ro.setMailType("2");
		ro.setWeight(1.0);
		ro.setRecAreaCode("CN");
		ro.setSenName(ChineseName.getName());
		ro.setSenMobile("18567890843");
		ro.setSenPhone("8429234");
		ro.setSenProvCode("110000");
		ro.setSenCity(str1);
		ro.setSenCountyCode(str11);
		ro.setSenAddress(" 北京668)");
		ro.setRecName(ChineseName.getName());
		ro.setRecMobile("18909090808");
		ro.setRecPhone("45632134");
		ro.setRecProvCode("110000");
		ro.setRecCity(str2);
		ro.setRecCountyCode(str22);
		ro.setRecAddress("上海路 18号");
		ro.setTypeOfContents(randrom.nextInt(3) + 1 + "");
		ro.setNameOfCoutents("普通");
		ro.setMailCode("321134");
		ro.setRecDatetime(date);
		ro.setInsuranceValue("888");
		ro.setSenAreaCode("CN");
		ro.setSenCityCode(str1);
		ro.setRecCityCode(str2);
		
		String str = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			str = mapper.writeValueAsString(ro);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String msgId = i + "+" + System.currentTimeMillis();
		System.out.println("msgId=" + msgId);
		//创建KeyedMessage发送消息，参数1为topic名，参数2为分区名（若为null则随机发到一个分区），参数3为消息
        KeyedMessage<String, String> data = new KeyedMessage<String, String>(Constants.topic, str);

		return data;
	}
}