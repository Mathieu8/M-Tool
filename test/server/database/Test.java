package server.database;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

//import org.junit.Test;
//import static org.junit.Assert.*;
//import org.apache.commons.codec.digest.DigestUtils;

public class Test {

	public static void main(String[] args) throws Exception {
		LocalDateTime dt = LocalDateTime.now();
		StringBuilder dateTime = new StringBuilder( ToStringBuilder.reflectionToString(dt, ToStringStyle.SHORT_PREFIX_STYLE));
		String stringDateTime = dateTime.substring(dateTime.indexOf("[")+1, dateTime.indexOf("]"));;
		System.out.println(stringDateTime);
		System.out.println(dt);
		System.out.println(dt.toString());
		
		
	}
}
 