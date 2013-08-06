package com.github.rossrkk.stocksparser;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class StockParser
{
    public static void main(String[] args) 
    {
        try {
            new StockParser().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() throws Exception
    {
        URL url = new URL("http://spreadsheets.google.com/feeds/list/0AhySzEddwIC1dEtpWF9hQUhCWURZNEViUmpUeVgwdGc/1/public/basic?sq=symbol=ADN.L");
        URLConnection connection = url.openConnection();

        Document doc = parseXML(connection.getInputStream());
        NodeList descNodes = doc.getElementsByTagName("content");

        for(int i=0; i<descNodes.getLength();i++)
        {
            String content = descNodes.item(i).getTextContent();
            int length = content.length();
            int start = content.lastIndexOf(":");
            double out = Double.parseDouble(content.substring(start + 2, length));
            System.out.println(out);
        }
    }

    private Document parseXML(InputStream stream)
    throws Exception
    {
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try
        {
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

            doc = objDocumentBuilder.parse(stream);
        }
        catch(Exception ex)
        {
            throw ex;
        }       

        return doc;
    }
}