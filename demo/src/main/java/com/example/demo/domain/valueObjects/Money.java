package com.example.demo.domain.valueObjects;

import com.example.demo.domain.enums.Currency;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;

@Embeddable
@Builder
@Getter
public class Money {

    private final BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private final Currency currency;
    public Money(BigDecimal amount, Currency currency){
        this.amount=amount;
        this.currency=currency;
    }
    protected Money(){
        amount=new BigDecimal(0);
        currency=Currency.NONE;
    }

    public Money Zero(){
        return new Money(new BigDecimal(0),Currency.NONE);
    }

}
