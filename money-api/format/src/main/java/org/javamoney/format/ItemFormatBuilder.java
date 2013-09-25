/*
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE
 * CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT.
 * PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY
 * DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE
 * AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE"
 * BUTTON AT THE BOTTOM OF THIS PAGE.
 * 
 * Specification: JSR-354 Money and Currency API ("Specification")
 * 
 * Copyright (c) 2012-2013, Credit Suisse All rights reserved.
 */
package org.javamoney.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * This class implements a builder that allows creating of {@link ItemFormat}
 * instances programmatically using a fluent API. The formatting hereby is
 * modeled by a concatenation of {@link FormatToken} instances. The same
 * {@link FormatToken} instances also are responsible for implementing the
 * opposite, parsing, of an item from an input character sequence. Each
 * {@link FormatToken} gets access to the current parsing location, and the
 * original and current character input sequence, modeled by the
 * {@link ParseContext}. Finall if parsing of a part failed, a
 * {@link FormatToken} throws an {@link ItemParseException} describing the
 * problem.
 * <p>
 * This class is not thread-safe and therefore should not be shared among
 * different threads.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the target type.
 */
public class ItemFormatBuilder<T> {
	/** The tokens to be used for formatting/parsing. */
	private List<FormatToken<T>> tokens = new ArrayList<FormatToken<T>>();
	/** The localization configuration. */
	private LocalizationStyle style;
	/** The target type being parsed/formatted. */
	private Class<T> targetType;
	/** The item factory to be used. */
	private ItemFactory<T> itemFactory;

	/**
	 * Creates a new Builder.
	 */
	public ItemFormatBuilder() {

	}

	/**
	 * Creates a new Builder.
	 * 
	 * @param targetType
	 *            the target class.
	 */
	public ItemFormatBuilder(Class<T> targetType) {
		if (targetType == null) {
			throw new IllegalArgumentException("targetType must not be null.");
		}
		this.targetType = targetType;
	}

	/**
	 * Access the target class, which this formatter can handle with.
	 * 
	 * @return the target class, never null.
	 */
	public Class<T> getTargetType() {
		return this.targetType;
	}

	/**
	 * Configure the format with the given {@link LocalizationStyle}.
	 * 
	 * @param style
	 *            the style to be applied.
	 * @return the builder instance, for chaining.
	 */
	public ItemFormatBuilder<T> withStyle(LocalizationStyle style) {
		if (style == null) {
			throw new IllegalArgumentException("style required.");
		}
		this.style = style;
		return this;
	}

	/**
	 * Configure the format with the given target type.
	 * 
	 * @param targetType
	 *            the target type to be applied.
	 * @return the builder instance, for chaining.
	 */
	public ItemFormatBuilder<T> withTargetType(Class<T> targetType) {
		if (targetType == null) {
			throw new IllegalArgumentException("targetType required.");
		}
		this.targetType = targetType;
		return this;
	}

	/**
	 * Add a {@link FormatToken} to the token list.
	 * 
	 * @param token
	 *            the token to add.
	 * @return the builder, for chaining.
	 */
	public ItemFormatBuilder<T> append(FormatToken<T> token) {
		this.tokens.add(token);
		return this;
	}

	/**
	 * Add a {@link FormatToken} to the token list.
	 * 
	 * @param token
	 *            the token to add.
	 * @return the builder, for chaining.
	 */
	public ItemFormatBuilder<T> append(String token) {
		this.tokens.add(new LiteralToken<T>(token));
		return this;
	}

	/**
	 * The toal number of tokens.
	 * 
	 * @return the number of tokens.
	 */
	public int getTokenCount() {
		return this.tokens.size();
	}

	/**
	 * Set the item factory used, to create the item parsed from the results in
	 * the {@link ParseContext}.
	 * 
	 * @param itemFactory
	 *            the {@link ItemFactory}.
	 * @return the builder, for chaining.
	 */
	public ItemFormatBuilder<T> withItemFactory(ItemFactory<T> itemFactory) {
		this.itemFactory = itemFactory;
		return this;
	}

	/**
	 * Get the configured item factory.
	 * 
	 * @return the {@link ItemFactory}.
	 */
	public ItemFactory<T> getItemFactory() {
		return itemFactory;
	}

	/**
	 * Clears the builder (tokens, item factory).
	 */
	public void clear() {
		this.tokens.clear();
		this.itemFactory = null;
	}

	/**
	 * Checks if the builder is ready for creating a {@link ItemFormatBuilder}.
	 * 
	 * @return true, if a format instance can be build.
	 * @see #build()
	 */
	public boolean isBuildable() {
		return this.style != null && this.targetType != null
				&& !this.tokens.isEmpty();
	}

	/**
	 * Access all the token used for building up this format.
	 * 
	 * @return the token used by this formatter, never {@code null}.
	 */
	public List<FormatToken<T>> getTokens() {
		return Collections.unmodifiableList(this.tokens);
	}

	/**
	 * Get the configuring {@link LocalizationStyle}.
	 * 
	 * @return the style instance applied, never null.
	 */
	public LocalizationStyle getStyle() {
		return this.style;
	}

	/**
	 * This method creates an {@link ItemFormat} based on this instance, hereby
	 * using the given a {@link ItemFactory} to extract the item to be returned
	 * from the {@link ParseContext}'s results.
	 * 
	 * @return the {@link ItemFormat} instance, never null.
	 */
	public ItemFormat<T> build() {
		if (this.itemFactory == null) {
			return new TokenizedItemFormat<T>(targetType, style,
					new DefaultItemFactory<T>(targetType), tokens);
		}
		return new TokenizedItemFormat<T>(targetType, style, itemFactory,
				tokens);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BuildableItemFormat [targetType=" + targetType + ", style="
				+ style + ", tokens=" + tokens + "]";
	}

	/**
	 * Adapter implementation that implements the {@link ItemFormat} interface
	 * based on a {@link ItemFormatBuilder} and a {@link ItemFactory}.
	 * 
	 * @author Anatole Tresch
	 * 
	 * @param <T>
	 *            the target type
	 */
	private final static class TokenizedItemFormat<T> implements
			ItemFormat<T> {
		/** The tokens to be used for formatting/parsing. */
		private List<FormatToken<T>> tokens = new ArrayList<FormatToken<T>>();
		/** The localization configuration. */
		private LocalizationStyle style;
		/** The target type being parsed/formatted. */
		private Class<T> targetType;
		/** The item factory to be used. */
		private ItemFactory<T> itemFactory;

		/**
		 * Creates a new instance.
		 * 
		 * @param buildItemFormat
		 *            the base buildItemFormat, not null.
		 * @param itemFactory
		 *            the itemFactory to be used, not null.
		 */
		public TokenizedItemFormat(Class<T> targetType,
				LocalizationStyle style, ItemFactory<T> itemFactory,
				FormatToken<T>... tokens) {
			this(targetType, style, itemFactory, Arrays.asList(tokens));
		}

		/**
		 * Creates a new instance.
		 * 
		 * @param buildItemFormat
		 *            the base buildItemFormat, not null.
		 * @param itemFactory
		 *            the itemFactory to be used, not null.
		 */
		public TokenizedItemFormat(Class<T> targetType,
				LocalizationStyle style, ItemFactory<T> itemFactory,
				List<FormatToken<T>> tokens) {
			if (targetType == null) {
				throw new IllegalArgumentException(
						"Target Class must not be null.");
			}
			if (style == null) {
				throw new IllegalArgumentException(
						"LocalizationStyle must not be null.");
			}
			if (itemFactory == null) {
				throw new IllegalArgumentException(
						"ItemFactory must not be null.");
			}
			if (tokens == null || tokens.isEmpty()) {
				throw new IllegalArgumentException(
						"tokens must not be null or empty.");
			}
			this.targetType = targetType;
			this.style = style;
			this.itemFactory = itemFactory;
			this.tokens.addAll(tokens);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.format.ItemFormat#getTargetClass()
		 */
		@Override
		public Class<T> getTargetClass() {
			return this.targetType;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.format.ItemFormat#getStyle()
		 */
		@Override
		public LocalizationStyle getStyle() {
			return this.style;
		}

		/**
		 * Print the item into an {@link Appendable}.
		 * 
		 * @param appendable
		 *            the appendable, not null
		 * @param item
		 *            the item being formatted, not null
		 * @throws IOException
		 *             forwarded exception thrown by the {@link Appendable}.
		 */
		public void print(Appendable appendable, T item, Locale locale)
				throws IOException {
			for (FormatToken<T> token : tokens) {
				token.print(appendable, item, locale, style);
			}
		}

		/**
		 * Formats the item as {@link String}.
		 * 
		 * @param item
		 *            the item being formatted, not null
		 * @return The formatted String, not null.
		 * @throws ItemFormatException
		 *             If formatting fails.
		 */
		public String format(T item, Locale locale) {
			StringBuilder builder = new StringBuilder();
			try {
				print(builder, item, locale);
			} catch (IOException e) {
				throw new ItemFormatException("Error foratting of " + item, e);
			}
			return builder.toString();
		}

		/**
		 * Parses the input text into an item of type T.
		 * 
		 * @param text
		 *            The input text
		 * @return the item to be parsed.
		 * @throws ItemParseException
		 *             If parsing failed.
		 */
		public T parse(CharSequence text, Locale locale)
				throws ItemParseException {
			ParseContext<T> ctx = new ParseContext<T>(text, itemFactory);
			for (FormatToken<T> token : tokens) {
				token.parse(ctx, locale, style);
				if (ctx.isComplete()) {
					return ctx.getItem();
				}
			}
			if (ctx.isComplete()) {
				return ctx.getItem();
			}
			throw new ItemParseException("Parsing of item of type "
					+ getTargetClass() + " failed from " + ctx);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "BuildableItemFormat [targetType=" + targetType + ", style="
					+ style + ", itemFactory=" + itemFactory + ", tokens="
					+ tokens + "]";
		}

	}

}