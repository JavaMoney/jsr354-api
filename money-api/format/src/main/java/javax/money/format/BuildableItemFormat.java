/*
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

/**
 * This class implements a format based on an ordered list of
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
	 * This method creates an {@link ItemFormat} based on this instance, hereby
	 * a {@link DefaultItemFactory} is used to extract the item to be returned
	 * from the {@link ParseContext}'s results.
	 * 
	 * @return the {@link ItemFormat} instance, never null.
	 */
	public ItemFormat<T> toItemFormat() {
		return toItemFormat(new DefaultItemFactory<T>(getTargetClass()));
	}

	/**
	 * This method creates an {@link ItemFormat} based on this instance, hereby
	 * using the given a {@link ItemFactory} to extract the item to be returned
	 * from the {@link ParseContext}'s results.
	 * 
	 * @return the {@link ItemFormat} instance, never null.
	 */
	public ItemFormat<T> toItemFormat(ItemFactory<T> itemFactory) {
		return new BuiltItemFormatAdapter<T>(this, itemFactory);
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
	 * Adapter implementation that implements the {@link ItemFormat} interface
	 * based on a {@link BuildableItemFormat} and a {@link ItemFactory}.
	 * 
	 * @author Anatole Tresch
	 * 
	 * @param <T>
	 *            the target type
	 */
	private final static class BuiltItemFormatAdapter<T> implements
			ItemFormat<T> {
		/** the base {@link BuildableItemFormat} instance. */
		private BuildableItemFormat<T> buildItemFormat;
		/** The {@link ItemFactory} used. */
		private ItemFactory<T> itemFactory;

		/**
		 * Creates a new instance.
		 * 
		 * @param buildItemFormat
		 *            the base buildItemFormat, not null.
		 * @param itemFactory
		 *            the itemFactory to be used, not null.
		 */
		BuiltItemFormatAdapter(BuildableItemFormat<T> buildItemFormat,
				ItemFactory<T> itemFactory) {
			this.buildItemFormat = buildItemFormat;
			this.itemFactory = itemFactory;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.format.ItemFormat#getTargetClass()
		 */
		@Override
		public Class<T> getTargetClass() {
			return this.buildItemFormat.getTargetClass();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.format.ItemFormat#getStyle()
		 */
		@Override
		public LocalizationStyle getStyle() {
			return this.buildItemFormat.getStyle();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.format.ItemFormat#format(java.lang.Object)
		 */
		@Override
		public String format(T item) {
			return this.buildItemFormat.format(item);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.format.ItemFormat#print(java.lang.Appendable,
		 * java.lang.Object)
		 */
		@Override
		public void print(Appendable appendable, T item) throws IOException {
			this.buildItemFormat.print(appendable, item);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.money.format.ItemFormat#parse(java.lang.CharSequence)
		 */
		@Override
		public T parse(CharSequence text) throws ItemParseException {
			return this.buildItemFormat.parse(text, this.itemFactory);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "BuiltItemFormat [buildItemFormat=" + buildItemFormat
					+ ", itemFactory=" + itemFactory + "]";
		}

	}

}