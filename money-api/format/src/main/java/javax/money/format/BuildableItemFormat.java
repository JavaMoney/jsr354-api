/**
 * CREDIT SUISSE IS WILLING TO LICENSE THIS SPECIFICATION TO YOU ONLY UPON THE CONDITION THAT YOU ACCEPT ALL OF THE TERMS CONTAINED IN THIS AGREEMENT. PLEASE READ THE TERMS AND CONDITIONS OF THIS AGREEMENT CAREFULLY. BY DOWNLOADING THIS SPECIFICATION, YOU ACCEPT THE TERMS AND CONDITIONS OF THE AGREEMENT. IF YOU ARE NOT WILLING TO BE BOUND BY IT, SELECT THE "DECLINE" BUTTON AT THE BOTTOM OF THIS PAGE.
 *
 * Specification:  JSR-354  Money and Currency API ("Specification")
 *
 * Copyright (c) 2012-2013, Credit Suisse
 * All rights reserved.
 */
package javax.money.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.money.MonetaryFunction;
import javax.money.format.ParseContext.ItemFactory;

/**
 * This class implements a {@link ItemFormat} based on an ordered list of
 * {@link FormatToken} instances.
 * 
 * @author Anatole Tresch
 * 
 * @param <T>
 *            the target type.
 */
public class BuildableItemFormat<T> {
	/** The tokens to be used for formatting/parsing. */
	private List<FormatToken<T>> tokens = new ArrayList<FormatToken<T>>();
	/** The localization configuration. */
	private LocalizationStyle style;
	/** The target type being parsed/formatted. */
	private Class<T> targetType;

	/**
	 * Creates a new item format.
	 * 
	 * @param type
	 *            The target type being formatted/parsed.
	 * @param style
	 *            The configuring style, forwarded to all registered
	 *            {@link FormatToken} instances.
	 * @param tokens
	 *            The {@link FormatToken} instances to be used.
	 */
	public BuildableItemFormat(Class<T> type, LocalizationStyle style,
			FormatToken<T>... tokens) {
		this(type, style, null, tokens);
	}

	/**
	 * Creates a new item format.
	 * 
	 * @param type
	 *            The target type being formatted/parsed.
	 * @param style
	 *            The configuring style, forwarded to all registered
	 *            {@link FormatToken} instances.
	 * @param itemFactory
	 *            The item factory to be applied after all {@link FormatToken}
	 *            instances has parsed the input.
	 * @param tokens
	 *            The {@link FormatToken} instances to be used.
	 */
	public BuildableItemFormat(Class<T> type, LocalizationStyle style,
			MonetaryFunction<ParseContext<T>, T> itemFactory,
			FormatToken<T>... tokens) {
		if (style == null) {
			throw new IllegalArgumentException("Style must not be null.");
		}
		if (type == null) {
			throw new IllegalArgumentException("type must not be null.");
		}
		this.targetType = type;
		this.style = style;
		// TODO check compatibility of tokens...
		if (tokens != null && !(tokens.length == 0)) {
			this.tokens.addAll(Arrays.asList(tokens));
		}
	}

	/**
	 * Access the target class, which this formatter can handle with.
	 * 
	 * @return the target class, never null.
	 */
	public Class<T> getTargetClass() {
		return this.targetType;
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
	public void print(Appendable appendable, T item) throws IOException {
		for (FormatToken<T> token : tokens) {
			token.print(appendable, item, style);
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
	public String format(T item) {
		StringBuilder builder = new StringBuilder();
		try {
			print(builder, item);
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
	public T parse(CharSequence text) throws ItemParseException {
		return parse(text, new DefaultItemFactory<T>(getTargetClass()));
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
	public T parse(CharSequence text, ItemFactory<T> itemFactory)
			throws ItemParseException {
		ParseContext<T> ctx = new ParseContext<T>(text, itemFactory);
		for (FormatToken<T> token : tokens) {
			token.parse(ctx, style);
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
	 * This class implements a {@link FormatterBuilder} based on an ordered and
	 * {@link Decoratable} list of {@link FormatToken} instances.
	 * 
	 * @author Anatole Tresch
	 * 
	 * @param <T>
	 *            The target type.
	 */
	public static class Builder<T> {
		/** THe current tokens. */
		private List<FormatToken<T>> tokens = new ArrayList<FormatToken<T>>();
		/** the curren style. */
		private LocalizationStyle style;
		/** The target type. */
		private Class<T> targetType;
		/** The item factory to be used. */
		private ItemFactory<T> itemFactory;

		/**
		 * Creates a new Builder.
		 * 
		 * @param targetType
		 *            the target class.
		 */
		public Builder(Class<T> targetType) {
			if (targetType == null) {
				throw new IllegalArgumentException(
						"targetType must not be null.");
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
		 * The configuring style.
		 * 
		 * @return the style applied.
		 */
		public LocalizationStyle getLocalizationStyle() {
			return style;
		}

		/**
		 * COnfigure the format with the given {@link LocalizationStyle}.
		 * 
		 * @param style
		 *            the style to be applied.
		 * @return the builder instance, for chaining.
		 */
		public Builder<T> setLocalizationStyle(LocalizationStyle style) {
			this.style = style;
			return this;
		}

		/**
		 * Add a {@link FormatToken} to the token list.
		 * 
		 * @param token
		 *            the token to add.
		 * @return the builder, for chaining.
		 */
		public Builder<T> addToken(FormatToken<T> token) {
			this.tokens.add(token);
			return this;
		}

		/**
		 * Add a literal, which will be mapped to a {@link LiteralToken}.
		 * 
		 * @param token
		 *            the literal to be added to the format.
		 * @return the builder, for chaining.
		 */
		public Builder<T> addLiteral(String token) {
			this.tokens.add(new LiteralToken<T>(token));
			return this;
		}

		/**
		 * Access all tokens currently configured.
		 * 
		 * @return the tokens
		 */
		public Collection<FormatToken<T>> getTokens() {
			return Collections.unmodifiableCollection(this.tokens);
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
		 * Set the item factory used, to create the item parsed from the results
		 * in the {@link ParseContext}.
		 * 
		 * @param itemFactory
		 *            the {@link ItemFactory}.
		 * @return the builder, for chaining.
		 */
		public Builder<T> setItemFactory(ItemFactory<T> itemFactory) {
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
		 * Checks if the builder is ready for creating a
		 * {@link BuildableItemFormat}.
		 * 
		 * @return true, if a format instance can be build.
		 * @see #build()
		 */
		public boolean isBuildable() {
			return !this.tokens.isEmpty();
		}

		/**
		 * Builds a new {@link BuildableItemFormat}.
		 * 
		 * @return the new {@link BuildableItemFormat} instance.
		 */
		@SuppressWarnings("unchecked")
		public BuildableItemFormat<T> build() {
			return new BuildableItemFormat<T>(getTargetType(),
					getLocalizationStyle(), itemFactory,
					tokens.toArray(new FormatToken[tokens.size()]));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "DefaultTokenizedItemFormatter.Builder [style=" + style
					+ ", tokens=" + tokens + "]";
		}

	}

	/**
	 * {@link FormatToken} which adds an arbitrary literal constant value to the
	 * output.
	 * 
	 * @author Anatole Tresch
	 * 
	 * @param <T>
	 *            The item type.
	 */
	public static final class LiteralToken<R> implements FormatToken<R> {
		/** The literal part. */
		private String token;

		/**
		 * Constructor.
		 * 
		 * @param token
		 *            The literal token part.
		 */
		public LiteralToken(String token) {
			if (token == null) {
				throw new IllegalArgumentException("Token is required.");
			}
			this.token = token;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * javax.money.format.FormatToken#parse(javax.money.format.ParseContext,
		 * javax.money.format.LocalizationStyle)
		 */
		@Override
		public void parse(ParseContext<R> context, LocalizationStyle style)
				throws ItemParseException {
			if (!context.consume(token)) {
				throw new ItemParseException("Expected '" + token + "' in "
						+ context.getInput().toString());
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.format.FormatToken#print(java.lang.Appendable,
		 * java.lang.Object, javax.money.format.LocalizationStyle)
		 */
		@Override
		public void print(Appendable appendable, R item, LocalizationStyle style)
				throws IOException {
			appendable.append(this.token);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "LiteralToken [token=" + token + "]";
		}

	}

	/**
	 * Default implementation of {@link ItemFactory} that looks up resulting
	 * item under the class or class name key.
	 * 
	 * @author Anatole Tresch
	 * 
	 * @param <T>
	 *            teh item type.
	 */
	public static final class DefaultItemFactory<T> implements ItemFactory<T> {
		/** The item class. */
		private Class<T> itemClass;

		/**
		 * Constructor.
		 * 
		 * @param itemClass
		 *            The item class, not null.
		 */
		public DefaultItemFactory(Class<T> itemClass) {
			this.itemClass = itemClass;
		}

		/**
		 * Accesses the final item from the {@link ParseContext}.
		 * 
		 * @param context
		 *            the {@link ParseContext}.
		 * @return the item parsed.
		 * @throws IllegalStateException
		 *             , if the item could not be found.
		 * @see #isComplete(ParseContext)
		 */
		@Override
		public T apply(ParseContext<T> context) {
			T item = context.getResult(itemClass, itemClass);
			if (item == null) {
				item = context.getResult(itemClass.getName(), itemClass);
			}
			if (item == null) {
				throw new IllegalStateException("Parsing is not complete.");
			}
			return item;
		}

		/**
		 * CHecks if the required item is availalbe within the
		 * {@link ParseContext}, using the class or fully qualified class name
		 * as a key.
		 * 
		 * @param context
		 *            the {@link ParseContext}.
		 * @return true, if the item parsed was found or can be created.
		 * @see #apply(ParseContext)
		 */
		@Override
		public boolean isComplete(ParseContext<T> context) {
			return context.getResult(itemClass, itemClass) != null
					|| context.getResult(itemClass.getName(), itemClass) != null;
		}

	}

}