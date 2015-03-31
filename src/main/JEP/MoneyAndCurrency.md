 Title: JEP Money and Currency API
 Author: Anatole Tresch
 Organization: Credit Suisse AG
 Created: 2014/10/28
 Type: Feature
 State: Draft
 Exposure: Open
 Component: Core Libraries/i18n
 Scope: SE
 JSR: 354
 Discussion: i18n dash dev at openjdk dot java dot net
 Start: 2014/Q4
 Depends: -
 Blocks: -
 Effort: M
 Duration: M
 Template: 1.0

Summary
-------

JSR 354 defines a flexible and extensible API for dealing with currencies,
monetary amounts, currency conversion and monetary formatting.

Goals
-----

Extend the Java eco-system with financial abstractions out of the box.

Non-Goals
---------

The JSR 354 must not replace the existing java.util.Currency class. This class
could as well implement CurrencyUnit easily.

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
comes with extensive and support for handling financial
data.

Description
-----------

The JSR's API and design are described in the corresponding specification.
The latest version can be found at https://github.com/JavaMoney/jsr354-api/blob/master/src/main/asciidoc/JavaMoneySpecification.adoc.

The reference implementation as well is documented in a short user guide accessible
under https://github.com/JavaMoney/jsr354-ri/blob/master/src/main/asciidoc/userguide.adoc

Generally the JSR's code can be found at: 

* _API:_ https://github.com/JavaMoney/jsr354-api
* _RI:_ https://github.com/JavaMoney/jsr354-ri
* _TCK:_ https://github.com/JavaMoney/jsr354-tck

The build is based on Maven. The API does not depend on any other modules, whereas RI and TCK depend
on a common parent project located at: https://github.com/JavaMoney/javamoney-parent

Summarizing the JSR contains the following main artifacts:

* Interfaces for amounts (``MonetaryAmount``), currencies (``CurrencyUnit``), rounding (``MonetaryRounding``),
  conversion (``CurrencyConverter, ExchangeRateprovider``) and formatting (``MonetaryAmountFormat``).
* Corresponding singleton accessors (``Monetary, MonetaryConversions, MonetaryFormats``).
* The singleton accessors are backed up by SPI interfaces (``MonetaryCurrenciesSingletonSpi,
  MonetaryAmountsSingletonSpi, MonetaryRoundingsSingletonSpi, MonetaryConversionSingletonSpi,
  MonetaryFormatsSingletonSpi``).
* More complex details which cannot be modelled in a unified way, could be added to context
  instances (extending ``AbstractContext``): ``CurrencyContext, MonetaryAmountContext, RoundingContext,
  ConversionContext, ProviderContext, MonetaryFormatContext``).
* Contexts can be created using a fluent builder API: ``CurrencyContextBuilder, MonetaryAmountContextBuilder,
  RoundingContextBuilder, ConversionContextBuilder, ProviderContextBuilder, MonetaryFormatContextBuilder``.
* Similar to context, query parameter objects allow to query items in a flexible and extensible way:
  ``CurrencyQuery, MonetaryAmountFactoryQuery, RoundingQuery, ConversionQuery, FormatQuery``)
* Queries can be built using a fluent builder API:
  ``CurrencyQueryBuilder, MonetaryAmountFactoryQueryBuilder, RoundingQueryBuilder,
  ConversionQueryBuilder, FormatQueryBuilder``)
* Bootstrapping is done by a ``ServiceProvider`` component, which by default relies on the ``java.util.ServiceLoader``.
  The loader component can be replaced, making the mechanism open and flexible for alternate/extended usage
  component lifecycle scenarios.
* Finally the API provides functional interfaces (unary or functions) for adding external functions
  to amounts.

The reference implementation comes with some interesting features as well:
* It provides two implementations for amount: ``Money`` (based on ``BigDecimal``) and ``FastMoney`` (based on a ``long`` value).
* It provides default formatting for all currencies, based on the current ``DecimalFormat`` instance.
* It provides CurrencyUnit instances for all currencies already available from ``java.util.Currency``.
* It provides a ``BuildableCurrencyUnit`` and an according`` BuildableCurrencyUnitBuilder``.
* It provides default rounding based on the ``CurrencyUnit`` or a ``MathContext`` provided as attribute.
* It provides default formatting based on ``java.text.DecimalFormat``, hereby also supporting some
  additional features, such as adaptive number grouping, different currency styles and more.

How the API should be included into the OpenJDK should be discussed with the OpenJDK architects.
Currently we see the following options:

* Adding a separate package like "javax.money" (current), or "java.money" somewhat similar to 310. 
  Whether that's considered "core" or optional in future JDKs was up to packaging (preferred solution).
* Tighter integration, along the lines of "java.util" or "java.util.money" where as mentioned 
  java.util.Currency would implement CurrencyUnit, and a type like Money plus other core features
  provided to the JDK.

To give some first impressions of the API the following sections give some examples of how the API is designed for use:

#### Working with org.javamoney.moneta.Money

The class ``org.javamoney.moneta.Money`` implements ``MonetaryAmount`` using ``java.math.BigDecimal`` internally:

    MonetaryAmountFactory<Money> fact = Monetary.getAmountFactory(Money.class);
    Money m = fact.setCurrency("USD").setNumber(200.50).create();

Also a generic MonetaryAmount instance can be accessed using a raw factory (hereby it depends on the
configured default amount factory, which effective type instance is returned):

    MonetaryAmount amt = Monetary.getDefaultAmountFactory()
                       .setCurrency("USD").setNumber(200.50).create();
                       
Still we can evaluate the effective amount’s type effectively:

    if(Money.class==amt.getClass()){
      Money m = (Money)amt;
    }
  
But in general, we do not need to know the exact implementation in most cases, since we can access
amount meta-data as a MonetaryContext, This meta-data provides information, such as the maximal
precision, maximal scale supported by the type’s implementation as well as other attributes.

    MonetaryContext ctx = m.getMonetaryContext();
    if(ctx.getMaxPrecision()==0){
       System.out.println("Unbounded maximal precision.");
    }
    if(ctx.getMaxScale()>=5){
       System.out.println("Sufficient scale for our use case, go for it.");
    }

Finally performing arithmetic operations in both of the above scenarios works similar as it is when using 
``java.math.BigDecimal``:

    MonetaryAmount amt = ...;
    amt = amt.multiply(2.0).subtract(1.345);

Also the sample above illustrates how algorithmic operations can be chained together using a fluent API.
As mentioned also external functionality can be chained, e.g. using instances of ``MonetaryOperator``:

    MonetaryAmount amt = ...;
    amt = amt.multiply(2.12345).with(MonetaryRoundings.getDefaultRounding())
          .with(MonetaryFunctions.minimal(100)).
          .multiply(2.12345).with(MonetaryRoundings.getDefaultRounding())
          .with(MonetaryFunctions.percent(23));
        
#### Numeric Precision and Scale

Since the ``Money`` implementation class, which is part of the reference implementation, internally 
uses ``java.math.BigDecimal`` the numeric capabilities match exact the capabilities of ``BigDecimal``. 
When accessing ``MonetaryAmountFactory`` instances it is possible to configure the ``MathContext``
effectively used (by default ``Money`` uses ``MathContext.DECIMAL64``).:

#### Example Configuring a ``MonetaryAmountFactory``, using the RI class ``Money`` as example.

    MonetaryAmountFactory<Money> fact = Monetary.getAmountFactory(
       MonetaryAmountFactoryQueryBuilder.of(Money.class)
         .set(new MathContext(250, RoundingMode.HALF_EVEN)).build()
    );
    
    // Creates an instance of Money with the given MathContext
    MonetaryAmount m1 = fact.setCurrency("CHF").setNumber(250.34).create();
    Money m2 = fact.setCurrency("CHF").setNumber(250.34).create();

#### Extending the API

Now, one last thing to discuss is, how users can add their own functionality, e.g. by writing their own
``MonetaryOperator`` functions. Basically there are two distinct usage scenarios:

* When the basic arithmetic defined on each MonetaryAmount is sufficient, it should be easy to
  implement such functionality, since it's behaving like any other type. The amount type 
  will throw an ``ArithemticException`` by default, if the numeric capabilities are not
  capable of creating the result required.

    public final class DuplicateOp implements MonetaryOperator{
      public <T extends MonetaryAmount> T apply(T amount){
        return (T) amount.multiply(2);
      }
    }
  
* In case the basic operations are not sufficient anymore, or it is more convenient to do a calculation
  externally, it is still not necessary to cast to any implementation type, since 

** the numeric capabilities can be evaluated using the ``MonetaryContext``. On ``MonetaryAmountFactory`` both
   the default and the maximal supported ``MonetaryContext`` can be accessed.
** the numerical value can be extracted in a portable way accessing the ``NumberValue``.
** a ``MonetaryFactory`` can be created to create the result of the same implementation type, without having
   to cast to this type ever explicitly.

As an example find below a rather academical example of a ``MonetaryOperator`` that simply converts any given
amount to an amount with the same numeric value, but with XXX (undefined) as currency:

    public final class ToInvalid implements MonetaryOperator{
      public <T extends MonetaryAmount> T apply(T amount){
        return (T)amount.getFactory().setCurrency("XXX").create();
      }
    }
  
#### Working with ``org.javamoney.moneta.FastMoney``

``org.javamoney.moneta.FastMoney`` implements a ``MonetaryAmount`` using ``long`` as numeric representation, 
whereas the full amount is interpreted as minor units, with a denumerator of 100000.

As an example ``CHF 2.5`` is internally stored as ``CHF 250000``. Addition and subtraction of values is 
trivial, whereas division and multiplication get more complex with non integral values. Compared to ``Money``
the possible amounts to be modeled are limited to an overall precision of 18 and a fixed scale of 5 digits.

Beside that the overall handling of ``FastMoney`` is similar to ``Money``. So we could rewrite the former
example by just replacing ``FastMoney`` with ``Money``:

    MonetaryAmountFactory<FastMoney> fact = Monetary.getAmountFactory(FastMoney.class);
    // Creates an instance of Money with the given MathContext
    MonetaryAmount m1 = fact.setCurrency("CHF").setNumber(250.34).create();
    FastMoney m2 = fact.setCurrency("CHF").setNumber(250.34).create();

Of course, the ``MonetaryContext`` is different than for Money:

    maxPrecision = 18;  // hard limit
    maxScale = 5;       // fixed scale
    numeric class = Long
    attributes: RoundingMode.HALF_EVEN

#### Calculating a Total

A total of amounts can be calculated in multiple ways, one way is simply to chain the amounts with 
``add(MonetaryAmount)``:

    Collection<MonetaryAmount> params = Arryas.asList(new MonetaryAmount[]{
                     Money.of("CHF", 100), Money.of("CHF", 10.20),
                           Money.of("CHF", 1.15),});
    MonetaryAmount total = params.get(0);
    for(int i=1; i<params.length;i++){
      total = total.add(params.get(i));
    }
  
As an alternate it is also possible to define a ``MonetaryOperator``, which can be passed to all amounts:

    public class Total implements MonetaryOperator{
      private MonetaryAmount total;

      public <T extends MonetaryAmount<T>> T apply(T amount){
         if(total==null){
           total = amount;
         }
         else{
           total = total.add(amount);
         }
         // ensure to return correct type, since different implementations
         // can be passed as amount parameter
         return amount.getFactory().with(total).create();
      }
    
      public MonetaryAmount getTotal(){
        return total;
      }
    
      public <T extends MonetaryAmount> T getTotal(Class<T> amountType){
        return Monetary.getAmountFactory(amountType).with(total).create();
      }
    }

Important: We are well aware of the fact that this implementation still has some severe drawbacks,
but we decided for simplicity to not add the following features to this example:

* the implementation can only handle one currency, a better implementation could also be multi-currency
  capable.
* The example implementation above is not thread-safe.

Now with the ``MonetaryOperator`` totalizing looks as follows:

    Total total = new Total();
    params.forEach(total::with);
    System.out.println("TOTAL: " + total.getTotal());

A similar approach can also be used for other multi value calculations as used in statistics, e.g.
average, median etc. Corresponding implementation classes are as well part of the reference implementation.

#### Calculating a Present Value

The present value (abbreviated PV) shows how financial formulas can be implemented based on the JSR 354 API.
A PV models the current value of a financial in- or outflow in the future, weighted with a calculatory 
interest rate. Calculation of a PV requires the following parameters:

* the time of the cash flow (in periods)
* the discount rate (the rate of return that could be earned on an investment in the financial markets
  with similar risk.); the opportunity cost of capital
* the net cash flow i.e. ``cash inflow – cash outflow``, at time t.

The same financial function now can be implemented as follows:

    public <T extends MonetaryAmount> T presentValue(
                                    T amt, BigDecimal rate, int periods){
      BigDecimal divisor = BigDecimal.ONE.add(rate).pow(periods);
      // cast should be safe for implementations that adhere to this spec
      return (T)amt.divide(divisor);
    }
  
This algorithm can be implemented as ``MonetaryOperator`` as well:

    public final class PresentValue implements MonetaryOperator{
       private BigDecimal rate;
       private int periods;
       private BigDecimal divisor;
     
       public PresentValue(BigDecimal rate, int periods){
         Objects.requireNotNull(rate);
         this.rate = rate;
         this.periods = periods;
         this.divisor = BigDecimal.ONE.add(periods).power(periods);
       }
     
       public int getPeriods(){ return periods; }
     
       public BigDecimal getRate(){ return rate; }
    
       public <T extends MonetaryAmount> T apply(T amount){
         // cast should be safe for implementations that adhere to this spec
         return (T)amount.divide(divisor);
       }
    
       public String toString(){...}
    }
  
For simplicity we did not add additional feature such as caching of ``PresentValue`` instances using a
static factory method, or pre-calculation of divisor matrices. Now given the ``MonetaryOperator`` a
present value can be calculated as follows:

    Money m = Money.of("CHF", 1000);
    // present value for an amount of 100, available in two periods,
    // with a rate of 5%.
    Money pv = m.with(new PresentValue(new BigDecimal("0.05"), 2));

#### Performing Currency Conversion

Currency Conversion is also a special case of a ``MonetaryOperator`` since it creates a new amount based
on another amount. The resulting amount will typically have a different currency
and a different numeric amount:

    MonetaryAmount inCHF =...;
    CurrencyConversion conv = MonetaryConversions.getConversion("EUR");
    MonetaryAmount inEUR = inCHF.with(conv);

Also we can define the providers to be used for currency conversion by passing the provider names explicitly:

    CurrencyConversion conv = MonetaryConversions.getConversion("EUR", "EZB", "IMF");

To cover also more complex usage scenarios we can also pass a ``ConversionQuery`` with additional parameters
for conversion, e.g.:

    MonetaryAmount inCHF =...;
    CurrencyConversion conv = MonetaryConversions.getConversion(ConversionQueryBuilder.of()
          .setProviders("CS", "EZB", "IMF")
          .setTermCurrency("EUR")
          .set(MonetaryAmount.class, inCHF, MonetaryAmount.class)
          .setTimestamp(ts)
          .setRateType(RateType.HISTORIC)
          .set(StockExchange.NYSE) // custom type
          .set("contractId", "AA-1234.2")
          .build());
    MonetaryAmount inEUR = inCHF.with(conv);

Testing
-------

The JSR's component tests are based on TestNG. There are tests for the API
singletons, in addition to the reference implementation tests. Additionally
the JSR comes with a TCK, that ensures correct implementation and
interoperability. This TCK must be run against the Java runtime with the
JSR included. It should be executed without any errors without any additional
configuration.
From a runtime perspective the JSR's API currently requires Java SE 8, since
it makes use of lambdas, method references and default methods. When Java ME
would also provide functional features, the API could be usable in ME as well.


Dependencies
------------

The JSR's bootstrap mechanism uses ``java.util.ServiceLoader`` for loading of
backing services. Logging is done using ``java.util.logging``. The currency
providers for ISO currencies are using ``java.util.Currency`` internally,
currently wrapping the ``Currency`` instance into an adapter implementing
``CurrencyUnit``. Alternatively it would be possible to have ``java.util.Currency``
implement ``CurrencyUnit``. Basically most of the methods required are already
present. The additional ``CurrencyContext`` to be returned can be easily
implemented by a static default instance.
The amount implementations provided are based on ``BigDecimal`` (``Money``) and
``long`` (``FastMoney``). ``FastMoney`` additionally also uses ``java.math.Math`` for
arithmetic functions.
The JSR's formatting API internally relies on ``java.text.DecimalFormat`` for
the default formats provided.
The JSR's conversion providers finally are trying to update their datafeeds
on startup. It is arguable, if these providers in the current form should
be added into the JDK. They could easily also provided as an external
Java module or library.

This JEP does not have any dependencies, or other JEPs that depend on it.
