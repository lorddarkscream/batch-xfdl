 import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DA2062 extends XFDLDocument {

	
	public static String DA2062_FIELD_TO = "TO";
	public static String DA2062_FILED_FROM = "FROM";
	public static String DA2062_FIELD_RECPTNR = "RECPTNR";
	public static String DA2062_FIELD_ENDITMS ="ENDITEMS";
	public static String DA2062_FIELD_ITEMDES ="ITEMDES";
	public static String DA2062_FIELD_PUBNR = "PUBNR";
	public static String DA2062_FIELD_PUBDATE = "PUBDATE";
	public static String DA2062_FIELD_QUANTITY = "QUANTITY";
	
	private static final String FORM_NUMBER = "2062";
		
	public DA2062(String inputFile) throws IOException,
			ParserConfigurationException, SAXException, InvalidFormException {
		super(inputFile);
		
		if(!super.getFormNumber().equals(FORM_NUMBER)) {
			throw new InvalidFormException("Specified file is not a DA Form 2062");
		}
	}
	
	public void printFields() {
		NodeList nl = doc.getDocumentElement().getElementsByTagName("field");
		for(int x=0; x<nl.getLength(); x++) {
			System.out.println(nl.item(x).
					getAttributes().getNamedItem(XFDL_ATTRIBUTE_SID));
		}
	}
	
	/**
	 * Adds an additional page to the 2062
	 * @throws XPathExpressionException XPath error while searching for
	 * 		specified field.
	 * @throws InvalidFieldException Unable to locate specified field
	 * 		in document.  Should only occur if the format of the 
	 * 		document has changed or it is not a standard DA2062. 
	 */
	public void addPage() throws XPathExpressionException, InvalidFieldException {
		//get the second page of the document
		NodeList pages = doc.getElementsByTagName(XFDL_ELEMENT_PAGE);
		Node pageTwo = pages.item(1);
		
		//clone it
		Node newPage = pageTwo.cloneNode(true);
		
		//replace SID on <page> tag.
		newPage.getAttributes().getNamedItem(XFDL_ATTRIBUTE_SID).setNodeValue("PAGE" + (getPageCount()+1));
		
		//append after the current last page
		doc.getDocumentElement().appendChild(newPage);
		
		//empty the contents of all of the fields, change the SIDs
		String fieldsOnPageQuery = XFDL_ELEMENT_PAGE + "[@" + XFDL_ATTRIBUTE_SID + '=' + 
		"'" + newPage.getAttributes().getNamedItem(XFDL_ATTRIBUTE_SID).getNodeValue() 
		+ "']" + "/field";
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList newFields = 
			(NodeList)xPath.evaluate(
					fieldsOnPageQuery, 
					doc.getDocumentElement(), 
					XPathConstants.NODESET);
		
		String newSID;
		char pageLetter = (char)((int)'A' - 1 + getPageCount());
		final String oldLetter = "B"; 
		
		for(int x=0; x<newFields.getLength(); x++) {
			
			//replace page letter
			newSID = changePageLetter(newFields.item(x).getAttributes().
					getNamedItem(XFDL_ATTRIBUTE_SID).getNodeValue(),
					oldLetter,
					pageLetter);
				
			newFields.item(x).getAttributes().
					getNamedItem(XFDL_ATTRIBUTE_SID).
					setNodeValue(newSID.toString());
			this.setFieldValue(newSID.toString(), "");
			
			
			Node next = (Node)xPath.evaluate(XFDL_QUERY_NEXT, 
					newFields.item(x), XPathConstants.NODE);
			Node previous = (Node)xPath.evaluate(XFDL_QUERY_PREVIOUS, 
					newFields.item(x), XPathConstants.NODE);
			
			next.setNodeValue(updateNextPreviousField(next, pageLetter));
			previous.setNodeValue(updateNextPreviousField(previous, pageLetter));
		}
		
		//replace the label text to be 1 of #, 2 of #, etc
		String labelQuery = "//page/global/label";
		
		NodeList labels = 
			(NodeList)xPath.evaluate(
					labelQuery, 
					doc.getDocumentElement(), 
					XPathConstants.NODESET);
		for (int x = 0; x< labels.getLength(); x++) {
			String newLabel = labels.item(x).getTextContent();
			newLabel = newLabel.replace(
					newLabel.substring(newLabel.indexOf("Page")), 
					"Page " + (x + 1) + " of " + getPageCount());
			labels.item(x).setTextContent(newLabel);
			
		}
	}
	
	private String changePageLetter(String original, String oldLetter, char newLetter) {
		StringBuilder newSID = new StringBuilder(original);
			int oldCharIndex = newSID.lastIndexOf(oldLetter);
			
			if(oldCharIndex > 0) {
				newSID.setCharAt(oldCharIndex, newLetter);
			}
			return newSID.toString();
	}
	
	private String updateNextPreviousField(Node n, char pageLetter) {
		StringBuilder val = new StringBuilder(n.getNodeValue());
		int underscore = val.lastIndexOf("_");
		if(underscore == -1) {
			val.setCharAt(val.length() - 1, pageLetter);
		} else {
			val.setCharAt(underscore - 1, pageLetter);
		}
		
		return val.toString();
	}

	/**
	 * Sets the To Field of the 2062
	 * @param to Value for the To Field
	 * @throws XPathExpressionException Error in XPath while searching for the
	 * 		to field.
	 * @throws InvalidFieldException Unable to find the To field in document.
	 * 		Should only occur if document is not a standard DA2062 or standard
	 * 		has changed. 
	 */
	public void setTo(String to) 
	throws XPathExpressionException, InvalidFieldException {
		super.setFieldValue(DA2062_FIELD_TO, to);
	}
	
	/**
	 * Sets the From Field of the 2062
	 * @param from Value for the From Field
	 * @throws XPathExpressionException Error in XPath while searching for the
	 * 		From Field.
	 * @throws InvalidFieldException Unable to find the From field in document.
	 * 		Should only occur if document is not a standard DA2062 or standard
	 * 		has changed.
	 */
	public void setFrom(String from) 
	throws XPathExpressionException, InvalidFieldException {
		super.setFieldValue(DA2062_FILED_FROM, from);
	}
	
	
	/**
	 * Sets the Receipt Number Field of the 2062
	 * @param from Value for the From Field
	 * @throws XPathExpressionException Error in XPath while searching for the
	 * 		RECPTNR.
	 * @throws InvalidFieldException Unable to find the RECPTNR field in document.
	 * 		Should only occur if document is not a standard DA2062 or standard
	 * 		has changed.
	 */
	public void setReceiptNumber(String receptnr) 
	throws XPathExpressionException, InvalidFieldException {
		super.setFieldValue(DA2062_FIELD_RECPTNR, receptnr);
	}
	
	/**
	 * Sets the End Items Field of the 2062
	 * @param from Value for the From Field
	 * @throws XPathExpressionException Error in XPath while searching for the
	 * 		ENDITMS field.
	 * @throws InvalidFieldException Unable to find the ENDITMS field in 
	 * 		document.  Should only occur if document is not a standard 
	 * 		DA2062 or standard has changed.
	 */
	public void setEndItem(String enditems) 
	throws XPathExpressionException, InvalidFieldException {
		super.setFieldValue(DA2062_FIELD_ENDITMS, enditems);
	}
	
	/**
	 * Sets the Item Description Field of the 2062
	 * @param from Value for the From Field
	 * @throws XPathExpressionException Error in XPath while searching for the
	 * 		ITEMDES.
	 * @throws InvalidFieldException Unable to find the Item Description field 
	 * 		in document. Should only occur if document is not a standard 
	 * 		DA2062 or standard has changed.
	 */
	public void setItemDescription(String itemdes) 
	throws XPathExpressionException, InvalidFieldException {
		super.setFieldValue(DA2062_FIELD_ITEMDES, itemdes);
	}
	
	/**
	 * Sets the Publication Number Field of the 2062
	 * @param from Value for the From Field
	 * @throws XPathExpressionException Error in XPath while searching for the
	 * 		PUBNR.
	 * @throws InvalidFieldException Unable to find the Publication Number 
	 * 		field in document. Should only occur if document is not a 
	 * 		standard DA2062 or standard	has changed.
	 */
	public void setPublicationNumber(String pubnr) 
	throws XPathExpressionException, InvalidFieldException {
		super.setFieldValue(DA2062_FIELD_PUBNR, pubnr);
	}
	
	/**
	 * Sets the Publication Date Field of the 2062
	 * @param from Value for the From Field
	 * @throws XPathExpressionException Error in XPath while searching for the
	 * 		PUBDATE.
	 * @throws InvalidFieldException Unable to find the Publication Date 
	 * 		field in document. Should only occur if document is not a 
	 * 		standard DA2062 or standard	has changed.
	 */
	public void setPublicationDate(String pubdate) 
	throws XPathExpressionException, InvalidFieldException {
		super.setFieldValue(DA2062_FIELD_PUBDATE, pubdate);
	}

	/**
	 * Sets the Quantity Field of the 2062
	 * @param from Value for the From Field
	 * @throws XPathExpressionException Error in XPath while searching for the
	 * 		QUANTITY.
	 * @throws InvalidFieldException Unable to find the Quantity 
	 * 		field in document. Should only occur if document is not a 
	 * 		standard DA2062 or standard	has changed.
	 */
	public void setQuantity(String pubdate) 
	throws XPathExpressionException, InvalidFieldException {
		super.setFieldValue(DA2062_FIELD_QUANTITY, pubdate);
	}

}
