package com.daa.service;

import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.daa.util.Utility;

import android.util.Log;

/**
 * Parse the response retrieved from the server and transformed into 
 * element node equivalent.
 * @author DaaSys
 *
 */
public class ServiceParser {

	public static Element getDocumentElement(String result) {		
		InputStream streamToParse = Utility.stringToInputStream(result);
		if(streamToParse == null) return null;					
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document dom = builder.parse(streamToParse);
			Element root = dom.getDocumentElement();	
			return root;
		} catch(Exception ex) {
			Log.e("ServiceParser::", "getDocumentElement()" + ex.getMessage());
			return null;
		}	
	}
}
