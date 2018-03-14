package springMvc.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by hyeondeok on 2018. 3. 13..
 */
public class Deal {
    long deal_srl;
    String keyword;
    String keyword_org;
    LocalDateTime regdate;

    public long getDeal_srl() {
        return deal_srl;
    }

    public void setDeal_srl(long deal_srl) {
        this.deal_srl = deal_srl;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword_org() {
        return keyword_org;
    }

    public void setKeyword_org(String keyword_org) {
        this.keyword_org = keyword_org;
    }

    public String getRegdate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return regdate.format(formatter);
    }

    public void setRegdate(LocalDateTime regdate) {
        this.regdate = regdate;
    }

    @Override public String toString() {
        return Long.toString(deal_srl) + ", " + getKeyword() + ", " + getKeyword_org() + ", " + getRegdate();
    }
}
