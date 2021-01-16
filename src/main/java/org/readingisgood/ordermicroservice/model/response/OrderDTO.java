package org.readingisgood.ordermicroservice.model.response;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDTO {

    private String transactionId;
    private Long bookId;
    private Long quantity;
    private Date date;

}
