package mvc.controller;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import gamePieces.Direction;
import gamePieces.GridPoint;

public class XMLHandler extends DefaultHandler{
	
	private boolean isRabbit, isFox, isMushroom, isRow, isCol, isDirec;
	private int row, col;
	private Direction direc;
	private TilePlayBoard board;
	
	public XMLHandler() {
		board = new TilePlayBoard();
		isRabbit = false;
		isFox = false;
		isMushroom = false;
		isRow = false;
		isCol = false;
		isDirec = false;
	}

	/**
	 * @param board the board to set
	 */
	public void setBoard(TilePlayBoard board) {
		this.board = board;
	}
	
    public void toXMLFile(String str) throws SAXException {
		SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		
		try {
			TransformerHandler handler = factory.newTransformerHandler();
			Transformer transformer = handler.getTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			Result result = new StreamResult(str + ".xml");
			handler.setResult(result);
			
			AttributesImpl attr = new AttributesImpl();
			
			handler.startDocument();
			handler.startElement("", "", "Pieces", attr);
			attr.clear();
			
			//store rabbits in XML
			for(int i=0; i<board.getRabbits().size(); i++) {
				attr.addAttribute("", "", "id", "", String.valueOf(i));
				handler.startElement("", "", "rabbit", attr);
				
				attr.clear();
				handler.startElement("", "", "row", attr);
				handler.characters((board.getRabbits().get(i).getRow()+"").toCharArray(), 0, 1);
				handler.endElement("", "", "row");
				
				attr.clear();
				handler.startElement("", "", "col", attr);
				handler.characters((board.getRabbits().get(i).getCol()+"").toCharArray(), 0, 1);
				handler.endElement("", "", "col");
				
				handler.endElement("", "", "rabbit");	
			}
			
			//store foxes in XML
			for(int i=0; i<board.getFoxes().size(); i++) {
				attr.addAttribute("", "", "id", "", String.valueOf(i));
				handler.startElement("", "", "fox", attr);
				
				attr.clear();
				handler.startElement("", "", "row", attr);
				handler.characters((board.getFoxes().get(i).getHead().getRow()+"").toCharArray(), 0, 1);
				handler.endElement("", "", "row");
				
				attr.clear();
				handler.startElement("", "", "col", attr);
				handler.characters((board.getFoxes().get(i).getHead().getCol()+"").toCharArray(), 0, 1);
				handler.endElement("", "", "col");
				
				attr.clear();
				handler.startElement("", "", "direction", attr);
				handler.characters((board.getFoxes().get(i).getOrientation().ordinal()+"").toCharArray(), 0, 1);
				handler.endElement("", "", "direction");
				
				handler.endElement("", "", "fox");	
			}
			
			//store mushroom in XML
			for(int i=0; i<board.getMushrooms().size(); i++) {
				attr.addAttribute("", "", "id", "", String.valueOf(i));
				handler.startElement("", "", "mushroom", attr);
				
				attr.clear();
				handler.startElement("", "", "row", attr);
				handler.characters((board.getMushrooms().get(i).getRow()+"").toCharArray(), 0, 1);
				handler.endElement("", "", "row");
				
				attr.clear();
				handler.startElement("", "", "col", attr);
				handler.characters((board.getMushrooms().get(i).getCol()+"").toCharArray(), 0, 1);
				handler.endElement("", "", "col");
				
				handler.endElement("", "", "mushroom");	
			}
			
			handler.endElement("", "", "Pieces");
			handler.endDocument();
		
		}catch(TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
    }
    
 
    //import default games with number
    public void importXMLFile(int num) throws Exception{
    	SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();	
		
		saxParser.parse(new File("game" + num + ".xml"), this);
		
    }
    
    //import saved games with name
    public void importXMLFileByName(String name) throws Exception{
    	SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();	
		
		saxParser.parse(new File(name+".xml"), this);
		
    }
    
    public void readSAX(File f) throws Exception{
    	SAXParserFactory spf = SAXParserFactory.newInstance();
    	SAXParser s = spf.newSAXParser();
    	s.parse(f, this);
    }
    	
    @Override
    public void startElement(String u, String in, String qName, Attributes a) throws SAXException {
    	
    	super.startElement(u, in, qName, a);
    
		if("rabbit".equals(qName)) {
			isRabbit = true;
		}
		else if("fox".equals(qName)) {
			isFox = true;
		}
		else if("mushroom".equals(qName)) {
			isMushroom = true;
		}
		else if("row".equals(qName)) {
			isRow = true;
		}
		else if("col".equals(qName)) {
			isCol = true;
		}
		else if("direction".equals(qName)) {
			isDirec = true;
		}
		
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
    	if(qName.equals("rabbit") && isRabbit) {
    		this.board.setRabbit(row, col);
    		isRabbit = false;
    	}
    	else if(qName.equals("fox") && isFox) {
    		this.board.setFox(new GridPoint(row, col), direc);
    		isFox = false;
    	}
    	else if(qName.equals("mushroom") && isMushroom) {
    		this.board.setMushroom(row, col);
    		isMushroom = false;
    	}
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException{
    	
    	super.characters(ch, start, length);
    	
    	String text = new String(ch, start, length);
    	
		if(isRow) {
			row = Integer.parseInt(text);
			isRow = false;
		}
		else if(isCol) {
			col = Integer.parseInt(text);
			isCol = false;
		}
		else if(isDirec) {
			direc = Direction.values()[Integer.parseInt(text)];
			isDirec = false;
		}
    }   
    
    public TilePlayBoard getBoard() {
    	return this.board;
    }
    
}
