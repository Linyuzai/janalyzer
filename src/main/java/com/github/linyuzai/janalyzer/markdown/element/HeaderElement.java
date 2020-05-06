package com.github.linyuzai.janalyzer.markdown.element;

public class HeaderElement extends MarkdownElement {

    private int headerLevel;

    public int getHeaderLevel() {
        return headerLevel;
    }

    public void setHeaderLevel(int headerLevel) {
        this.headerLevel = headerLevel;
    }
}
