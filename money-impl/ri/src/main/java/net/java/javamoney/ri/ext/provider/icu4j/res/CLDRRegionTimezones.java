/*
 * Copyright (c) 2012, 2013, Credit Suisse (Anatole Tresch), Werner Keil.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * Contributors: Anatole Tresch - initial implementation. Werner Keil -
 * extension and adjustment.
 */
package net.java.javamoney.ri.ext.provider.icu4j.res;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//public class CLDRRegionTimezones {
//
//	private String language;
//
//	private CLDRWindowsZones resource = CLDRWindowsZones.getInstance();
////	<supplementalData>
////	<version number="$Revision$"/>
////	<generation date="$Date$"/>
////	<windowsZones>
////	<mapTimezones otherVersion="7dd0000" typeVersion="2013a">
////	<!--  (UTC-12:00) International Date Line West  -->
////	<mapZone other="Dateline Standard Time" territory="001" type="Etc/GMT+12"/>
//	
//	public CLDRRegionTimezones(String language) {
//		this.language = language;
//		load();
//	}
//
//	public void load() {
//		Map<String, CurrencyData> currencyDataMap = new HashMap<String, CurrencyData>();
//		Map<String, List<CurrencyEntry>> currencyRegionData = new HashMap<String, List<CurrencyEntry>>();
//
//		NodeList nl = resource.getDocument().getDocumentElement()
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
//					CurrencyEntry currencyEntry = new CurrencyEntry(
//							currencyNodes.item(j));
//					CurrencyData cd = currencyDataMap.get(currencyEntry
//							.getCurrencyCode());
//					if (cd == null) {
//						cd = new CurrencyData(currencyEntry.getCurrencyCode());
//						currencyDataMap
//								.put(currencyEntry.getCurrencyCode(), cd);
//					}
//					cd.addCurrencyEntry(currencyEntry);
//					List<CurrencyEntry> regionEntries = currencyRegionData
//							.get(regionCode);
//					if (regionEntries == null) {
//						regionEntries = new ArrayList<CurrencyEntry>();
//						currencyRegionData.put(regionCode, regionEntries);
//					}
//					regionEntries.add(currencyEntry);
//				}
//			}
//		}
//		this.currencyData = currencyDataMap;
//		this.currencyRegionData = currencyRegionData;
//	}
//
//	public CurrencyData getCurrencyData(String currencyCode) {
//		return this.currencyData.get(currencyCode);
//	}
//
//	public List<CurrencyEntry> getCurrencyEntries(String countryCode) {
//		return this.currencyRegionData.get(countryCode);
//	}
//
//	public static final class CurrencyData {
//
//		private String code;
//		private Integer digits;
//		private Integer rounding;
//		private Integer cashRounding;
//		private boolean tender;
//		private List<CurrencyEntry> currencyEntries = new ArrayList<CurrencyEntry>();
//
//		public CurrencyData(Node node) {
//			Node attrNode = node.getAttributes()
//					.getNamedItem("digits");
//			this.digits = attrNode != null ? Integer.valueOf(attrNode
//					.getNodeValue()) : null;
//			attrNode = node.getAttributes()
//					.getNamedItem("rounding");
//			this.rounding = attrNode != null ? Integer.valueOf(attrNode
//					.getNodeValue()) : null;
//			attrNode = node.getAttributes()
//					.getNamedItem("cashRounding");
//			this.cashRounding = attrNode != null ? Integer.valueOf(attrNode
//					.getNodeValue()) : null;
//			attrNode = node.getAttributes()
//					.getNamedItem("iso4217");
//			this.code = attrNode != null ? attrNode.getNodeValue() : null;
//			attrNode = node.getAttributes()
//					.getNamedItem("tender");
//			this.tender = attrNode != null ? Boolean.parseBoolean(attrNode
//					.getNodeValue()) : true;
//		}
//
//		public void addCurrencyEntry(CurrencyEntry currencyEntry) {
//			this.currencyEntries.add(currencyEntry);
//		}
//
//		public CurrencyData(String currencyCode) {
//			this.code = currencyCode;
//		}
//
//		/**
//		 * @return the type
//		 */
//		public final String getCode() {
//			return code;
//		}
//
//		/**
//		 * @return the numeric
//		 */
//		public final Integer getDigits() {
//			return digits;
//		}
//
//		/**
//		 * @return the alpha3
//		 */
//		public final Integer getRounding() {
//			return rounding;
//		}
//
//		/**
//		 * @return the fips10
//		 */
//		public final Integer getCashRounding() {
//			return cashRounding;
//		}
//
//		/**
//		 * 
//		 * @return
//		 */
//		public final boolean isTender() {
//			return tender;
//		}
//
//		/*
//		 * (non-Javadoc)
//		 * 
//		 * @see java.lang.Object#toString()
//		 */
//		@Override
//		public String toString() {
//			return "CurrencyData [code=" + code + ", digits=" + digits
//					+ ", rounding=" + rounding + ", cashRounding="
//					+ cashRounding + "]";
//		}
//
//	}
//
//	public static final class CurrencyRegion {
//		private String regionCode;
//		private List<CurrencyEntry> currencyMapping = new ArrayList<CurrencyEntry>();
//
//	}
//
//	public static final class CurrencyEntry {
//		private String code;
//		private String from;
//		private String to;
//
//		public CurrencyEntry(Node node) {
//			code = node.getAttributes().getNamedItem("iso4217").getNodeValue();
//			from = node.getAttributes().getNamedItem("from").getNodeValue();
//			Node toNode = node.getAttributes().getNamedItem("to");
//			if (toNode != null) {
//				to = toNode.getNodeValue();
//			}
//		}
//
//		public String getCurrencyCode() {
//			return code;
//		}
//
//		public String getFrom() {
//			return from;
//		}
//
//		public String getTo() {
//			return to;
//		}
//	}
//
//}
