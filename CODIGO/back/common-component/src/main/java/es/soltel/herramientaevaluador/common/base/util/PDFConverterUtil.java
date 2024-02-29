package es.soltel.herramientaevaluador.common.base.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.springframework.beans.factory.annotation.Value;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import fr.opensagres.poi.xwpf.converter.core.FileURIResolver;
import fr.opensagres.poi.xwpf.converter.core.ImageManager;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;


public class PDFConverterUtil {

    @Value("${lanzador.servicios.enviarMensajesLexnetPendientes.url}")
    protected String rutaImagenes;

    public static String convertDocXToTXT(byte[] doc) throws Exception {


        InputStream myInputStream = new ByteArrayInputStream(doc);
        return convertDocXToTXT(myInputStream);


    }

    private static String convertDocXToTXT(InputStream doc) throws Exception {
        BufferedInputStream is = new BufferedInputStream(doc);
        String text = "";
        // Read the Doc/DOCx file
        XWPFDocument docum = new XWPFDocument(is);
        XWPFWordExtractor ex = new XWPFWordExtractor(docum);
        text = ex.getText();


        return text;
    }

    public static String convertDocXToHTML(InputStream doc) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        //            Dox4j no transforma bien
        //            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(doc);
        //
        //            Docx4J.toHTML(wordMLPackage, "java.io.tmpdir", "java.io.tmpdir", os);
        //
        //            os.flush();
        //            os.close();
        //
        //            return os.toString();

        XWPFDocument document = new XWPFDocument(doc);
        XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(new File("word/media")));

        //Seleccionamos "tempImagesLictor" como carpeta temporal en la ruta de la variable java.io.tmpdir
        options.setImageManager(new ImageManager(new File(System.getProperty("java.io.tmpdir")), "tempImagesLictor"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XHTMLConverter.getInstance().convert(document, out, options);

        return out.toString();

        //return enrutarImagenes(out.toString());

    }


    private static String enrutarImagenes(String escrito) {
        String resultado = escrito;

        String[] srcImagenes = escrito.split("tempImagesLictor");
        //La primera no incluye ninguna imagen
        for (int i = 1; i < srcImagenes.length; i++) {
            String rutaAnterior = "tempImagesLictor" + srcImagenes[i].substring(0, srcImagenes[i].indexOf("\""));
            String srcImagen = System.getProperty("java.io.tmpdir") + "tempImagesLictor"
                    + srcImagenes[i].substring(0, srcImagenes[i].indexOf("\""));
            File imagen = new File(srcImagen);
            String imagenEncriptada;
            try {
                imagenEncriptada = "data:" + Files.probeContentType(imagen.toPath()) + ";base64,"
                        + new String(Base64.getEncoder().encode(Files.readAllBytes(imagen.toPath())));
                resultado = StringUtils.replace(resultado, rutaAnterior, imagenEncriptada);
            }
            catch (IOException e) {
           
                e.printStackTrace();
            }


        }
        return resultado;
    }

    //    private static byte[] convertDocToPDF(InputStream doc) throws Exception {
    //        BufferedInputStream is = new BufferedInputStream(doc);
    //        byte[] resultado = null;
    //        if (FileMagic.valueOf(is) == FileMagic.OLE2) {
    //            throw new OldDocumentException();
    //        }
    //        else if (FileMagic.valueOf(is) == FileMagic.OOXML) {
    //            resultado = convertDocXToPDF(doc);
    //        }
    //
    //        return resultado;
    //    }

    //    private static byte[] convertDocXToPDF(InputStream doc) throws Exception {
    //        ByteArrayOutputStream os = new ByteArrayOutputStream();
    //
    //        try {
    //            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(doc);
    //            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
    //            Docx4J.toPDF(wordMLPackage, os);
    //
    //            os.flush();
    //            os.close();
    //
    //            return os.toByteArray();
    //        }
    //        catch (Exception e) {
    //            throw e;
    //        }
    //        finally {
    //            // close the document  
    //            os.flush();
    //            os.close();
    //        }
    //    }


    //    private static byte[] convertDocOldToPDF(InputStream is) throws Exception {
    //        POIFSFileSystem fs = null;
    //        Document document = new Document();
    //        ByteArrayOutputStream os = new ByteArrayOutputStream();
    //
    //        try {
    //            System.out.println("Starting the test");
    //            fs = new POIFSFileSystem(is);
    //
    //            HWPFDocument doc = new HWPFDocument(fs);
    //            WordExtractor we = new WordExtractor(doc);
    //
    //            //OutputStream file = new FileOutputStream(new File("/Users/pablosalas/Downloads/test.pdf"));
    //
    //            PdfWriter writer = PdfWriter.getInstance(document, os);
    //
    //            Range range = doc.getRange();
    //            document.open();
    //            writer.setPageEmpty(true);
    //            document.newPage();
    //            writer.setPageEmpty(true);
    //
    //            String[] paragraphs = we.getParagraphText();
    //            for (int i = 0; i < paragraphs.length; i++) {
    //
    //                org.apache.poi.hwpf.usermodel.Paragraph pr = range.getParagraph(i);
    //                // CharacterRun run = pr.getCharacterRun(i);
    //                // run.setBold(true);
    //                // run.setCapitalized(true);
    //                // run.setItalic(true);
    //                paragraphs[i] = paragraphs[i].replaceAll("\\cM?\r?\n", "");
    //                System.out.println("Length:" + paragraphs[i].length());
    //                System.out.println("Paragraph" + i + ": " + paragraphs[i].toString());
    //
    //                // add the paragraph to the document  
    //                document.add(new Paragraph(paragraphs[i]));
    //            }
    //            return os.toByteArray();
    //        }
    //        catch (Exception e) {
    //            throw e;
    //        }
    //        finally {
    //            // close the document  
    //            document.close();
    //            os.flush();
    //            os.close();
    //        }
    //    }


    public static byte[] convertOdtToPDF(byte[] doc) throws Exception {


        InputStream myInputStream = new ByteArrayInputStream(doc);
        return convertOdtToPDF(myInputStream);


    }

    public static byte[] convertOdtToPDF(InputStream doc) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            OdfTextDocument document = OdfTextDocument.loadDocument(doc);
            fr.opensagres.odfdom.converter.pdf.PdfOptions options = fr.opensagres.odfdom.converter.pdf.PdfOptions
                    .create().fontEncoding("windows-1250");
            ;
            fr.opensagres.odfdom.converter.pdf.PdfConverter.getInstance().convert(document, out, options);
            out.flush();
            out.close();

            return out.toByteArray();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            // close the document  
            out.flush();
            out.close();
        }
    }

    public static byte[] convertTxtToPDF(byte[] doc) throws Exception {


        InputStream myInputStream = new ByteArrayInputStream(doc);
        return convertTxtToPDF(myInputStream);


    }

    public static byte[] convertTxtToPDF(InputStream contenido) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(contenido));

            String inLine = null;
            StringBuffer sb = new StringBuffer("");

            while ((inLine = inputStream.readLine()) != null) {

                sb.append(inLine);
            }

            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            document.add(new Paragraph(sb.toString()));
            sb.append(inLine);

            document.close();
            out.flush();
            out.close();
            inputStream.close();

            return out.toByteArray();


        }
        catch (Exception e) {
            throw e;
        }
        finally {
            // close the document 
            out.flush();
            out.close();
        }

    }


    //    public static byte[] convertHtmlToPDF(InputStream doc) throws Exception {
    //
    //        try {
    //            Document document = new Document();
    //            ByteArrayOutputStream out = new ByteArrayOutputStream();
    //
    //            PdfWriter writer = PdfWriter.getInstance(document, out);
    //
    //            document.open();
    //            HTMLWorker htmlWorker = new HTMLWorker(document);
    //            Reader targetReader = new InputStreamReader(doc);
    //            htmlWorker.parse(targetReader);
    //            document.close();
    //            return out.toByteArray();
    //        }
    //        catch (Exception e) {
    //            throw new Exception("Error conviertiendo el documento HTML a PDF", e);
    //        }
    //    }


}
