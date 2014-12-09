package org.jpwh.model.querying;

import java.math.BigInteger;
import java.util.Date;

public class ItemSummary {

    protected Long itemId;

    protected String name;

    protected Date auctionEnd;

    public ItemSummary() {
    }

    // TODO https://hibernate.atlassian.net/browse/HHH-8237
    public ItemSummary(BigInteger itemId, String name, Date auctionEnd) {
        this(itemId.longValue(), name, auctionEnd);
    }

    public ItemSummary(Long itemId, String name, Date auctionEnd) {
        this.itemId = itemId;
        this.name = name;
        this.auctionEnd = auctionEnd;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAuctionEnd() {
        return auctionEnd;
    }

    public void setAuctionEnd(Date auctionEnd) {
        this.auctionEnd = auctionEnd;
    }

    // ...
}