//import brut.androlib.Androlib;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class xslt {

    private static xslt xsl = new xslt();
    public xslt(){}
    public static xslt getInstance(){
        return xsl;
    }
    /*
        File xslFile   配置文件
        File configXml 原始文件
        File finalConfigXml 合并文件
        File finalResXml  结果文件
     */
    private void Use(File xslFile,File configXml,File finalConfigXml,File finalResXml) throws IOException {

        mergeXml(xslFile,configXml,finalConfigXml,finalResXml);
    }

    private static void mergeXml(File xslFile, File mergeFile, File finalConfigXml,File finalResXml) throws IOException {

        DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document destDoc;

        FileInputStream input;
        try {
            builder = dbFac.newDocumentBuilder();
            destDoc =  builder.parse(finalConfigXml);

            input = new FileInputStream(xslFile);
            StreamSource source = new StreamSource(input);
            Transformer transformer = TransformerFactory.newInstance().newTransformer(source);
            // 为xsl文件设置变量"mergeFile"，将mergeXml文件的路径传递给xsl
            transformer.setParameter("mergeFile", mergeFile.getAbsoluteFile());
//            transformer.transform(new StreamSource(finalConfigXml), new StreamResult(finalResXml));
            transformer.transform(new DOMSource(destDoc), new StreamResult(finalResXml));




        } catch (Exception e) {
            e.printStackTrace();
        }


    }








    public static void main(String[] args) throws IOException {
//        配置文件
        File xslFile = new File("C:\\Users\\Administrator\\Desktop\\manifestTemplate.xsl");
//        合并文件
        File configXml = new File("C:\\Users\\Administrator\\IdeaProjects\\untitled1\\src\\111.xml");
//        原始的文件
        File finalConfigXml = new File("C:\\Users\\Administrator\\IdeaProjects\\untitled1\\src\\222.xml");
//        结果文件
        File finalResXml = new File("C:\\Users\\Administrator\\IdeaProjects\\untitled1\\src\\res.xml");
        xslt xs =xslt.getInstance();
        xs.Use(xslFile,configXml,finalConfigXml,finalResXml);
        InputStreamReader read = new InputStreamReader(new FileInputStream(finalResXml), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = bufferedReader.readLine();

//---------------------------------------------------------------------------------------------------------


//
//        Androlib  androlib = new Androlib();
//        //检查androidManifest
//        File appDir = new File("d:/");
//        File manifest = new File("C:\\Users\\Administrator\\IdeaProjects\\untitled1\\src\\res.xml");
//        File manifestOriginal = new File("d:/", "AndroidManifest.xml.orig");
//
//        androlib.buildManifestFile(appDir,manifest,manifestOriginal);
//
//


    }


}
