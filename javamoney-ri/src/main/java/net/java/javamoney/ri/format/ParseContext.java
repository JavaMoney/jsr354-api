package net.java.javamoney.ri.format;

import java.util.Map;

import javax.money.format.common.ParseException;
import javax.money.format.common.Targeted;

public interface ParseContext<T> extends Targeted<T>{

	int getPosition();
	int getLength();
	CharSequence lookupChars(int num)throws ParseException;
	CharSequence lookupChars();
	CharSequence consumeChars(int num)throws ParseException;
	CharSequence consumeChars();
	Map<Object,Object> getAttributes();
}
