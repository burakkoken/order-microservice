package org.readingisgood.ordermicroservice.model.response;

import lombok.Data;

@Data
public class StockDTO {

    private Long bookId;
    private Long quantity;

}
