package net.ib.paperless.spring.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;


public class DateUtil {

	/**
	 * <pre>
	 *    현재 날짜를 yyyyMMdd 형식으로 반환한다.
	 * </pre>
	 *
	 * @param None
	 * @return String yyyyMMdd 형식의 현재날짜
	 */
	public static String getCurrentDateString() {
		return getCurrentDateString("yyyyMMdd");
	}

	/**
	 * <pre>
	 *    현재 시각을  HHmmss 형식으로 반환한다.
	 * </pre>
	 *
	 * @param None
	 * @return String HHmmss 형식의 현재 시각
	 */
	public static String getCurrentTimeString() {
		return getCurrentDateString("HHmmss");
	}

	/**
	 * <pre>
	 *    현재날짜를 주어진 pattern 에 따라 반환한다.
	 * </pre>
	 *
	 * @param pattern SimpleDateFormat 에 적용할 pattern
	 * @return String pattern 형식의 현재날짜
	 */
	public static String getCurrentDateString(String pattern) {
		return convertToString(getCurrentTimeStamp(), pattern);
	}

	/**
	 * <pre>
	 * java.util.Date를 주어진 패턴의 스트링 타입으로 변환한다.
	 * </pre>
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(java.util.Date date, String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * <pre>
	 * yyyyMMdd 형식의 스트링을 java.util.Date 타입으로 변환한다. 변환 실패시 null을 리턴.
	 * </pre>
	 *
	 * @param yyyyMMdd
	 * @return
	 * @throws ParseException 
	 */
	public static java.util.Date parseDate(String yyyyMMdd) throws ParseException {
		if (yyyyMMdd == null || yyyyMMdd.length() != 8)
			return null;
		return DateUtils.parseDate(yyyyMMdd, new String[] { "yyyyMMdd" });
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 날짜를 yyyy/MM/dd 형식으로 반환한다.
	 * </pre>
	 *
	 * @param dateData yyyyMMdd 형식의 날짜
	 * @return String yyyy/MM/dd 형식의 해당 날짜 
	 */
	public static String convertFormat(String dateData) {
		return convertFormat(dateData, "yyyy/MM/dd");
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 날짜를 yyyy/MM/dd 형식으로 반환한다.
	 * </pre>
	 *
	 * @param dateData yyyyMMdd 형식의 날짜
	 * @param format SimpleDateFormat 에 적용할 pattern
	 * @return String pattern 형식의 해당 날짜 
	 */

	public static String convertFormat(String dateData, String format) {
		return convertToString(convertToTimestamp(dateData), format);
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 날짜를 yyyy/MM/dd 형식으로 반환한다.
	 * </pre>
	 *
	 * @param None
	 * @return Timestamp 현재 Timestamp 값
	 * @exception Exception
	 */

	public static Timestamp getCurrentTimeStamp() {
		Calendar cal = new GregorianCalendar();
		Timestamp result = new Timestamp(cal.getTime().getTime());
		return result;
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 날짜를 yyyy/MM/dd 형식으로 반환한다.
	 * </pre>
	 *
	 * @param None
	 * @return Timestamp 현재 Timestamp 값
	 */
	public static String getCurrentTime(String timeZone, String formant) {
		TimeZone tz;
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(formant);
		tz = TimeZone.getTimeZone(timeZone);
		df.setTimeZone(tz);
		return df.format(date);
	}

	/**
	 * 
	 * @param timeZone
	 * @return
	 */
	public static String getCurrentTime(String timeZone) {
		return getCurrentTime(timeZone, "yyyyMMddHHmmss");
	}

	/**
	 * 
	 * @param dateData
	 * @param toFormat
	 * @return
	 * @throws Exception
	 */
	public static String convertFormatU(String dateData, String toFormat) {
		String yearString = dateData.substring(6, 10);
		String monthString = dateData.substring(0, 2);
		String dayString = dateData.substring(3, 5);
		String time = "000000";
		dateData = yearString + monthString + dayString + time;
		return convertToString(convertToTimestamp(dateData), toFormat);
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 Timestamp 날짜를 yyyy/MM/dd 형식으로 반환한다.
	 * </pre>
	 *
	 * @param dateData Timestamp 형식의 날짜
	 * @return String yyyy/MM/dd 형식의 Timestamp 날짜
	 */
	public static String convertToString(Timestamp dateData) {
		return convertToString(dateData, "yyyy/MM/dd");
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 Timestamp 날짜를 pattern 에 따른 형식으로 반환한다.
	 * </pre>
	 *
	 * @param dateData Timestamp 형식의 날짜
	 * @param pattern SimpleDateFormat 에 적용할 pattern
	 * @return String yyyy/MM/dd 형식의 Timestamp 날짜
	 */
	public static String convertToString(Timestamp dateData, String pattern) {
		return convertToString(dateData, pattern, java.util.Locale.KOREA);
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의 Timestamp 날짜를 pattern 과 locale  에 따른 형식으로 반환한다.
	 *
	 * </pre>
	 *
	 * @param dateData Timestamp 형식의 날짜
	 * @param pattern SimpleDateFormat 에 적용할 pattern
	 * @param locale 국가별 LOCALE
	 * @return String pattern 형식의 Timestamp 날짜
	 * @exception Exception
	 */
	public static String convertToString(Timestamp dateData, String pattern, java.util.Locale locale) {
		if (dateData == null) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
		return formatter.format(dateData);
	}

	/**
	 * <pre>
	 *    yyyyMMdd 형식의  날짜를 Timestamp 로  반환한다.
	 * </pre>
	 *
	 * @param dateData yyyyMMdd 형식의 날짜
	 * @return Timestamp 형식의 해당 날짜
	 */
	public static Timestamp convertToTimestamp(String dateData) {
		if (dateData == null)
			return null;
		if (dateData.trim().equals(""))
			return null;

		int dateObjLength = dateData.length();

		String yearString = "2002";
		String monthString = "01";
		String dayString = "01";

		if (dateObjLength >= 4) {
			yearString = dateData.substring(0, 4);
		}
		if (dateObjLength >= 6) {
			monthString = dateData.substring(4, 6);
		}
		if (dateObjLength >= 8) {
			dayString = dateData.substring(6, 8);
		}

		int year = Integer.parseInt(yearString);
		int month = Integer.parseInt(monthString) - 1;
		int day = Integer.parseInt(dayString);

		Calendar cal = new GregorianCalendar();
		cal.set(year, month, day);
		// cal.getTime();
		return new Timestamp(cal.getTime().getTime());
	}

	/**
	 * <pre>
	 *    yyyyMMddHHmmss 형식의  날짜시각을 Timestamp 로  반환한다.
	 * </pre>
	 *
	 * @param dateData yyyyMMddHHmmss 형식의 날짜시각
	 * @return Timestamp 형식의 해당 날짜시각
	 */
	public static Timestamp convertToTimestampHMS(String dateData) {
		if (dateData == null)
			return null;
		if (dateData.trim().equals(""))
			return null;

		String yearString = dateData.substring(0, 4);
		String monthString = dateData.substring(4, 6);
		String dayString = dateData.substring(6, 8);
		String hourString = dateData.substring(8, 10);
		String minString = dateData.substring(10, 12);
		String secString = dateData.substring(12, 14);

		int year = Integer.parseInt(yearString);
		int month = Integer.parseInt(monthString) - 1;
		int day = Integer.parseInt(dayString);
		int hour = Integer.parseInt(hourString);
		int min = Integer.parseInt(minString);
		int sec = Integer.parseInt(secString);

		Calendar cal = new GregorianCalendar();
		cal.set(year, month, day, hour, min, sec);

		return new Timestamp(cal.getTime().getTime());
	}

	/**
	 * <pre>
	 * check date string validation with an user defined format.
	 * </pre>
	 *
	 * @param s date string you want to check.
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return date java.util.Date
	 */
	@SuppressWarnings("unused")
	private static java.util.Date check(String s, String format) throws java.text.ParseException {
		if (s == null)
			throw new java.text.ParseException("date string to check is null", 0);
		if (format == null)
			throw new java.text.ParseException("format string to check date is null", 0);

		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, java.util.Locale.KOREA);
		java.util.Date date = null;
		try {
			date = formatter.parse(s);
		} catch (java.text.ParseException e) {
			throw new java.text.ParseException(" wrong date:\"" + s + "\" with format \"" + format + "\"", 0);
		}
		return date;
	}
	
	/**
	 * <pre>
	 * 지정된 format을 SimpleDateFormat의 문법에 입각하여 formatting한다.<BR>
	 * </pre>
	 *
	 * @param String format SimpleDateFormat의 문법에 맞는 format 문자열
	 * @return 주어진 format이 적용된 date string
	 */
	public static String getDateFormat(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, java.util.Locale.KOREA);
		return dateFormat.format(new java.util.Date());
	}

	/**
	 * <pre>
	 * 주민등록번호 13자리값을 받아 현재의 만 나이를 계산한다.
	 *
	 * 일반 만나이 계산법 (회원 가입시 14세 미만 체크시 해당)
	 * --------------------------------------------------- (생일이 지난 경우) 만 나이: 현재
	 * 연도 - 태어난 연도(주민등록상) (생일이 안 지난 경우) 만 나이: 현재 연도 - 태어난 연도(주민등록상) - 1
	 *
	 * 청소년보호법 관련 19세 계산법 ---------------------------------------------------- 위
	 * 만나이 계산법에서 생일이 지난경우로 계산합니다. 연 나이: 현재 연도 - 태어난 연도(주민등록상)
	 * </pre>
	 *
	 * @param ssn 주민등록번호(13자리)
	 * @param ageCalculus 계산법 구분 (1: 일반 만나이, 2:청소년보호법)
	 * @return 현재의 만 나이
	 */
	public static String getAge(String ssn, String ageCalculus) {

		int _age = 0;

		// 주민번호13자리
		if (ssn != null && ssn.length() == 13) {

			// 주민등록번호 자르기 (년/월일/성별구분)
			int ssnYear = Integer.parseInt(ssn.substring(0, 2)); // 년
			int ssnDate = Integer.parseInt(ssn.substring(2, 6)); // 월일
			int sexGubun = Integer.parseInt(ssn.substring(6, 7)); // 남자/여자

			/*
			 * 주민번호2 의 성별 구분값으로 년도 재계산 2000년 이전에 태어난 사람은 남자 1, 여자 2로 시작 2000년 이후에 태어난 사람은 남자 3, 여자 4로 시작
			 */
			if (sexGubun == 1 || sexGubun == 2 || sexGubun == 5 || sexGubun == 6) {
				ssnYear = 1900 + ssnYear;
			} else if (sexGubun == 3 || sexGubun == 4 || sexGubun == 7 || sexGubun == 8) {
				ssnYear = 2000 + ssnYear;
			} else if (sexGubun == 9 || sexGubun == 0) {
				ssnYear = 1800 + ssnYear;
			}

			// 오늘날짜분석
			int currYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())); // 년
			int currDate = Integer.parseInt(new SimpleDateFormat("MMdd").format(new Date())); // 월일

			// 일반 만나이 계산(생일이 지났는지 확인)
			if (ageCalculus.equals("1"))
				_age = currYear - ssnYear - ((currDate - ssnDate < 0) ? 1 : 0);
			else
				_age = currYear - ssnYear;

		}

		return String.valueOf(_age);
	}

	/**
	 * <pre>
	 * 주민등록번호 13자리값을 받아 현재의 만 나이를 계산한다.
	 * </pre>
	 *
	 * @param ssn 주민등록번호(13자리)
	 * @return 현재의 만 나이
	 */
	public static String getAge(String ssn) {
		// default : 일반 만나이 계산
		return getAge(ssn, "1");
	}
	
    /**
     * 어떤 날짜가( 1998.01.02, 98.01.02, 19980102, 980102 ) 들어오건 간에 YYYYMMDD로 변환한다.
     * 
     * @param argDate - 변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
     * @return String - 변환된 일자
     */
    public static String toYYYYMMDDDate(String argDate) {
        boolean isMunja = false;
        boolean isCorrectArg = true;
        String subArg = "";
        String date = "";
        String result = "";

        if (argDate != null) subArg = argDate.trim();

        for (int inx = 0; inx < subArg.length(); inx++) {
            if (java.lang.Character.isLetter(subArg.charAt(inx)) || subArg.charAt(inx) == ' ') {
                isCorrectArg = false;
                break;
            }
        }

        if (!isCorrectArg) {           
            throw new IllegalArgumentException("'" + argDate + "'는 올바르지 않은 년월일 형식입니다.");
        }

        if (subArg.length() != 8) {
            if (subArg.length() != 6 && subArg.length() != 10) {                
                throw new IllegalArgumentException("'" + argDate + "'는 올바르지 않은 년월일 형식입니다.");
            }

            if (subArg.length() == 6) {
                if (Integer.parseInt(subArg.substring(0, 2)) > 50) date = "19";
                else date = "20";

                result = date + subArg;
            }

            if (subArg.length() == 10) result = subArg.substring(0, 4) + subArg.substring(5, 7)
                    + subArg.substring(8, 10);
        } else {// 8자린 경우 ( 98.01.02, 19980102 )

            try {
                Integer.parseInt(subArg);
            } catch (NumberFormatException ne) {
                isMunja = true;
            }

            if (isMunja) // 98.01.02 혹은 98/01/02 형식의 포맷일 경우
            {
                date = subArg.substring(0, 2) + subArg.substring(3, 5) + subArg.substring(6, 8);
                if (Integer.parseInt(subArg.substring(0, 2)) > 50) result = "19" + date;
                else result = "20" + date;
            } else // 19980102 형식의 포맷인 경우
            return subArg;
        }
        return result;
    }
	
    /**
     * 입력된 일자를 Date 객체로 반환한다.
     * 
     * @param year - 년
     * @param month - 월
     * @param date - 일
     * @return Date - 해당일자에 해당하는 Date
     */
    public static Date createDate(int year, int month, int date) {
        return createCalendar(year, month, date).getTime();
    }
    
    /**
     * 입력된 일자를 Calendar 객체로 반환한다.
     * 
     * @param year - 년
     * @param month - 월
     * @param date - 일
     * @return Calendar - 해당일자에 해당하는 Calendar
     */
    public static Calendar createCalendar(int year, int month, int date) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(year, month - 1, date);
        return calendar;
    }

    /**
     * 입력된 일자를 Date 객체로 반환한다.
     * 
     * @param argDate - 변환할 일자( 1998.01.02, 98.01.02, 19980102, 980102 등 )
     * @return Calendar - 해당일자에 해당하는 Calendar
     */
    public static Date toDate(String pDate) {
        String date = toYYYYMMDDDate(pDate);

        return createDate(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(4, 6)), Integer.parseInt(date.substring(6, 8)));
    }
    
	/**
	 * 입력된 amount로 '달(월)' 재설정
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date setMonth(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(calendar.MONTH, amount);

		return calendar.getTime();
	}

	/**
	 * 입력된 amount로 '일' 재설정
	 * 
	 * @param date
	 * @param amount
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date setDay(Date date, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(calendar.DATE, amount);

		return calendar.getTime();
	}
	
	/**
	 * 두 날짜 사이의 일수 반환
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDayCnt(Date startDate, Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();

		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);
		int day = 0;
		while (startCalendar.compareTo(endCalendar) != 1) {
			day++;
			startCalendar.add(Calendar.DATE, 1);
		}
		return day;

	}
    
}