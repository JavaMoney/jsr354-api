package net.java.javamoney.ri.ext.provider.icu.res;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.java.javamoney.ri.ext.provider.icu.Currency4Region;
//import net.java.javamoney.ri.ext.provider.icu.CurrencyData;
//import net.java.javamoney.ri.ext.provider.icu.Territory;
//
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//public final class CLDRSupplementalData extends AbstractXmlResource {
//
//	private static final CLDRSupplementalData INSTANCE = createInstance();
//
//	private Map<String, CurrencyData> currencyData;
//	private Map<String, List<Currency4Region>> currencyRegionData;
//
//	private Map<String, Territory> codesByType;
//	private Map<String, Territory> codesByAlpha3;
//	private Map<String, Territory> codesByNumericCode;
//	private Map<String, Territory> codesByFips10;
//
//	private CLDRSupplementalData() throws MalformedURLException {
//		super(
//				"CLDR-SupplementalData",
//				new URL(
//						"http://unicode.org/repos/cldr/trunk/common/supplemental/supplementalData.xml"),
//				CLDRSupplementalData.class
//						.getResource("java-money/defaults/SupplementalData.xml"));
//	}
//
////	@Produces
//	public static CLDRSupplementalData getInstance() {
//		return INSTANCE;
//	}
//
//	private static CLDRSupplementalData createInstance() {
//		try {
//			return new CLDRSupplementalData();
//		} catch (MalformedURLException e) {
//			throw new IllegalStateException(
//					"Error creating CLDR SupplementalData.", e);
//		}
//	}
//
//	@Override
//	protected void documentReloaded() {
//		// load currencies
//		Map<String, CurrencyData> currencyDataMap = new HashMap<String, CurrencyData>();
//		Map<String, List<Currency4Region>> currencyRegionData = new HashMap<String, List<Currency4Region>>();
//
//		NodeList nl = getDocument().getDocumentElement()
//				.getElementsByTagName("currencyData");
//
//		for (int i = 0; i < nl.getLength(); i++) {
//			Node node = nl.item(i);
//			if ("fractions".equals(node.getNodeName())) {
//				NodeList infoNodes = node.getChildNodes();
//				for (int j = 0; j < infoNodes.getLength(); j++) {
//					CurrencyData cd = new CurrencyData(infoNodes.item(j));
//					currencyDataMap.put(cd.getCode(), cd);
//				}
//			}
//			else if ("region".equals(node.getNodeName())) {
//				// <region iso3166="AF">
//				// <currency iso4217="AFN" from="2002-10-07"/>
//				// <currency iso4217="AFA" from="1927-03-14" to="2002-12-31"/>
//				String regionCode = node.getAttributes()
//						.getNamedItem("iso3166").getNodeValue();
//				NodeList currencyNodes = node.getChildNodes();
//				for (int j = 0; j < currencyNodes.getLength(); j++) {
//					Currency4Region currencyEntry = new Currency4Region(
//							currencyNodes.item(j));
//					CurrencyData cd = currencyDataMap.get(currencyEntry
//							.getCurrencyCode());
//					if (cd == null) {
//						cd = new CurrencyData(currencyEntry.getCurrencyCode());
//						currencyDataMap
//								.put(currencyEntry.getCurrencyCode(), cd);
//					}
//					cd.addCurrencyEntry(currencyEntry);
//					List<Currency4Region> regionEntries = currencyRegionData
//							.get(regionCode);
//					if (regionEntries == null) {
//						regionEntries = new ArrayList<Currency4Region>();
//						currencyRegionData.put(regionCode, regionEntries);
//					}
//					regionEntries.add(currencyEntry);
//				}
//			}
//		}
//		this.currencyData = currencyDataMap;
//		this.currencyRegionData = currencyRegionData;
//		// load territory codes
//		Map<String, Territory> codesByType = new HashMap<String, Territory>();
//		Map<String, Territory> codesByAlpha3 = new HashMap<String, Territory>();
//		Map<String, Territory> codesByNumericCode = new HashMap<String, Territory>();
//		Map<String, Territory> codesByFips10 = new HashMap<String, Territory>();
//		nl = getDocument().getDocumentElement()
//				.getElementsByTagName("codeMappings");
//		nl = ((Element) nl.item(0)).getChildNodes();
//		for (int i = 0; i < nl.getLength(); i++) {
//			Node node = nl.item(i);
//			if (!"territoryCodes".equals(node.getNodeName())) {
//				continue;
//			}
//			Territory codes = new Territory(node);
//			if (codes.getAlpha3Code() != null) {
//				codesByAlpha3.put(codes.getAlpha3Code(), codes);
//			}
//			if (codes.getFips10Code() != null) {
//				codesByFips10.put(codes.getFips10Code(), codes);
//			}
//			if (codes.getNumericCode() != null) {
//				codesByNumericCode.put(codes.getNumericCode(), codes);
//			}
//			if (codes.getCode() != null) {
//				codesByType.put(codes.getCode(), codes);
//			}
//		}
//		this.codesByType = codesByType;
//		this.codesByAlpha3 = codesByAlpha3;
//		this.codesByNumericCode = codesByNumericCode;
//		this.codesByFips10 = codesByFips10;
//	}
//
//	public CurrencyData getCurrencyData(String currencyCode) {
//		return this.currencyData.get(currencyCode);
//	}
//
//	public List<Currency4Region> getCurrencyEntries(String countryCode) {
//		return this.currencyRegionData.get(countryCode);
//	}
//
//	public Territory getTerritoryCodesByCode(String code) {
//		return this.codesByType.get(code);
//	}
//
//	public Territory getTerritoryCodesByFips10(String fips10) {
//		return this.codesByFips10.get(fips10);
//	}
//
//	public Territory getTerritoryCodesByAlpha3(String alpha3) {
//		return this.codesByAlpha3.get(alpha3);
//	}
//
//	public Territory getTerritoryCodesByNumericCode(String numericCode) {
//		return this.codesByNumericCode.get(numericCode);
//	}
//
//	public Collection<Territory> getTerritoryCodes() {
//		return codesByType.values();
//	}
//
//}
