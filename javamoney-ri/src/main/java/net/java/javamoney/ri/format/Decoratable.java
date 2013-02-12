package net.java.javamoney.ri.format;

public interface Decoratable<T> {
	
	public void setDecorator(FormatDecorator<T> decorator);

	public FormatDecorator<T> getDecorator();
	
}
