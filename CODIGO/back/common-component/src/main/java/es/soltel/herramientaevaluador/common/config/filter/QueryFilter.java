package es.soltel.herramientaevaluador.common.config.filter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.validator.GenericValidator;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import es.soltel.herramientaevaluador.common.base.exception.RestBadRequestException;
import es.soltel.herramientaevaluador.common.base.util.Utils;
import es.soltel.herramientaevaluador.common.base.vo.FilterVO;
import es.soltel.herramientaevaluador.common.base.vo.RequestVO;

@Component
public class QueryFilter<T> {

    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String NOT = "NOT ";
    private static final String LITERAL = "\"";


    public Specification<T> filter(RequestVO requestVO) {
        return (obj, cq, cb) -> {
            if (requestVO != null && requestVO.getFilters() != null && !requestVO.getFilters().isEmpty()) {
                //Si sólo hay un filtro, se devuelve el resultado de su procesado
                if (requestVO.getFilters().size() == 1) {
                    return processsFilter(requestVO.getFilters().get(0), obj, cb);
                    //Si hay varios se hace el logic (and / or ) de todos
                }
                else {
                    List<Predicate> predicates = new ArrayList<>();

                    for (FilterVO f : requestVO.getFilters()) {
                        if (f != null)
                            predicates.add(processsFilter(f, obj, cb));

                    }

                    return processsLogic(requestVO.getLogic(), predicates, cb);
                }
            }
            return null;
        };
    }


    public Predicate processsFilter(FilterVO filter, Root<T> obj, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();
        FilterVO transformedFilter = null;
        if (filter != null) {
            //Se procesa el campo
            if (filter != null && filter.getField() != null && filter.getValue() != null) {
                //Si hay operadores lógicos se procesan
                if (existLogicOperators(filter.getValue())) {
                    transformedFilter = transformFilter(filter);
                    predicates.add(processsFilter(transformedFilter, obj, cb));
                }
                else {
                    //Si no, se procesa el filtro directamente
                    predicates.add(processsField(obj, filter.getField(), filter.getOperator(), filter.getValue(), cb));
                }
            }

            //A continuación se procesan los subfiltros   
            if (transformedFilter != null && transformedFilter.getSubfilters() != null
                    && !transformedFilter.getSubfilters().isEmpty()) {
                for (FilterVO sf : transformedFilter.getSubfilters()) {
                    if (sf != null)
                        predicates.add(processsFilter(transformFilter(sf), obj, cb));

                }
            }
            else if (filter.getSubfilters() != null && !filter.getSubfilters().isEmpty()) {
                for (FilterVO sf : filter.getSubfilters()) {
                    if (sf != null)
                        predicates.add(processsFilter(transformFilter(sf), obj, cb));

                }
            }
            return processsLogic(filter.getLogic(), predicates, cb);

        }

        return null;
    }

    public Predicate processsLogic(String logic, List<Predicate> predicates, CriteriaBuilder cb) {
        if (logic != null) {
            switch (logic) {
                case "AND":
                    return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                case "OR":
                    return cb.or(predicates.toArray(new Predicate[predicates.size()]));
                default:
                    //throw UndefinedLogicException?
                    throw new RestBadRequestException("El logic introducido no es valido");
            }
        }
        else {
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }
    }

    public Predicate processsField(Root<T> obj, String field, String comparator, Object value, CriteriaBuilder cb) {
        if (field != null) {
            Expression expresion;
            if (field.contains(".")) {
                expresion = cb.treat(obj.get(field.substring(0, field.indexOf("."))), obj.getJavaType())
                        .get(field.substring((field.indexOf(".")) + 1));
            }
            else {
                expresion = obj.get(field);
            }
            if (comparator == null) {
                comparator = "=";
            }
            if (value != null) {
                //Boolean Verdadero
                if (value.toString().contains("true")) {
                    return cb.isTrue(expresion);
                    //Boolean Falso
                }
                else if (value.toString().contains("false")) {
                    return cb.isFalse(expresion);
                    //Null
                }
                else if (value.toString().contains("null")) {
                    if (comparator.equals("!=")) {
                        return cb.isNotNull(expresion);
                    }
                    else {
                        return cb.isNull(expresion);
                    }
                    //Fechas
                }
                else if (GenericValidator.isDate(value.toString(), "yyyy-MM-dd", true)) {
                    LocalDate parsedValue = LocalDate.parse(value.toString());
                    switch (comparator) {
                        case "=":
                            return cb.equal(expresion, parsedValue);
                        case "!=":
                            return cb.notEqual(expresion, parsedValue);
                        case ">":
                            return cb.greaterThan(expresion, parsedValue);
                        case ">=":
                            return cb.greaterThanOrEqualTo(expresion, parsedValue);
                        case "<":
                            return cb.lessThan(expresion, parsedValue);
                        case "<=":
                            return cb.lessThanOrEqualTo(expresion, parsedValue);
                        default:
                            throw new RestBadRequestException("El comparator introducido no es valido para una fecha");
                    }
                }
                //Fechas con horas
                else if (GenericValidator.isDate(value.toString(), "yyyy-MM-dd HH:mm:ss", true)) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime parsedValue = LocalDateTime.parse(value.toString(), formatter);
                    switch (comparator) {
                        case "=":
                            return cb.equal(expresion, parsedValue);
                        case "!=":
                            return cb.notEqual(expresion, parsedValue);
                        case ">":
                            return cb.greaterThan(expresion, parsedValue);
                        case ">=":
                            return cb.greaterThanOrEqualTo(expresion, parsedValue);
                        case "<":
                            return cb.lessThan(expresion, parsedValue);
                        case "<=":
                            return cb.lessThanOrEqualTo(expresion, parsedValue);
                        default:
                            throw new RestBadRequestException(
                                    "El comparator introducido no es valido para una fecha y hora");
                    }
                }
                //Números
                else if (GenericValidator.isLong(value.toString())) {
                    Long parsedValue = Long.parseLong(value.toString());
                    switch (comparator) {
                        case "=":
                            return cb.equal(expresion, parsedValue);
                        case "!=":
                            return cb.notEqual(expresion, parsedValue);
                        case "in":
                            return cb.and(expresion.in(Arrays.asList(value.toString().split("\\s*,\\s*"))));
                        case "notIn":
                            return cb.and(expresion.in(Arrays.asList(value.toString().split("\\s*,\\s*"))).not());
                        case ">":
                            return cb.greaterThan(expresion, parsedValue);
                        case ">=":
                            return cb.greaterThanOrEqualTo(expresion, parsedValue);
                        case "<":
                            return cb.lessThan(expresion, parsedValue);
                        case "<=":
                            return cb.lessThanOrEqualTo(expresion, parsedValue);
                        case "like":
                            return cb.like(expresion, "%" + value.toString() + "%");
                        case "notLike":
                            return cb.notLike(expresion, "%" + value.toString() + "%");
                        case "likeNonSensitive":
                            //Se eliminan accents y se hace no case sensitive
                            return cb.like(
                                    cb.function("translate", String.class, cb.upper(expresion), cb.literal("ÁÉÍÓÚ"),
                                            cb.literal("AEIOU")),
                                    "%" + Utils.stripAccents(value.toString()).toUpperCase() + "%");
                        case "notLikeNonSensitive":
                            //Se eliminan accents y se hace not like no case sensitive
                            return cb.notLike(
                                    cb.function("translate", String.class, cb.upper(expresion), cb.literal("ÁÉÍÓÚ"),
                                            cb.literal("AEIOU")),
                                    "%" + Utils.stripAccents(value.toString()).toUpperCase() + "%");
                        default:
                            throw new RestBadRequestException("El comparator introducido no es valido para un número");
                    }
                }
                //Cadenas
                else {
                    if (value.toString().trim().startsWith("'") && value.toString().trim().endsWith("'")) {
                        value = value.toString().trim().substring(1, value.toString().trim().length() - 1);
                    }
                    switch (comparator) {
                        case "=":
                            return cb.equal(expresion, value.toString());
                        case "!=":
                            return cb.notEqual(expresion, value.toString());
                        case "in":
                            return cb.and(expresion.in(Arrays.asList(value.toString().split("\\s*,\\s*"))));
                        case "notIn":
                            return cb.and(expresion.in(Arrays.asList(value.toString().split("\\s*,\\s*"))).not());
                        case "like":
                            return cb.like(expresion, "%" + value.toString() + "%");
                        case "notLike":
                            return cb.notLike(expresion, "%" + value.toString() + "%");
                        case "contains":
                            return cb.greaterThan(
                                    cb.function("CONTAINS", Integer.class, expresion, cb.literal(value.toString())), 0);
                        case "likeNonSensitive":
                            //Se eliminan accents y se hace no case sensitive
                            return cb.like(
                                    cb.function("translate", String.class, cb.upper(expresion), cb.literal("ÁÉÍÓÚ"),
                                            cb.literal("AEIOU")),
                                    "%" + Utils.stripAccents(value.toString()).toUpperCase() + "%");
                        case "notLikeNonSensitive":
                            //Se eliminan accents y se hace not like no case sensitive
                            return cb.notLike(
                                    cb.function("translate", String.class, cb.upper(expresion), cb.literal("ÁÉÍÓÚ"),
                                            cb.literal("AEIOU")),
                                    "%" + Utils.stripAccents(value.toString()).toUpperCase() + "%");
                        default:
                            throw new RestBadRequestException("El comparator introducido no es valido para una cadena");
                    }
                }

            }
            else {
                return cb.isNull(obj.get(field));
            }
        }
        else {
            throw new RestBadRequestException("El field introducido no es valido");
        }
    }

    public boolean existLogicOperators(String valor) {

        String[] valores = valor.split(AND + "|" + OR + "|" + NOT + "|" + LITERAL);

        return (valores.length > 1 ? true : false);
    }

    //Función recursiva que va dividiendo la cadena por la que hay que filtrar localizando operadores logicos
    //e incluyendo cada uno de los posibles valores de esos operadores lógicos en distintos filtros
    public FilterVO transformFilter(FilterVO filter) {
        FilterVO transformedFilter = new FilterVO();
        if (filter.getValue() != null) {
            if (filter.getValue().contains(AND)) {

                String[] valores = filter.getValue().split(AND);
                FilterVO filtroNuevo = null;
                for (String valor : valores) {
                    if (!valor.trim().equals("")) {
                        if (filtroNuevo == null) {
                            filtroNuevo = new FilterVO();
                            filtroNuevo.setLogic("AND");


                        }
                        FilterVO subFiltroNuevo = new FilterVO();
                        subFiltroNuevo.setField(filter.getField());
                        subFiltroNuevo.setOperator(filter.getOperator());
                        subFiltroNuevo.setValue(valor.trim());
                        subFiltroNuevo.setLogic("AND");

                        if (filtroNuevo.getSubfilters() == null) {
                            filtroNuevo.setSubfilters(new ArrayList<FilterVO>());
                        }
                        filtroNuevo.getSubfilters().add(subFiltroNuevo);


                    }
                }
                transformedFilter.setSubfilters(filter.getSubfilters());
                if (transformedFilter.getSubfilters() == null) {
                    transformedFilter.setSubfilters(new ArrayList<FilterVO>());
                }
                transformedFilter.getSubfilters().add(filtroNuevo);

                return transformFilter(transformedFilter);
            }
            else if (filter.getValue().contains(OR)) {


                String[] valores = filter.getValue().split(OR);
                FilterVO filtroNuevo = null;
                for (String valor : valores) {
                    if (!valor.trim().equals("")) {
                        if (filtroNuevo == null) {
                            filtroNuevo = new FilterVO();
                            filtroNuevo.setLogic("OR");


                        }
                        FilterVO subFiltroNuevo = new FilterVO();
                        subFiltroNuevo.setField(filter.getField());
                        subFiltroNuevo.setOperator(filter.getOperator());
                        subFiltroNuevo.setValue(valor.trim());
                        subFiltroNuevo.setLogic("OR");

                        if (filtroNuevo.getSubfilters() == null) {
                            filtroNuevo.setSubfilters(new ArrayList<FilterVO>());
                        }
                        filtroNuevo.getSubfilters().add(subFiltroNuevo);


                    }
                }
                transformedFilter.setSubfilters(filter.getSubfilters());
                if (transformedFilter.getSubfilters() == null) {
                    transformedFilter.setSubfilters(new ArrayList<FilterVO>());
                }
                transformedFilter.getSubfilters().add(filtroNuevo);

                return transformFilter(transformedFilter);
            }
            else if (filter.getValue().contains(NOT)) {

                transformedFilter.setField(filter.getField());
                transformedFilter.setLogic(filter.getLogic());
                if (filter.getOperator().equals("likeNonSensitive")) {
                    transformedFilter.setOperator("notLikeNonSensitive");
                }
                else if (filter.getOperator().equals("like")) {
                    transformedFilter.setOperator("notLike");
                }
                else if (filter.getOperator().equals("=")) {
                    transformedFilter.setOperator("!=");
                }
                else {
                    transformedFilter.setOperator(filter.getOperator());
                }

                transformedFilter.setValue(filter.getValue().replaceFirst(NOT, "").trim());
                transformedFilter.setSubfilters(filter.getSubfilters());

                return transformFilter(transformedFilter);

            }
            else if (filter.getValue().contains(LITERAL)) {

                transformedFilter.setField(filter.getField());
                transformedFilter.setLogic(filter.getLogic());
                if (filter.getOperator().equals("likeNonSensitive")) {
                    transformedFilter.setOperator("like");
                }
                else if (filter.getOperator().equals("notLikeNonSensitive")) {
                    transformedFilter.setOperator("notLike");
                }
                else {
                    transformedFilter.setOperator(filter.getOperator());
                }
                transformedFilter.setValue(filter.getValue().replace(LITERAL, ""));
                transformedFilter.setSubfilters(filter.getSubfilters());
            }
            else {
                transformedFilter = filter;
            }
        }
        else {
            transformedFilter = filter;
        }

        return transformedFilter;
    }
}
