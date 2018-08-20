package measurements;

import java.time.LocalDateTime;

/**
 * A simple example of Measurements class, later it should be increased to a
 * complete Measurements.
 * 
 * Most set-methods should also auto increase their counter here. 
 * 
 * @author Mathieu
 * @version 08/07/2018
 *
 */
public class Measurements implements BasicMeasurements {
	private int UID;
	private LocalDateTime BeginDateTime;
	long beginTime, endTime, duration;
	String emotion;
	int emotionCounter;
	int intesitity;
	int intesitityCounter;

	public Measurements(int UID, LocalDateTime dt) {
		this.UID = UID;
		this.BeginDateTime = dt;
		beginTime = System.currentTimeMillis();
	}

	public void setDuraction() {
		endTime = System.currentTimeMillis();
		duration = endTime - beginTime;
	}

	@Override
	public String getTableName() {
		return "meetresultaat";
	}

	@Override
	public String[] getFields() {
		String[] temp = { "dateTime", "duration", "emotion", "emotionCounter", "intesitity", "intesitityCounter" };
		return temp;
	}

	@Override
	public String[] getData() {
		String[] temp = { BeginDateTime.toString(), "" + duration, emotion, "" + emotionCounter, "" + intesitity,
				"" + intesitityCounter };
		return temp;
	}

	public LocalDateTime getBeginDateTime() {
		return BeginDateTime;
	}

	public void setBeginDateTime(LocalDateTime beginDateTime) {
		BeginDateTime = beginDateTime;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
		emotionCounter++;
		System.out.println("in measurements " + this.emotion);

	}

	public int getIntesitity() {
		return intesitity;
	}

	public void setIntesitity(int intesitity) {
		intesitityCounter++;
		this.intesitity = intesitity;
	}

	public int getUID() {
		return UID;
	}

	public void setUID(int uID) {
		UID = uID;
	}

	public String getDt() {
		return BeginDateTime.toString();
	}

	public void setDt(LocalDateTime dt) {
		this.BeginDateTime = dt;
	}

}