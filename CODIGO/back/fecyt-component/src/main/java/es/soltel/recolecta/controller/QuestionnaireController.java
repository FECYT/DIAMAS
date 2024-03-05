package es.soltel.recolecta.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import es.soltel.recolecta.service.QuestionnaireService;
import es.soltel.recolecta.vo.QuestionnaireVO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = {"https://diamas.fecyt.es"})
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService service;

    @GetMapping
    public List<QuestionnaireVO> getAll() {
        return service.findAllActive();
    }

    @GetMapping("/userId/{id}")
    public List<QuestionnaireVO> getByUserId(@PathVariable Long id) {
        return service.findByUserId(id);
    }

    @GetMapping("/{id}")
    public QuestionnaireVO getById(@PathVariable Long id) {
        return service.findActiveById(id);
    }

    @GetMapping("/evaluation/{id}")
    public QuestionnaireVO findByEvaluationId(@PathVariable Long id) {
        return service.findByEvaluationId(id);
    }

    @PostMapping("/create")
    public QuestionnaireVO create(@RequestBody QuestionnaireVO vo) {
        return service.create(vo);
    }

    @PutMapping("/update")
    public QuestionnaireVO update(@RequestBody QuestionnaireVO vo) {
        return service.update(vo);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(
    		@RequestParam("idType") Long idType,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam("language") String language,
            @RequestParam("format") String format) {
        try {
            byte[] exportedData = service.exportData(idType, startDate, endDate, language, format);

            HttpHeaders headers = new HttpHeaders();
            if ("csv".equals(format)) {
                headers.setContentType(MediaType.parseMediaType("text/csv"));
                headers.setContentDispositionFormData("attachment", "data.csv");
            } else if ("excel".equals(format)) {
                headers.setContentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                headers.setContentDispositionFormData("attachment", "data.xlsx");
            }

            return new ResponseEntity<>(exportedData, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de errores aquí
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/exportFilter")
    public ResponseEntity<byte[]> exportDataFilter(
    		@RequestParam("idType") Long idType,
            @RequestParam("year") Long year,
            @RequestParam("nombre") String nombre,
            @RequestParam("language") String language,
            @RequestParam("format") String format) {
        try {
            byte[] exportedData = service.exportDataFilter(idType, year, nombre, language, format);

            HttpHeaders headers = new HttpHeaders();
            if ("csv".equals(format)) {
                headers.setContentType(MediaType.parseMediaType("text/csv"));
                headers.setContentDispositionFormData("attachment", "data.csv");
            } else if ("excel".equals(format)) {
                headers.setContentType(
                        MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                headers.setContentDispositionFormData("attachment", "data.xlsx");
            }

            return new ResponseEntity<>(exportedData, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Manejo de errores aquí
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/downloadCertificate")
    public List<QuestionnaireVO> exportData(
    		@RequestParam("idType") Long idType, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return service.getCertificatesByDate(idType, date);
    }

    @GetMapping("/certificate/{id}")
    public ResponseEntity<byte[]> exportCertificate(@PathVariable Long id) {
        try {
            byte[] exportedCertificate = service.exportCertificate(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

            return new ResponseEntity<>(exportedCertificate, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/closeevaluation-pdf")
    public ResponseEntity<Resource> closeEvaluationPDF(@RequestParam("pdfFile") MultipartFile pdfFile,
    		@RequestParam("id") Long id,@RequestParam("actionAuthor") Long actionAuthor) {
        try {
            // Obtener los bytes del archivo PDF
            byte[] pdfBlob = pdfFile.getBytes();

            // Crear el archivo ZIP utilizando el método createZip
            byte[] zipData = createZip(pdfBlob, pdfFile.getOriginalFilename());

            // Crear un recurso ByteArrayResource para el archivo ZIP
            ByteArrayResource resource = new ByteArrayResource(zipData);

            // Configurar las cabeceras de la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "processed_documents.zip");
            
            service.closeEvaluationPDF(id, actionAuthor, zipData);

            // Devolver el recurso en el cuerpo de la respuesta
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/process-pdf")
    public ResponseEntity<Resource> processPdf(@RequestParam("pdfFile") MultipartFile pdfFile) {
        try {
            // Obtener los bytes del archivo PDF
            byte[] pdfBlob = pdfFile.getBytes();

            // Crear el archivo ZIP utilizando el método createZip
            byte[] zipData = createZip(pdfBlob, pdfFile.getOriginalFilename());

            // Crear un recurso ByteArrayResource para el archivo ZIP
            ByteArrayResource resource = new ByteArrayResource(zipData);

            // Configurar las cabeceras de la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "processed_documents.zip");

            // Devolver el recurso en el cuerpo de la respuesta
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private byte[] createZip(byte[] pdfBlob, String name) throws Exception {
        // Inicializar un ByteArrayOutputStream para el archivo ZIP
        ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();

        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(pdfBlob));
             ZipOutputStream zip = new ZipOutputStream(zipOutputStream)) {

            // Agregar el PDF original al ZIP
            zip.putNextEntry(new ZipEntry(name + ".pdf"));
            zip.write(pdfBlob);
            zip.closeEntry();

            // Agregar la primera página del PDF como imagen PNG al ZIP
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            byte[] pngData = generatePngForPage(pdfRenderer, 0); // Generar PNG solo para la primera página
            zip.putNextEntry(new ZipEntry(name + ".png"));
            zip.write(pngData);
            zip.closeEntry();

        }

        return zipOutputStream.toByteArray();
    }


    @PostMapping("/process-multiple-pdfs")
    public ResponseEntity<Resource> processMultiplePdfs(@RequestParam("pdfFiles") List<MultipartFile> pdfFiles) {
        try {
            // Inicializar un ByteArrayOutputStream para el archivo ZIP
            ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
            try (ZipOutputStream zip = new ZipOutputStream(zipOutputStream)) {
                // Recorrer la lista de archivos PDF
                for (int i = 0; i < pdfFiles.size(); i++) {
                    MultipartFile pdfFile = pdfFiles.get(i);
                    
                    zip.putNextEntry(new ZipEntry(pdfFiles.get(i).getOriginalFilename() + (i + 1) + ".pdf"));
                    zip.write(pdfFile.getBytes());
                    zip.closeEntry();

                    // Agregar imágenes PNG al ZIP
                    for (int page = 0; page < getPdfPageCount(pdfFile); page++) {
                        byte[] pngData = generatePngForMultiplePages(pdfFile, page);
                        zip.putNextEntry(new ZipEntry(pdfFiles.get(i).getOriginalFilename() + (i + 1) + ".png"));
                        zip.write(pngData);
                        zip.closeEntry();
                    }
                }
            }

            // Crear un recurso ByteArrayResource para el archivo ZIP
            ByteArrayResource resource = new ByteArrayResource(zipOutputStream.toByteArray());

            // Configurar las cabeceras de la respuesta
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "processed_documents.zip");

            // Devolver el recurso en el cuerpo de la respuesta
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    private byte[] generatePngForMultiplePages(MultipartFile pdfFile, int pageNumber) throws Exception {
        try (PDDocument document = PDDocument.load(pdfFile.getInputStream())) {
            // Obtener el renderizador para el documento PDF
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // Renderizar la página como una imagen PNG
            BufferedImage image = pdfRenderer.renderImageWithDPI(pageNumber, 300);

            // Convertir la imagen a bytes
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", pngOutputStream);

            return pngOutputStream.toByteArray();
        }
    }
    
    private int getPdfPageCount(MultipartFile pdfFile) throws Exception {
        try (PDDocument document = PDDocument.load(pdfFile.getInputStream())) {
            return document.getNumberOfPages();
        }
    }

    @PutMapping("closeEvaluation/evaluation/{id}/{actionAuthor}")
    public void closeEvaluation(@PathVariable Long id, @PathVariable Long actionAuthor) {
        service.closeEvaluation(id, actionAuthor);
    }

    private byte[] generatePngForPage(PDFRenderer pdfRenderer, int pageNumber) throws Exception {
        // Renderizar la página como una imagen PNG
        BufferedImage image = pdfRenderer.renderImageWithDPI(pageNumber, 300);

        // Convertir la imagen a bytes
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", pngOutputStream);
        return pngOutputStream.toByteArray();
    }

}
