package test.kafkaMQ.utilities;

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
}