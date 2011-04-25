import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 */

/**
 * @author andrew.nortrup
 * Class creates a model of the DA Form 1594 DAILY STAFF JOURNAL OR DUTY OFFICER'S LOG.
 * Model based on XFDL document downloaded from 
 * http://armypubs.army.mil/eforms/da1300_to_da2199_1.html on 23APR2011
 */
public class DA1594 extends XFDLDocument {
	
	private static final String FORM_NUMBER = "1524";
	
	/**
	 * Creates a DA1594 object.
	 * @param inputFile XFDL file to be manipulated
	 * @throws IOException Thrown if file cannot be read.
	 * @throws ParserConfigurationException Thrown if the XML cannot be parsed.
	 * @throws SAXException Thrown if the XML cannot be parsed properly.
	 * @throws InvalidFormException Thrown if the form is not a DA 1594.
	 */
	public DA1594(String inputFile) throws IOException,
		ParserConfigurationException, 
		SAXException, 
		InvalidFormException
	{
	
		super(inputFile);
		
		if(super.getFormNumber().equals(FORM_NUMBER)) {
			throw new InvalidFormException("Specified file is not a DA Form 1524");
		}
		
	}
	
	/**
	 * Adds an additional page to the 1594
	 * @throws XPathExpressionException Error in XPath while searching for the
	 * 		to field.
	 * @throws InvalidFieldException Unable to find the To field in document.
	 * 		Should only occur if document is not a standard DA1594 or standard
	 * 		has changed.
	 */
	public void addPage() throws XPathExpressionException, InvalidFieldException {
		//get the second page of the document
		NodeList pages = doc.getElementsByTagName(XFDL_ELEMENT_PAGE);
		Node pageTwo = pages.item(0);
		
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
		
		for(int x=0; x<newFields.getLength(); x++) {
			
			//replace page letter
			newSID = changePageLetter(newFields.item(x).getAttributes().
						getNamedItem(XFDL_ATTRIBUTE_SID).getNodeValue(),
					pageLetter);
				
			newFields.item(x).getAttributes().
					getNamedItem(XFDL_ATTRIBUTE_SID).
					setNodeValue(newSID.toString());
			this.setFieldValue(newSID.toString(), "");
			
			
			Node next = (Node)xPath.evaluate(XFDL_QUERY_NEXT, 
					newFields.item(x), XPathConstants.NODE);
			Node previous = (Node)xPath.evaluate(XFDL_QUERY_PREVIOUS, 
					newFields.item(x), XPathConstants.NODE);
			
			next.setNodeValue(changePageLetter(next.getNodeValue(), pageLetter));
			previous.setNodeValue(changePageLetter(previous.getNodeValue(), pageLetter));
		}
		
	}
	
	/**
	 * Inserts a page letter before the underscore "_" in page fields or at
	 * end of field name.
	 * @param original
	 * @param oldLetter
	 * @param newLetter
	 * @return
	 */
	private String changePageLetter(String original, char newLetter) {
		
		String underscore = "_";
		
		StringBuilder newSID = new StringBuilder(original);
			int underscoreIndex = newSID.lastIndexOf(underscore);
			
			if(underscoreIndex > 0) {
				newSID.insert(underscoreIndex - 1, newLetter);
			} else {
				newSID.append(newLetter);
			}
			return newSID.toString();
	}
}
