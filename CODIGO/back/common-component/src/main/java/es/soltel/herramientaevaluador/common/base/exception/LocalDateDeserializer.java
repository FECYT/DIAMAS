package es.soltel.herramientaevaluador.common.base.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateDeserializer extends StdDeserializer<LocalDateTime> {

	private static final long serialVersionUID = -18415750863112991L;

	protected LocalDateDeserializer() {
		super(LocalDateTime.class);
	}

	@Override
	public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		return LocalDateTime.parse(parser.readValueAs(String.class));
	}
}