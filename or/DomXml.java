

import java.io.File;
import java.util.*;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Attr;
import org.xml.sax.SAXException;

/*
* OR Paramateric transition case
*/

public class DomXml {

	public static void main(String argv[]) {

	   try {
	   	long startTime = System.currentTimeMillis(); // Used to measure execution time later	
		String filepath = "file4.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);

		// Get the root element
		Node company = doc.getFirstChild();
		String saveTarget = "test";
		String saveStr = "test";
		int nosens = 3; // Hard coding number of sensors.. tada
		String theEvent = "Person.inPresence_OR_LogPos";
		// Get the staff element , it may not working if tag has spaces, or
		// whatever weird characters in front...it's better to use
		// getElementsByTagName() to get it directly.
		// Node staff = company.getFirstChild();

		// Setting default number of sensors as 3 and the parameter 'get_sensor_value0' 

		NodeList nList = doc.getElementsByTagName("outgoingTransitions");
		int tempLen = nList.getLength();
		getRandom get = new getRandom();
		String[] outId = new String[50];
		
		for(int tempId =0; tempId < nosens-1; tempId++) { // -1 as we already have one outgoing transition, need to add nosens-1
			outId[tempId] = get.getMe();
		}

		for(int temp = 0; temp < tempLen; temp++) {
			Node nNode = nList.item(temp);
			Element eElement = (Element) nNode;
			if((theEvent).equals(eElement.getAttribute("specification"))) {
				System.out.println(eElement.getAttribute("specification"));
				String target = eElement.getAttribute("target");
				String speci = eElement.getAttribute("specification");

				String str = eElement.getAttribute("xmi:id");
	
				Node parent = nNode.getParentNode();
					saveStr = str;

				for (int temp23 = 0; temp23 < nosens-1; temp23++) {

					Element age = doc.createElement("outgoingTransitions");
					Attr attr = doc.createAttribute("specification");
					attr.setValue(speci);
					age.setAttributeNode(attr);
					Attr attr1 = doc.createAttribute("target");
					attr1.setValue(target);
					age.setAttributeNode(attr1);
					Attr attr2 = doc.createAttribute("xmi:id");
					attr2.setValue(outId[temp23]);
					age.setAttributeNode(attr2);
					parent.appendChild(age);

					saveTarget = target;
					saveStr = saveStr + " " + outId[temp23];
				}

				System.out.println("Done!");
				break;
			}
			
		}

		NodeList nList1 = doc.getElementsByTagName("vertices");
		int tempLen1 = nList1.getLength();
		
		for(int temp1 = 0; temp1 < tempLen1; temp1++) {
			Node nNode1 = nList1.item(temp1);
			Element eElement1 = (Element) nNode1;
			
			if(saveTarget.equals(eElement1.getAttribute("xmi:id"))) {
				System.out.println(eElement1.getAttribute("incomingTransitions"));
				System.out.println(saveStr);
				eElement1.setAttribute("incomingTransitions", saveStr);
			}
		}

/*
		// Get the staff element by tag name directly
		Node staff = doc.getElementsByTagName("staff").item(0);

		// update staff attribute
		NamedNodeMap attr = staff.getAttributes();
		Node nodeAttr = attr.getNamedItem("id");
		nodeAttr.setTextContent("2");

		// append a new node to staff
		Element age = doc.createElement("age");
		age.appendChild(doc.createTextNode("28"));
		staff.appendChild(age);


		// loop the staff child node
		NodeList list = staff.getChildNodes();

		for (int i = 0; i < list.getLength(); i++) {
			
                   Node node = list.item(i);

		   // get the salary element, and update the value
		   if ("salary".equals(node.getNodeName())) {
			node.setTextContent("2000000");
		   }

                   //remove firstname
		   if ("firstname".equals(node.getNodeName())) {
			staff.removeChild(node);
		   }

		}
*/

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(filepath));
		transformer.transform(source, result);

		System.out.println("Done");
		long endTime   = System.currentTimeMillis();
		System.out.println("Execution time");
		System.out.println(endTime - startTime);

	   } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	   } catch (TransformerException tfe) {
		tfe.printStackTrace();
	   } 
	   catch (IOException ioe) {
		ioe.printStackTrace();
	   } catch (SAXException sae) {
		sae.printStackTrace();
	   }
	}
	private static class getRandom
	{
		public String getMe() {
			return "_"+UUID.randomUUID().toString().substring(0,22);
		}
	}
}
