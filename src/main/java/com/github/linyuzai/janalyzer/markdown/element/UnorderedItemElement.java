package com.github.linyuzai.janalyzer.markdown.element;

public class UnorderedItemElement extends MarkdownElement {

    private int listLevel;

    public int getListLevel() {
        return listLevel;
    }

    public void setListLevel(int listLevel) {
        this.listLevel = listLevel;
    }
}
