import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;


public class XFDLNamespaceContext implements NamespaceContext {

	@Override
	public String getNamespaceURI(String prefix) {
		if (prefix == null) throw new NullPointerException("Invalid Namespace Prefix");
		else if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX))
			return "http://www.PureEdge.com/XFDL/6.5";
		else if ("custom".equals(prefix))
			return "http://www.PureEdge.com/XFDL/Custom";
		else if ("designer".equals(prefix)) 
			return "http://www.PureEdge.com/Designer/6.1";
		else if ("pecs".equals(prefix)) 
			return "http://www.PureEdge.com/PECustomerService";
		else if ("xfdl".equals(prefix))
			return "http://www.PureEdge.com/XFDL/6.5";		
		else if ("xforms".equals(prefix)) 
			return "http://www.w3.org/2003/xforms";
		else 	
			return XMLConstants.NULL_NS_URI;
	}

	@Override
	public String getPrefix(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator getPrefixes(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
