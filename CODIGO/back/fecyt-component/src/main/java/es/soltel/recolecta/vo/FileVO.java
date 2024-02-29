package es.soltel.recolecta.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileVO {

    private Long id;
    private String normalizedDocumentName;
    private String fileFormat;
    private Long fileSize;
    private LocalDateTime aswerDateTime;
    private String fileHash;
    private String filePath;
    @JsonProperty("nDeleteState")
    private Integer nDeleteState;
    private QuestionnaireQuestionVO questionnaireQuestion;

    // getters and setters...

        public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNormalizedDocumentName() {
        return normalizedDocumentName;
    }

    public void setNormalizedDocumentName(String normalizedDocumentName) {
        this.normalizedDocumentName = normalizedDocumentName;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getAswerDateTime() {
        return aswerDateTime;
    }

    public void setAswerDateTime(LocalDateTime aswerDateTime) {
        this.aswerDateTime = aswerDateTime;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }

    public QuestionnaireQuestionVO getQuestionnaireQuestion() {
        return questionnaireQuestion;
    }

    public void setQuestionnaireQuestion(QuestionnaireQuestionVO questionnaireQuestion) {
        this.questionnaireQuestion = questionnaireQuestion;
    }
}
