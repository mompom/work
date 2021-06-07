	package net.ib.paperless.spring.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.eaio.uuid.UUIDGen;
import com.google.common.primitives.Longs;

public class UUIDs {
	private UUIDs() {
	}
	/**
	 * UUID v.1
	 *
	 * @return
	 */
	public static UUID createTimeUUID() {
		com.eaio.uuid.UUID uuid = new com.eaio.uuid.UUID();
		return new UUID(uuid.time, uuid.clockSeqAndNode);
	}

	public static UUID createTimeUUID(long time) {
		com.eaio.uuid.UUID uuid = new com.eaio.uuid.UUID(time, UUIDGen.getClockSeqAndNode());
		return new UUID(uuid.time, uuid.clockSeqAndNode);
	}

	/**
	 * time_low(4bytes), time_mid(2bytes), time_hi(2bytes) 순서를 역으로 변경하여 HBase
	 * byte order가 가능하게 함
	 *
	 * @return
	 */
	public static UUID switchTimeUUID(UUID uuid) {

		long mostSignificantBits = uuid.getMostSignificantBits();
		long time = 0;

        time =  (mostSignificantBits << 48) & 0xffff000000000000L;
        time |= (mostSignificantBits >> 32) & 0x00000000ffffffffL;
        time |= (mostSignificantBits << 16) & 0x0000ffff00000000L;

		return new UUID(time, uuid.getLeastSignificantBits());
	}

	public static UUID restoreOriginalTimeUUID(UUID uuid) {

		long mostSignificantBits = uuid.getMostSignificantBits();
		long time = 0;

        time =  (mostSignificantBits >> 48) & 0x000000000000ffffL;
        time |= (mostSignificantBits << 32) & 0xffffffff00000000L;
        time |= (mostSignificantBits >> 16) & 0x00000000ffff0000L;

		return new UUID(time, uuid.getLeastSignificantBits());
	}

	public static UUID switchAndReverseTimeUUID(UUID uuid) {
		long mostSignificantBits = uuid.getMostSignificantBits();
		long time = 0;

        time =  (mostSignificantBits << 48) & 0xffff000000000000L;
        time |= (mostSignificantBits >> 32) & 0x00000000ffffffffL;
        time |= (mostSignificantBits << 16) & 0x0000ffff00000000L;

		return new UUID(time ^ MAX_ULONG, uuid.getLeastSignificantBits() ^ MAX_ULONG);
	}

	/**
	 * UUID v.3
	 *
	 * @param name
	 * @return
	 */
	public static UUID createNameUUID(byte[] name) {
		return UUID.nameUUIDFromBytes(name);
	}

	/**
	 * UUID v.4
	 *
	 * @return
	 */
	public static UUID createRandomUUID() {
		return UUID.randomUUID();
	}

	/**
	 * TimeUUID를 비교할 때 사용할 수 있는 {@link UUID} {@link Comparator} <br>
	 * Java에서 기본으로 제공하는 {@link UUID}의 compareTo메서드가 MSB, LSB 비교를 Signed Long기준으로
	 * 비교하기 때문에 TimeUUID에 적합하지 않다.
	 *
	 * @return TimeUUID 비교에 사용할 수 있는 Comparator
	 */

	public static int compare(UUID x, UUID y) {
		return (isLessThanUnsigned(x.getMostSignificantBits(),
				y.getMostSignificantBits()) ? -1 : (isGreaterThanUnsigned(
				x.getMostSignificantBits(), y.getMostSignificantBits()) ? 1
				: (isLessThanUnsigned(x.getLeastSignificantBits(),
						y.getLeastSignificantBits()) ? -1
						: (isGreaterThanUnsigned(x.getLeastSignificantBits(),
								y.getLeastSignificantBits()) ? 1 : 0))));
	}

	@SuppressWarnings("unused")
	private static final class UUIDComparator implements Comparator<UUID> {
		@Override
		public int compare(UUID o1, UUID o2) {
			return UUIDs.compare(o1, o2);
		}
	}

	private static boolean isLessThanUnsigned(long n1, long n2) {
		return (n1 < n2) ^ ((n1 < 0) != (n2 < 0));
	}

	private static boolean isGreaterThanUnsigned(long n1, long n2) {
		return (n1 > n2) ^ ((n1 < 0) != (n2 < 0));
	}

	private static final long MAX_ULONG = 0xFFFFFFFFFFFFFFFFL;

	@SuppressWarnings("unused")
	private static final byte[] reverse(byte[] original) {
		byte[] reverse = new byte[original.length];
		for (int i = 0; i < original.length; i++) {
			reverse[i] = (byte) (original[i] ^ 0xFF);
		}
		return reverse;
	}

	public static final UUID reverse(UUID uuid) {
		return new UUID(uuid.getMostSignificantBits() ^ MAX_ULONG,
				uuid.getLeastSignificantBits() ^ MAX_ULONG);
	}

	public static final UUID MAX_UUID = new UUID(MAX_ULONG, MAX_ULONG);
	public static final UUID MIN_UUID = new UUID(0, 0);
	public static final byte[] MAX_ULONG_BYTES = Longs.toByteArray(MAX_ULONG);
	public static final byte[] MIN_ULONG_BYTES = Longs.toByteArray(0);


	/**
	 * @param left
	 *            left operand
	 * @param right
	 *            right operand
	 * @return 0 if equal, < 0 if left is less than right, etc.
	 */
	@SuppressWarnings("unused")
	private static int compareTo(final byte[] left, final byte[] right) {
		return compareTo(left, 0, left.length, right, 0, right.length);
	}

	/**
	 * Lexographically compare two arrays.
	 *
	 * @param buffer1
	 *            left operand
	 * @param buffer2
	 *            right operand
	 * @param offset1
	 *            Where to start comparing in the left buffer
	 * @param offset2
	 *            Where to start comparing in the right buffer
	 * @param length1
	 *            How much to compare from the left buffer
	 * @param length2
	 *            How much to compare from the right buffer
	 * @return 0 if equal, < 0 if left is less than right, etc.
	 */
	private static int compareTo(byte[] buffer1, int offset1, int length1,
			byte[] buffer2, int offset2, int length2) {
		// Bring WritableComparator code local
		int end1 = offset1 + length1;
		int end2 = offset2 + length2;
		for (int i = offset1, j = offset2; i < end1 && j < end2; i++, j++) {
			int a = (buffer1[i] & 0xff);
			int b = (buffer2[j] & 0xff);
			if (a != b) {
				return a - b;
			}
		}
		return length1 - length2;
	}

	public static String convertTime(UUID uuid, String pattern) {

		if (uuid.version() == 1) {
			Calendar cal = GregorianCalendar.getInstance();
			long timestamp = uuid.timestamp();
			cal.setTimeInMillis((timestamp / 10000) -12219292800000L);

	    	SimpleDateFormat df = new SimpleDateFormat(pattern);
	    	String strDate = df.format(cal.getTime());

			return strDate;
		} else {
			return null;
		}
	}

	/**
	 * TimeUUID 에 대한 시간 문자열
	 * (TimeUUID가 아니면 UnsupportedException 발생)
	 *
	 * @param timeUUID
	 * @return 포멧으로 date String 리턴
	 */
	public static String convertTimeAtTimeUUID(UUID timeUUID, String pattern) throws UnsupportedOperationException {
		long timestamp = timeUUID.timestamp();
		timestamp -= 0x01B21DD213814000L;
		timestamp /= 10000;

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = new Date(timestamp);

		return sdf.format(date);
	}

	/**
	 * TimeUUID 에 대한 시간 문자열
	 *
	 * @param timeUUID
	 * @return 포멧으로 date String 리턴
	 */
	public static String convertTimeFromUUIDMsb(UUID timeUUID, String pattern) {
		long time;
		long timeMillis = timeUUID.getMostSignificantBits();

		time = (timeMillis << 48) & 0x0fff000000000000L;
		time |= (timeMillis >> 32) & 0x00000000ffffffffL;
		time |= (timeMillis << 16) & 0x0000ffff00000000L;

		time -= 0x01B21DD213814000L;
		time /= 10000;

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = new Date(time);

		return sdf.format(date);
	}

	public static boolean checkUuidStr(String strUuid) {
        Pattern pattern = Pattern.compile("[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}");
        Matcher match = pattern.matcher(strUuid);
        return match.find();
	}
}
