package com.myco.stock.trader.domain;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "stocktradedata")
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)

public class StocTradeData {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(columnDefinition = "jsonb") 
//    //@Type(type = "jsonb", value = null)
//    private String data;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "data")
    private Object data;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "globalQuote_id", referencedColumnName = "id")
    @JsonProperty(value = "Global Quote")
    private GlobalQuote globalQuote;
    

}
