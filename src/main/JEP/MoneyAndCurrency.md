+Title: JEP Money and Currency API
+Author: Anatole Tresch
 Organization: Credit Suisse AG
+Created: 2014/10/28
+Type: Feature
 State: Draft
+Exposure: Open
+Component: Core Libraries/i18n
+Scope: SE | JDK | Implementation
 JSR: 354
 RFE: <number of primary RFE, if any> (<secondary RFE> ...)
+Discussion: i18n dash dev at openjdk dot java dot net
 Start: YYYY/QN
 Depends: -
 Blocks: -
 Effort: M
 Duration: M
+Template: 1.0

// The body of a JEP uses the Markdown markup language
// (http://daringfireball.net/projects/markdown/basics).

Summary
-------

JSR 354 defines a flexible and extensible API for dealing with currencies,
monetary amounts, currency conversion and monetary formatting.

Non-Goals
---------

The JSR 354 should not replace the existing java.util.Currency class.

Success Metrics
---------------

The JSR's TCK runs successfully against the Java platform with the JSR's API and
implementation included.

Motivation
----------

Adding this JSR is requested by huge parts of the Java community, e.g.
see http://www.takipiblog.com/350-developers-voted-for-features-in-java-9-have-they-decided-the-same-as-oracle/#more-659
Similarly adding the API to the JDK will help companies and financial institutions
to adopt the API. The Java language would be the first programming language that
comes with extensive and well designed support for handling with financial
data.

Description
-----------

The JSR's API and design are described in the correponding specification.
The latest version can be found at https://github.com/JavaMoney/jsr354-api/blob/master/src/main/asciidoc/JavaMoneySpecification.adoc.

The reference implementation as well is documented in a short user guide accessible
under https://github.com/JavaMoney/jsr354-ri/blob/master/src/main/asciidoc/userguide.adoc

Summarizing the JSR contains the following main artifacts:
* Interfaces for amounts (MonetaryAmount), currencies (CurrencyUnit), rounding (MonetaryRounding),
  conversion (CurrencyConverter, ExchangeRateprovider) and formatting (MonetaryAmountFormat).
* Corresponding singleton accessors (MonetaryCurrencies, MonetaryAmounts, MonetaryRoundings,
  MonetaryConversions, MonetaryFormats).
* The singleton accessors are backed up by SPI interfaces (MonetaryCurrenciesSingletonSpi,
  MonetaryAmountsSingletonSpi, MonetaryRoundingsSingletonSpi, MonetaryConversionSingletonSpi,
  MonetaryFormatsSingletonSpi).
* More complex detailed not possible being modelled in a unified way, could be added to context
  instances (extending AbstractContext): CurrencyContext, MonetaryAmountContext, RoundingContext,
  ConversionContext, ProviderContext, MonetaryFormatContext).
* Contexts can be created using a fluent builder API: CurrencyContextBuilder, MonetaryAmountContextBuilder,
  RoundingContextBuilder, ConversionContextBuilder, ProviderContextBuilder, MonetaryFormatContextBuilder.
* Similar to context, query parameter objects allow to query items in a flexible and extensible way:
  CurrencyQuery, MonetaryAmountFactoryQuery, RoundingQuery, ConversionQuery, FormatQuery)
* Queries can be built using a fluent builder API:
  CurrencyQueryBuilder, MonetaryAmountFactoryQueryBuilder, RoundingQueryBuilder,
  ConversionQueryBuilder, FormatQueryBuilder)
* Bootstrapping is done by a ServiceProvider component, which by default relies on the java.util.ServiceLoader.
  The loader component can be replaced, making the mechanism open and flexible for alternate/estended usage
  component lifecycle scenarios.
* Finally the API provides functional interfaces (unary or functions) for adding external functions
  to amounts.

The implementation part as well comes with some interesting features:
* It provides two implementation for amount: Money (based on BigDecimal) and FastMoney (based on a long value).
* It provides default formatting for all currencies, based on the current DecimalFormat instance.
* It provides CurrencyUnit instances for all currencies already available from java.util.Currency.
* It provides a BuildableCurrencyUnit and an according BuildableCurrencyUnitBuilder.
* It provides default rounding based on the CurrencyUnit or a MathContext provided as attribute.
* It provides default formatting based on java.text.DecimalFormat, hereby also supporting some
  additional features, such as adaptive number grouping, different currency styles and more.

Testing
-------

The JSR's component tests are based on TestNG. There are tests for the API
singletons, in addition to the reference implementation tests. Additionally
the JSR comes with a TCK, that ensures correct implementation and
interoperability. This TCK must be run against the Java runtime with the
JSR included. It should be executed without any errors without any additional
configuration.
From a runtime perspective the JSR's API currently requires Java SE 8, since
it makes use of lamdas, method references and default methods. When Java ME
would also provide functional features, the API could be usable in ME as well.


Dependencies
------------

The JSR's bootstrap mechanism uses java.util.ServiceLoader for loading of
backing services. Logging is done using java.util.logging. The currency
providers for ISO currencies are using java.util.Currency internally,
currently wrapping the Currency instance into an adapter implementing
CurrencyUnit. Alternatively it would be possible to let java.util.Currency
implement CurrencyUnit. Basically most of the methods required are already
present. The additional CurrencyContext to be returned can be easily
implemented by a static default instance.
The amount implementations provided are based on BigDecimal (Money) and
long (FastMoney). FastMoney additionally also uses java.math.Math for
arithmetic functions.
The JSR's formatting API internally relies on java.text.DecimalFormat for
the default formats provided.
The JSR's conversion providers finally are trying to update their datafeeds
on startup. It is arguable, if these providers in the current form should
be added into the JDK. They could easily also provided as an external
Java module or library.

This JEP does not have any dependencies, or other JEPs that depend on it.
