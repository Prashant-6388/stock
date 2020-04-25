import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

public class XMLGenerator {
	
	public static final String TEMPLATE_CLOSING_TAG = "</xsl:template>";
	public static final String STYLESHEET_CLOSING_TAG = "</xsl:stylesheet>";
	public static final String NEWLINE = "\n";
	public static final String XLST_COPY_OPENING_TAG = "<xsl:copy>";
	public static final String XLST_COPY_CLOSING_TAG = "</xsl:copy>";
	/**
	 * @param args
	 */
	public static void main(String args[]) {
		XStream xstream = new XStream();
		xstream.alias("cat", Cat.class);

//		String template = new XMLGenerator().templateCreater(new Cat(4,"Garfield"));
//		System.out.println(prettyFormat(template,"2"));
		try {
			File template = new File(
					"C:\\Users\\Prashant\\Documents\\workspace-sts-3.9.10.RELEASE\\Test\\src\\CatTemplate.xslt");
			TraxSource traxSource = new TraxSource(new Cat(4, "Garfield"), xstream);
			Writer buffer = new StringWriter();
//			Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(IOUtils.toInputStream(template)));
			Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(template));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(traxSource, new StreamResult(buffer));
			System.out.println("Buf = \n" + buffer.toString());
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		 
	}

	public <T> String templateCreater(T object) {
		StringBuilder sb = new StringBuilder();
		sb.append(createXSLTHeader());
		sb.append(createTemplateMatchElement(object.getClass().getName().toLowerCase())).append(NEWLINE);
		sb.append(XLST_COPY_OPENING_TAG);
		List<String> nodeMap = createNodeMap(object);
		nodeMap.forEach( (s) -> 
				sb.append(createXSLTselectElement(s, null)).append(NEWLINE)
			);
		sb.append(XLST_COPY_CLOSING_TAG);
		sb.append(TEMPLATE_CLOSING_TAG).append(NEWLINE);
		sb.append(STYLESHEET_CLOSING_TAG);
		
		System.out.println(sb.toString());
		return sb.toString();
	}

	private String createXSLTselectElement(String nodeName, String value) {
		/*
		 * <id> <xsl:apply-templates select="id" /> </id>
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("<"+nodeName+"><xsl:value-of select=\""+nodeName+"\" /></"+nodeName+">");
		return sb.toString();
	}
	
	public String createXSLTHeader() {
		StringBuilder sb  = new StringBuilder("<xsl:stylesheet version=\"1.0\"\r\n" + 
				"xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\r\n" + 
				"<xsl:output method=\"xml\" omit-xml-declaration=\"yes\"\r\n" + 
				"indent=\"no\" />\r\n" + 
				"");
		return sb.toString();
	}
	public String createTemplateMatchElement(String className) {
		StringBuilder sb = new StringBuilder("<xsl:template match=\"/"+className+"\">");
		return sb.toString();
	}

	public <T>  List<String> createNodeMap(T object) {
		Field[] properties = object.getClass().getDeclaredFields();
//		StandardEvaluationContext spelContext = new StandardEvaluationContext();
//		spelContext.setVariable("object", object);
//		ExpressionParser parser = new SpelExpressionParser();
		List<String> nodeMap = new ArrayList<>();
		for (Field field : properties) {
//			Expression expression = parser.parseExpression("#object." + field.getName());
//			nodeMap.put(field.getName(), expression.getValue(spelContext).toString());
			nodeMap.add(field.getName());
		}
		return nodeMap;
	}
	
	public static String prettyFormat(String input, String indent) {
		Source xmlInput = new StreamSource(new StringReader(input));
		StringWriter stringWriter = new StringWriter();
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", indent);
			transformer.transform(xmlInput, new StreamResult(stringWriter));

			return stringWriter.toString().trim();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}


