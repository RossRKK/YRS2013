package com.github.rossrkk.stocksparser;

//source https://spreadsheets.google.com/feeds/list/0AhySzEddwIC1dEtpWF9hQUhCWURZNEViUmpUeVgwdGc/1/public/basic?sq=symbol%3DADN.L
//Example1.java to test set up
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;



public class Example1 extends DefaultHandler {
	
	  
    static InputStream inputStream;

	static StringBuffer sb = new StringBuffer(1024);
	private static CharArrayWriter contents = new CharArrayWriter();
	public static String name;
	public static InputStream is;

    // default parser to use
    private static String parserClass = "org.apache.xerces.parsers.SAXParser";
   
    //override methods of the DefaultHandler class
    // to gain notification of sax events
    // see org.xml.sax.ContentHandler for all available events
    // 
    public void startDocument() throws SAXException {
	sb.append("sax event: startDocument <br>");
    }// end startDocument

    public void endDocument() throws SAXException{
    	sb.append("sax event: endDocument <br>");
    }// end endDocument

    public void startElement(String namespaceURI, String localName,
			 String qName, Attributes attr) throws SAXException {
    	contents.reset();
    	
    //	contents.append("sax event: startElement[ " + localName + " ] <br>" );
	// also lets print out the attributes if there are any:
	for (int i = 0; i < attr.getLength();i++){
		contents.append("Attribute: " + 
			       attr.getLocalName(i) +
			       " VALUE: " + attr.getValue(i));
	}// end of for loop
    } // end of startElement

    public void endElement(String namespaceURI, String localName, String qName)
	           throws SAXException{
    	if (localName.equals("content")){
    	    name = contents.toString();
    	}
    //	contents.append("sax event: endElement [ " + localName + " ] <br>");
    }// end endElement

    public void characters(char[] ch, int start, int length) 
                  throws SAXException{
    	//contents.append("sax event: characters[ " );

	try {
		contents.write(ch, start, length);
		//sb.append(contents.toString());
		//System.out.println("contents: " + contents.toString());
		//System.out.println("sb in characters: " +  sb.toString());
//	    OutputStreamWriter outw = new OutputStreamWriter (System.out);
//	    outw.write(ch, start,length);
//	    outw.flush();
	
	}catch (Exception e) {
	    e.printStackTrace();
	}
	//contents.append(" ] <br> ");
  
    }// end characters

    public static void main(String[] argv) throws MalformedURLException{
  //  public String Parse(String path) {
    	StringBuffer sb2 = new StringBuffer(1024);
    	
    	URL url;
        url = new URL("http://spreadsheets.google.com/feeds/list/0AhySzEddwIC1dEtpWF9hQUhCWURZNEViUmpUeVgwdGc/1/public/basic?sq=symbol=ADN.L");
    	try {  
            Socket socket = new Socket(url.getHost(), 80);  
    
            // Send request   
              
            OutputStream outputStream =  
              socket.getOutputStream();  
            PrintWriter out = new PrintWriter(outputStream);  
            out.print("GET " +url.getPath() + " HTTP/1.0\r\n\n");  
            out.flush();  
    
            // Fetch response  
            inputStream =  
              socket.getInputStream();  
            InputStreamReader inputStreamReader =  
              new InputStreamReader(inputStream);  
            BufferedReader in =  
              new BufferedReader(inputStreamReader);//pas conseille pour xaland ne supporte pas buffereader  
    
            StringBuffer buffer = new StringBuffer();  
           String line;  
             
             
             
             
           while ((line = in.readLine()) != null) {  
                 
               buffer.append(line).append("\n");  
                
                 
           }  
          //   System.out.println(line.substring(indice));   
           String str = buffer.toString();  
             
           int indice=0;  
           char symbole='<';  
           while(indice<str.length())  
           {  
            if(str.charAt(indice)!=symbole) indice++;  
            else break;  
           }  
                 
                 
          //   System.out.println(str.substring(indice,str.length()));   
                 
                 
                 
             is= new ByteArrayInputStream(str.substring(indice,str.length()).getBytes("UTF-8"));  
                 
               //org.w3c.dom.Document document = parseXmlDom(is);  
                 
               //System.out.println("Root Node Name: "  
                //         + document.getChildNodes().item(0).getNodeName());  
    
    
                  //NodeList children = document.getChildNodes();  
                  /*for (int i = 0; i < children.getLength(); i++)  
                  {  
                  String  child = children.item(i).getNodeName();  
                      
                  System.out.println(child);  
                  }  */
    
            // Close everything  
            out.close();  
            in.close();  
            socket.close();  
    
          } catch (IOException e) {  
          } 
    	sb2.append("example1 sax events: <br>");
	try {
	    // create sax2 parser
	    XMLReader xr = XMLReaderFactory.createXMLReader(parserClass);

	    // set the ContentHandler
	    xr.setContentHandler(new Example1());

	    // parse the file
	    sb.append("add me ");
	    //System.out.println("preParse sb: " + sb.toString());
	    xr.parse(new InputSource(is));
	    //System.out.println("postParse sb: " + sb.toString());
		sb2.append("contents from parse: " + contents.toString());
		int length = name.length();
		int colon = name.lastIndexOf(":");
		name = name.substring(colon + 2, length);
		System.out.println(name);
	//	System.out.println(sb2);
	}catch (Exception e) {
	     e.printStackTrace();
        }
	
	//return sb2.toString();
    }// end main
}// end of Example1