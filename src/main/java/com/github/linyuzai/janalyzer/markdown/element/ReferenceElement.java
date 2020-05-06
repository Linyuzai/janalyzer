package com.github.linyuzai.janalyzer.markdown.element;

public class ReferenceElement extends MarkdownElement {

    private int referenceLevel;

    public int getReferenceLevel() {
        return referenceLevel;
    }

    public void setReferenceLevel(int referenceLevel) {
        this.referenceLevel = referenceLevel;
    }
}
