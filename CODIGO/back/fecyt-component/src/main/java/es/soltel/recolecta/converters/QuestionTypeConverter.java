package es.soltel.recolecta.converters;

import org.springframework.stereotype.Component;

import es.soltel.recolecta.entity.QuestionEntity.QuestionType;

@Component
public class QuestionTypeConverter {

    public static QuestionType fromString(String text) {
        if (text != null) {
            for (QuestionType type : QuestionType.values()) {
                if (text.equalsIgnoreCase(type.toString())) {
                    return type;
                }
            }
        }
        throw new IllegalArgumentException("No se pudo encontrar el QuestionType correspondiente para: " + text);
    }
    
}
