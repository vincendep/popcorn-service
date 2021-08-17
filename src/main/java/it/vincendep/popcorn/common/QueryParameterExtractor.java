package it.vincendep.popcorn.common;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ServerWebExchange;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryParameterExtractor {

    private String pageParamName = "page";
    private String sizeParamName = "size";
    private String sortParamName = "sort";
    private int defaultSize = 50;
    private int defaultPage = 0;

    public String getPageParamName() {
        return pageParamName;
    }

    public void setPageParamName(String pageParamName) {
        this.pageParamName = pageParamName;
    }

    public String getSizeParamName() {
        return sizeParamName;
    }

    public void setSizeParamName(String sizeParamName) {
        this.sizeParamName = sizeParamName;
    }

    public String getSortParamName() {
        return sortParamName;
    }

    public void setSortParamName(String sortParamName) {
        this.sortParamName = sortParamName;
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(int defaultPage) {
        this.defaultPage = defaultPage;
    }

    public Pageable getPageable(ServerWebExchange exchange) {
        var params = exchange.getRequest().getQueryParams();
        var page = Optional.ofNullable(params.getFirst(pageParamName)).map(Integer::parseInt).orElse(defaultPage);
        var size = Optional.ofNullable(params.getFirst(sizeParamName)).map(Integer::parseInt).orElse(defaultSize);
        var sort = getSort(exchange);
        return PageRequest.of(page, size, sort);
    }

    public Sort getSort(ServerWebExchange exchange) {
        var orderStrings = exchange.getRequest().getQueryParams().get(sortParamName);
        Sort sort = Sort.unsorted();
        if (orderStrings != null) {
            sort = orderStrings.stream()
                    .map(this::getSortOrder)
                    .filter(Objects::nonNull)
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(),
                            orders -> !orders.isEmpty() ? Sort.by(orders) : Sort.unsorted()));
        }
        return sort;
    }

    private @Nullable Sort.Order getSortOrder(String orderString) {
        String[] orderArray = orderString.split(",");
        Sort.Order order = null;
        if (orderArray.length == 1) {
            order = Sort.Order.asc(orderArray[0]);
        } else if (orderArray.length == 2) {
            if (orderArray[1].equals("DESC")) {
                order = Sort.Order.desc(orderArray[0]);
            } else if (orderArray[1].equals("ASC")) {
                order = Sort.Order.asc(orderArray[0]);
            }
        }
        return order;
    }
}
