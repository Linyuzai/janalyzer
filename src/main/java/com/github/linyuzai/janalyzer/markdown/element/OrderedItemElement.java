package com.github.linyuzai.janalyzer.markdown.element;

public class OrderedItemElement extends MarkdownElement {

    private int listLevel;

    private String serialNumber;

    public int getListLevel() {
        return listLevel;
    }

    public void setListLevel(int listLevel) {
        this.listLevel = listLevel;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
