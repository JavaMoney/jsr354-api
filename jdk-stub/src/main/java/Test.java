import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Test {
//	public static void main(String[] args) {
//		TimeZone tz = TimeZone.getTimeZone("Europe/Zurich");
//		tz.setID("blabla");
//		System.out.println(tz);
//		
//		tz = TimeZone.getDefault();
//		System.out.println("TimeZone: " + tz);
//		
//		tz = TimeZone.getTimeZone("GMT+3'): " + tz);
//		System.out.println("TimeZone.getTimeZone('GMT +3'): " + tz);
//		
//		tz = TimeZone.getTimeZone("GMT+111'): " + tz);
//		System.out.println("TimeZone.getTimeZone('GMT+111'): " + tz);
		
//		GregorianCalendar cal = new GregorianCalendar();
//		cal.setTimeInMillis(Long.MIN_VALUE);
//		System.out.println("Y: " + cal.get(Calendar.YEAR));
//		System.out.println("E: " + (cal.get(Calendar.ERA)==GregorianCalendar.BC?"BC":"AD"));
//		System.out.println("M: " + cal.get(Calendar.MONTH));
//		System.out.println("D: " + cal.get(Calendar.DAY_OF_MONTH));
//		System.out.println("--");
//		cal = new GregorianCalendar();
//		cal.set(Calendar.YEAR, -1500);
//		System.out.println("ms: " + cal.get(Calendar.MILLISECOND));
//		System.out.println("Y: " + cal.get(Calendar.YEAR));
//		System.out.println("E: " + (cal.get(Calendar.ERA)==GregorianCalendar.BC?"BC":"AD"));
//		System.out.println("M: " + cal.get(Calendar.MONTH));
//		System.out.println("D: " + cal.get(Calendar.DAY_OF_MONTH));
//		System.out.println("--");
//		Date date = new Date(-2206306800000L);
//		System.out.println("Date: " + date);
//		date.setYear(-1000);
//		System.out.println("Date: " + date.toGMTString());
//		date.setYear(-1500);
//		System.out.println("Date: " + date.toGMTString());
//		long ms = Date.UTC(0, 1, 1, 1, 0, 0);
//		System.out.println("ms: " + ms);
//	}
	
	public static void main(String[] args) {
		NumberFormat nf = NumberFormat.getInstance(new Locale("IND"));
//		((DecimalFormat)nf).getDecimalFormatSymbols().setInverseGrouping(true);
//		((DecimalFormat)nf).applyPattern("###,###,###,###,#0.00");
		((DecimalFormat)nf).applyPattern("###,###,##,#,##0.00");
		System.out.println(nf.format(123456789123456.1234567));
	}
}
