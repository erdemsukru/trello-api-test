package com.testinium.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {
    private String id;
    private String name;
    private String desc;
    private String idBoard;
    private String idList;
    private String url;

    public Card() {}

    public Card(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    public String getIdBoard() { return idBoard; }
    public void setIdBoard(String idBoard) { this.idBoard = idBoard; }

    public String getIdList() { return idList; }
    public void setIdList(String idList) { this.idList = idList; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    @Override
    public String toString() {
        return "Card{id='" + id + "', name='" + name + "'}";
    }
}