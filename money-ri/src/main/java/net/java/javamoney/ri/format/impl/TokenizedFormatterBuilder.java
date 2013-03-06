package net.java.javamoney.ri.format.impl;

import java.util.Enumeration;

import javax.money.format.ItemFormatter;
import javax.money.format.LocalizationStyle;

import net.java.javamoney.ri.format.FormatterToken;

public interface TokenizedFormatterBuilder<T> {

	public LocalizationStyle getStyle();

	public TokenizedFormatterBuilder<T> setLocalizationStyle(
			LocalizationStyle style);

	public TokenizedFormatterBuilder<T> addToken(FormatterToken<T> token);

	public TokenizedFormatterBuilder<T> addToken(String token);

	public Enumeration<FormatterToken<T>> getTokens();

	public int getTokenCount();

	public void clear();

	public ItemFormatter<T> toItemFormatter();

}