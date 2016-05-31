
import java.io.File;
import java.io.IOException;
import java.util.*;
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
* AND Parameter Transition case
*/

public class DomXml {

	public static void main(String argv[]) {


	   try { 
	   	long startTime = System.currentTimeMillis(); // Used to measure execution time later

		String filepath = "file3.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(filepath);

		// Number of sensors- 
		int numberSensors = 2;
		String eventName = "Person.inInsulin_AND_Average";
		String laterEventName = "Person.checkInsulin";
		// Get the root element
		Node company = doc.getFirstChild();

		// Random string genearting object
		getRandom get = new getRandom();

		// Assign Ids
		String sync1Id = get.getMe();
		String sync2Id = get.getMe();
		String regStateId = get.getMe();
		String aggStateId = get.getMe();
		// Flexible states ids.. depending on number of sensors
		String[] innerState1 = new String[50];
		String[] innerState2 = new String[50];

		//innerState1[0] = get.getMe();
		//innerState1[1] = get.getMe();
		//innerState2[0] = get.getMe();
		//innerState2[1] = get.getMe();


		// Save all the vertices' id's in an array
		NodeList nList1 = doc.getElementsByTagName("vertices");
		int tempLen1 = nList1.getLength();
		String[] vertId = new String[100];
		String[] vertInco = new String[100];
		String[] vertName = new String[100];

		// Capture to be directed edges
		String topVert = "Top";
		String bottomVert = "Bottom";
	//	String[] noStateSensors = new String[100];
		
		// Storing the related nodes - To be stored as Hashmap in future iterations
		Node aboveNode = company;
		String aboveNodeName = "init";
		String aboveNodeId = "init";
		Node belowNode = company;
		String belowNodeName = "init";
		String belowNodeId = "init";

		Node transAbove = company;

		String topStateName = "init";
		String bottomStateName = "init";
		String topStateId = "init";
		String bottomStateId = "init";

		String outAboveState = get.getMe();
		// The multi-region state transitions divided into multiple sync-groups
		String[] outSync1 = new String[100]; 
		String[] outSync2 = new String[100]; 
		String[] outSync3 = new String[100]; 

		//outSync1[0] = get.getMe();		
		//outSync1[1] = get.getMe();		
		//outSync2[0] = get.getMe();
		//outSync2[1] = get.getMe();
		//outSync3[0] = get.getMe();
		//outSync3[1] = get.getMe();

		/// Would always be two innerState, for now
			

		for(int tempInner = 0 ; tempInner < numberSensors; tempInner++) {
			outSync1[tempInner] = get.getMe();
			outSync2[tempInner] = get.getMe();
			outSync3[tempInner] = get.getMe();
			innerState2[tempInner] = get.getMe();
			innerState1[tempInner] = get.getMe();
		}
		innerState2[numberSensors] = get.getMe();
		innerState2[numberSensors] = get.getMe();

		String outAlways = get.getMe();
		String outProceed = get.getMe(); 

		for (int temp1 = 0; temp1 < tempLen1; temp1++) {
			Node nNode1 = nList1.item(temp1);
			Element eElement1 = (Element) nNode1;

			vertId[temp1] = eElement1.getAttribute("xmi:id");
			vertInco[temp1] = eElement1.getAttribute("incomingTransitions");
			vertName[temp1] = eElement1.getAttribute("name");
			System.out.println(vertId[temp1]);
		}

		System.out.println("Vertices");
		/*for(int t = 0; t < vertId.length && vertId[t] != null; t++) {
			System.out.println("id");
			System.out.println(vertId[t]);
			System.out.println("Incoming transitions");
			System.out.println(vertInco[t]);
		}
*/
		NodeList nList = doc.getElementsByTagName("outgoingTransitions");
		int tempLen = nList.getLength();
		int tapId = 0;

		for (int temp = 0; temp < tempLen; temp++) {
			Node nNode = nList.item(temp);
			Element eElement = (Element) nNode;

			/**
			** Person.event0 is an AND sensor parameter
			** Set Person.event0 as default
			** Capture some id's and name's here
			**/
			if((eventName).equals(eElement.getAttribute("specification"))) { 
				Node parent1 = nNode.getParentNode();
				Element parVert = (Element) parent1;
				topStateName = parVert.getAttribute("name");
				topStateId = parVert.getAttribute("xmi:id");

				transAbove = nList.item(temp-1);
				Element transElemn = (Element) transAbove;
				System.out.println("the transitions node=");
				System.out.println(transElemn.getAttribute("xmi:id"));

				//Should destroy the node now? or later usign the loop
				String outTarget = eElement.getAttribute("target");
				//get bottom state

				for(int teVe = 0; teVe < vertId.length && (vertId[teVe] != null); teVe++) {
					System.out.println(vertId[teVe]);
					System.out.println(!vertId[teVe].equals(null));
					if(!vertId[teVe].equals(null)) {
						if (outTarget.equals(vertId[teVe])) {
							bottomStateName = vertName[teVe];
							bottomStateId = vertId[teVe];
							belowNodeId = vertId[teVe + 1];
							System.out.println(belowNodeId);
							break;
						}

						if (topStateId.equals(vertId[teVe])) {
							aboveNodeId = vertId[teVe-1];
						}
					}

				}

			}
		}

		/*
		** Capture above & below nodes
		** Going through the same vertice loop 
		*/


		for (int temp1 = 0; temp1 < tempLen1; temp1++) {
			Node nNode1 = nList1.item(temp1);
			Element eElement1 = (Element) nNode1;
			System.out.println(belowNodeId);
			System.out.println(eElement1);

			if(eElement1 != null) {
				if(belowNodeId.equals(eElement1.getAttribute("xmi:id"))) {
					belowNode = nNode1;
				}

				if (aboveNodeId.equals(eElement1.getAttribute("xmi:id"))) {
					aboveNode = nNode1;
				}
			}
		//	vertId[temp1] = eElement1.getAttribute("xmi:id");
		//	vertInco[temp1] = eElement1.getAttribute("incomingTransitions");
		//	vertName[temp1] = eElement1.getAttribute("name");

		}

		// Had to remove nodes in separate loops else throws java.lang.NullPointerException in loop 'temp1' int

		for (int temp1 = 0; temp1 < tempLen1; temp1++) {
			Node nNode1 = nList1.item(temp1);
			Element eElement1 = (Element) nNode1;
			
			// Remove the above-below to AND parameter vertices -will re-generate inside region vertices later
			if(eElement1 != null) {
				if(bottomStateId.equals(eElement1.getAttribute("xmi:id"))) {
					eElement1.getParentNode().removeChild(eElement1);
				}
			}

		}

		for (int temp1 = 0; temp1 < tempLen1; temp1++) {
			Node nNode1 = nList1.item(temp1);
			Element eElement1 = (Element) nNode1;
			
			// Remove the above-below to AND parameter vertices -will re-generate inside region vertices later
			if(eElement1 != null) {
				if(topStateId.equals(eElement1.getAttribute("xmi:id"))) {
					eElement1.getParentNode().removeChild(eElement1);
				}	
			}
		}

		NodeList nList2 = doc.getElementsByTagName("regions");
		int tempLen2 = nList2.getLength();

		for (int temp2 = 0; temp2 < tempLen2; temp2++) {
			Node nNode2 = nList2.item(temp2);
			Element eElement2 = (Element) nNode2;

			if(("main region").equals(eElement2.getAttribute("name"))) {
				//In the main region

				//Create Sync vertices
				getVertices sync1 = new getVertices(doc, "sgraph:Synchronization", sync1Id, outAboveState , ""); //later fill in incoming transition
				
				System.out.println("outAboveState");
				System.out.println(outAboveState);

				Node syncNode = sync1.returnNode();

//				System.out.println(sync1.returnMe());
				getOutTrans[] getOutArr = new getOutTrans[50];
				for(int trTrans = 0; trTrans < numberSensors; trTrans++) {
					getOutArr[trTrans] = new getOutTrans(doc, outSync1[trTrans], "", innerState1[trTrans]);
					syncNode.appendChild(getOutArr[trTrans].returnMe());
				}

//				getOutTrans outgoing1 = new getOutTrans(doc, outSync1[0], "", innerState1[0]); //later fill in target
//				getOutTrans outgoing2 = new getOutTrans(doc, outSync1[1], "", innerState1[1]);

				//System.out.println(outgoing1.returnMe());
//				syncNode.appendChild(outgoing1.returnMe());
//				syncNode.appendChild(outgoing2.returnMe());
			
				nNode2.appendChild(syncNode); 

				//generate Region's vertice
				getVertices regVert = new getVertices(doc, "sgraph:State", get.getMe(), "" , "Sensors"); 
				// get Element  regVert.returnMe() later
				// Node of regVert, needed to append everything
				Node nodeRegVert = regVert.returnNode();

				/* flexible section */
				// Can be as many numbe rof time as you want, depending on the number of AND sensors available
				//region1 inside the region-vertice
	
				getRegion[] regMe = new getRegion[50];
				Node[] nodeTempReg = new Node[50];

				for (int tempRegNo = 0 ; tempRegNo < numberSensors; tempRegNo++) {
					String tempRegName = "r" + tempRegNo;
					regMe[tempRegNo] =  new getRegion(doc, get.getMe(), tempRegName);

					//Vertices & Transitions to append
					getVertices reg1Vert11 = new getVertices(doc, "sgraph:State", innerState2[tempRegNo], outSync2[tempRegNo], bottomStateName);//State0 //set incomingtransition
					getOutTrans reg1Vert1Out11 = new getOutTrans(doc, outSync3[tempRegNo], laterEventName, sync2Id);//set target
					Node vertNode11 = reg1Vert11.returnNode();
					vertNode11.appendChild(reg1Vert1Out11.returnMe());

					getVertices reg1Vert22 = new getVertices(doc, "sgraph:State", innerState1[tempRegNo], outSync1[tempRegNo], topStateName); //Idle
					getOutTrans reg1Vert2Out22 = new getOutTrans(doc, outSync2[tempRegNo], eventName, innerState2[tempRegNo]);
					Node vertNode22 = reg1Vert22.returnNode();
					vertNode22.appendChild(reg1Vert2Out22.returnMe());

					nodeTempReg[tempRegNo] = regMe[tempRegNo].returnNode();
					nodeTempReg[tempRegNo].appendChild(vertNode11);
					nodeTempReg[tempRegNo].appendChild(vertNode22);
				
					nodeRegVert.appendChild(nodeTempReg[tempRegNo]);
				}
				nNode2.appendChild(nodeRegVert);
				/* flexible section ends */

				// The Sync End function State
				String tempSync2Out = outSync3[0];
				for(int tempOutNo = 1 ; tempOutNo < numberSensors; tempOutNo++) {
					tempSync2Out =tempSync2Out + " " +outSync3[tempOutNo]; 
				}
			//	String tempSync2Out = outSync3[0] + " " + outSync3[1];
				getVertices syncVert = new getVertices(doc, "sgraph:Synchronization", sync2Id, tempSync2Out, "");
				Node syncNode1 = syncVert.returnNode();

				getOutTrans outtrans1 = new getOutTrans(doc, outProceed, "proceed", aggStateId);
				syncNode1.appendChild(outtrans1.returnMe());

				// Aggregate node
				getVertices aggVert = new getVertices(doc, "sgraph:State", aggStateId, outProceed, "aggregate");
				Node aggNode = aggVert.returnNode();

				getOutTrans outtrans2 = new getOutTrans(doc, outAlways, "always", belowNodeId);
				aggNode.appendChild(outtrans2.returnMe());

				nNode2.appendChild(syncNode1);
				nNode2.appendChild(aggNode);
			}
		}


		// Set target, incoming transition for top & bottom nodes
		Element aboveTransNodeElem = (Element) transAbove;
		Element belowNodeElem = (Element) belowNode;

		aboveTransNodeElem.setAttribute("target", sync1Id);
		aboveTransNodeElem.setAttribute("xmi:id", outAboveState);
		belowNodeElem.setAttribute("incomingTransitions", outAlways);
		System.out.println("end in");
		System.out.println(aboveTransNodeElem.getAttribute("target"));
		System.out.println(aboveTransNodeElem.getAttribute("xmi:id"));
		//Remove all notation-children nodes- Are regeneragted later & remove, re-add notation:diagram -> the simpler action
		NodeList nList3 = doc.getElementsByTagName("notation:Diagram");
		int tempLen3 = nList3.getLength();

		Node nodeNotelem = nList3.item(0);
		Element nodeElemNote = (Element) nodeNotelem;

		String noteElement = nodeElemNote.getAttribute("element");
		String notemeasurementUnit = nodeElemNote.getAttribute("measurementUnit");
		String notetype = nodeElemNote.getAttribute("type");
		String noteXmi = nodeElemNote.getAttribute("xmi:id");

		for (int temp3 = 0; temp3 < tempLen3; temp3++) {
			Node nNode3 = nList3.item(temp3);
			Element eElement3 = (Element) nNode3;

			if(eElement3 != null) {
					eElement3.getParentNode().removeChild(eElement3);
			}

		}

		Element elem24 = doc.createElement("notation:Diagram");
		Attr attr24 = doc.createAttribute("element");
		attr24.setValue(noteElement);
		elem24.setAttributeNode(attr24);
		Attr attr25 = doc.createAttribute("measurementUnit");
		attr25.setValue(notemeasurementUnit);
		elem24.setAttributeNode(attr25);
		Attr attr26 = doc.createAttribute("type");
		attr26.setValue(notetype);
		elem24.setAttributeNode(attr26);
		Attr attr27 = doc.createAttribute("xmi:id");
		attr27.setValue(noteXmi);
		elem24.setAttributeNode(attr27);

		NodeList nList4 = doc.getElementsByTagName("xmi:XMI");
		Node tempXMINode = nList4.item(0);

		tempXMINode.appendChild(elem24);		

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
	   } 
	   catch (TransformerException tfe) {
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

	// Class to generate infinite Vertices
	private static class getVertices 
	{
		Element elem;
		public getVertices(Document doc, String str1, String str2, String str3, String str4) {
			elem = doc.createElement("vertices");
					Attr attr = doc.createAttribute("xsi:type");
					attr.setValue(str1);
					elem.setAttributeNode(attr);
					Attr attr1 = doc.createAttribute("xmi:id");
					//System.out.println(getSalt);
					attr1.setValue(str2);
					elem.setAttributeNode(attr1);
					Attr attr2 = doc.createAttribute("incomingTransitions");
					attr2.setValue(str3); //Set incoming transition to sync later
					elem.setAttributeNode(attr2);
					Attr attr3 = doc.createAttribute("name");
					attr3.setValue(str4);
					elem.setAttributeNode(attr3);

		} 
		public Element returnMe() {
			return elem;
		}
		public Node returnNode () {
			return (Node) elem;
		}
	}


	// Class to generate region
	private static class getRegion
	{
		Element elem;
		public getRegion(Document doc, String str1, String str2) {
			elem = doc.createElement("regions");
			Attr attr1 = doc.createAttribute("xmi:id");
			attr1.setValue(str1);
			elem.setAttributeNode(attr1);
			Attr attr2 = doc.createAttribute("name");
			attr2.setValue(str2);
			elem.setAttributeNode(attr2);
		}

		public Element returnMe() {
			return elem;
		}
		public Node returnNode () {
			return (Node) elem;
		}
	}

	// Class to generate infinite Outgoing transitions
	private static class getOutTrans
	{
		Element elem;
		public getOutTrans(Document doc, String str1, String str2, String str3) {
				elem = doc.createElement("outgoingTransitions");
				Attr attr3 = doc.createAttribute("xmi:id");
				attr3.setValue(str1);
				elem.setAttributeNode(attr3);
				Attr attr4 = doc.createAttribute("specification");
				attr4.setValue(str2);
				elem.setAttributeNode(attr4);
				Attr attr5 = doc.createAttribute("target");
				attr5.setValue(str3);
				elem.setAttributeNode(attr5);
		}
		public Element returnMe(){
			return elem;
		}
		public Node returnNode () {
			return (Node) elem;
		}
	}	

}


