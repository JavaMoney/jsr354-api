package javax.money;

/**
 * Component that implements the most basic financial formulas as in use currently, for details also
 * check ({@link http://www.financeformulas.net} for further details.
 * 
 * @author Anatole Tresch
 */
public interface MoneyMath {

	/**
	 * Present Value (PV) is a formula used in Finance that calculates the present day value of an
	 * amount that is received at a future date. The premise of the equation is that there is
	 * "time value of money".
	 * 
	 * @return
	 */
	public Amount presentValue(Amount amount, Number returnRate, Number periods);
	
	/**
	 * The simple interest formula is used to calculate the interest accrued on a loan or savings
	 * account that has simple interest. The simple interest formula is fairly simple to compute and
	 * to remember as principal times rate times time. An example of a simple interest calculation
	 * would be a 3 year saving account at a 10% rate with an original balance of $1000. By
	 * inputting these variables into the formula, $1000 times 10% times 3 years would be $300.
	 * 
	 * Simple interest is money earned or paid that does not have compounding. Compounding is the
	 * effect of earning interest on the interest that was previously earned. As shown in the
	 * previous example, no amount was earned on the interest that was earned in prior years.
	 * 
	 * As with any financial formula, it is important that rate and time are appropriately measured
	 * in relation to one another. If the time is in months, then the rate would need to be the
	 * monthly rate and not the annual rate.
	 * 
	 * @return
	 */
	public Amount simpleInterest(Amount amount, Number units, Number interestRate);

	/**
	 * The compound interest formula calculates the amount of interest earned on an account or
	 * investment where the amount earned is reinvested. By reinvesting the amount earned, an
	 * investment will earn money based on the effect of compounding.
	 * 
	 * Compounding is the concept that any amount earned on an investment can be reinvested to
	 * create additional earnings that would not be realized based on the original principal, or
	 * original balance, alone. The interest on the original balance alone would be called simple
	 * interest. The additional earnings plus simple interest would equal the total amount earned
	 * from compound interest.
	 * 
	 * @param units
	 * @param interestRate
	 * @return
	 */
	public Amount compoundInterest(Amount amount, Number units, Number interestRate);

	/**
	 * Future Value (FV) is a formula used in finance to calculate the value of a cash flow at a
	 * later date than originally received. This idea that an amount today is worth a different
	 * amount than at a future time is based on the time value of money.
	 * 
	 * The time value of money is the concept that an amount received earlier is worth more than if
	 * the same amount is received at a later time. For example, if one was offered $100 today or
	 * $100 five years from now, the idea is that it is better to receive this amount today. The
	 * opportunity cost for not having this amount in an investment or savings is quantified using
	 * the future value formula. If one wanted to determine what amount they would like to receive
	 * one year from now in lieu of receiving $100 today, the individual would use the future value
	 * formula. See example at the bottom of the page.
	 * 
	 * @return
	 */
	public Amount futureValue(Amount amount, Number returnRate, Number periods);
	
	// to be continued...???
}
