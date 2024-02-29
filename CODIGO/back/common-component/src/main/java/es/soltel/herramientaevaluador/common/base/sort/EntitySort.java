package es.soltel.herramientaevaluador.common.base.sort;

import org.springframework.data.domain.Sort;

import es.soltel.herramientaevaluador.common.base.vo.OrderVO;
import es.soltel.herramientaevaluador.common.base.vo.RequestVO;

public class EntitySort {

    private final static String DESC = "desc";

    public static Sort getSort(RequestVO request) {

        if (request != null && request.getOrder() != null) {
            OrderVO order = request.getOrder();

            if (order.getField() != null && !"".equals(order.getField()) && !"string".equals(order.getField())) {
                if (order.getField().indexOf(",") > 0) {
                    String[] orders = order.getField().trim().split("\\s*,\\s*");

                    if (DESC.equals(order.getSort().toLowerCase())) {
                        return Sort.by(orders).descending();
                    }
                    else {
                        return Sort.by(orders).ascending();
                    }
                }
                else {
                    if (DESC.equals(order.getSort().toLowerCase())) {
                        return Sort.by(order.getField()).descending();
                    }
                    else {
                        return Sort.by(order.getField()).ascending();
                    }
                }
            }

        }

        return Sort.unsorted();
    }

}
