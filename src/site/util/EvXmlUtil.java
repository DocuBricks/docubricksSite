/***
 * Copyright (C) 2010 Johan Henriksson
 * This code is under the Endrov / BSD license. See www.endrov.net
 * for the full text and how to cite.
 */
package site.util;

import java.io.*;
import java.util.List;


import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * Utility functions for XML
 * @author Johan Henriksson
 */
public class EvXmlUtil
	{

	public static Document readXML(File filename) throws IOException, JDOMException
		{
//		FileInputStream fileInputStream = new FileInputStream(filename);
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(filename);
		return document;
		}

	public static class NoOpEntityResolver implements EntityResolver 
		{
	  @SuppressWarnings("deprecation")
		public InputSource resolveEntity(String publicId, String systemId) 
	  	{
	    return new InputSource(new StringBufferInputStream(""));
	  	}
		}

	public static Document readXML(Reader c) throws Exception
		{
		@SuppressWarnings("deprecation")
		SAXBuilder saxBuilder = new SAXBuilder(false); //No validation
		//saxBuilder.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false); //xerxes only
		saxBuilder.setEntityResolver(new NoOpEntityResolver()); //http://www.jdom.org/docs/faq.html#a0350
		Document document = saxBuilder.build(c);
		return document;
		}

	public static List<Element> getChildrenE(Element root, String name)
		{
		return root.getChildren(name);
		}
	public static List<Element> getChildrenE(Element root)
		{
		return root.getChildren();
		}
	
	public static String prettyPrint(Element e)
		{
		try
			{
			//TODO not sure if this works
			Document doc=new Document((Element)e.clone()); //need to clone?
			return xmlToString(doc);
			}
		catch (Exception e1)
			{
			e1.printStackTrace();
			return null;
			}
/*		
		
		
		StringBuffer b=new StringBuffer();
		prettyPrint(e, b);
		return b.toString();*/
		}
	public static void prettyPrint(Element e,StringBuffer b)
		{
		b.append("<"+e.getName()+">");
		for(Object o:e.getChildren())
			prettyPrint((Element)o,b);
		b.append("</"+e.getName()+">");
		}
	
	
	/**
	 * Checks if name and attributes equal, not content
	 */
	public static boolean elementsEqual(Element a, Element b)
		{
		if(a.getName().equals(b.getName()))
			{
			for(Object o:a.getAttributes())
				{
				Attribute attra=(Attribute)o;
				Attribute attrb=b.getAttribute(attra.getName());
				if(attrb==null)
					return false;
				if(!attra.getValue().equals(attrb.getValue()))
					return false;
				}
			for(Object o:b.getAttributes())
				{
				Attribute attrb=(Attribute)o;
				Attribute attra=a.getAttribute(attrb.getName());
				if(attra==null)
					return false;
				if(!attrb.getValue().equals(attra.getValue()))
					return false;
				}
			return true;
			}
		else
			return false;
		}

	/**
	 * Merge two XML-documents
	 * @param to
	 * @param from
	 */
	public static void mergeXML(Element to, Element from)
		{
		//For every element to be added
		for(Object o:from.getChildren())
			{
			Element frome=(Element)o;
			
			//Find matching element in destination
			Element match=null;
			for(Object o2:to.getChildren())
				{
				Element toe=(Element)o2;
				if(elementsEqual(frome, toe))
					{
					match=toe;
					break;
					}
				}
	
			//Add content
			if(match==null)
				to.addContent((Element)frome.clone());
			else
				mergeXML(match, frome);
			}
		}

	/**
	 * Write XML-document to disk
	 */
	public static void writeXmlData(Document doc, File file) throws Exception
		{
		//FileWriter writer = new FileWriter(file);
		FileOutputStream writer2=new FileOutputStream(file);
		writeXmlData(doc, writer2);
		writer2.close();
		}

	/**
	 * Write XML-document to stream
	 */
	public static void writeXmlData(Document doc, OutputStream os) throws Exception
		{
		Format format=Format.getPrettyFormat();
		XMLOutputter outputter = new XMLOutputter(format);
		outputter.output(doc, os);
		}
	
	/**
	 * Write XML-document to disk
	 */
	public static String xmlToString(Document doc) throws Exception
		{
		Format format=Format.getPrettyFormat();
		XMLOutputter outputter = new XMLOutputter(format);
		StringWriter writer = new StringWriter();
		outputter.output(doc, writer);
		return writer.toString();
		}
	
	
	public static Element stringToXml(String s) throws Exception
		{
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(new StringReader(s));
		Element element = document.getRootElement();
		return element;
		}

	
	}
