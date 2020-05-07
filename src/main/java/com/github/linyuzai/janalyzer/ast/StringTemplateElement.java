package com.github.linyuzai.janalyzer.ast;

import java.util.ArrayList;
import java.util.Collection;

public class StringTemplateElement<E extends StringTemplateElement> implements TemplateElement {

    private int beginIndex;
    private int endIndex;
    private String content;
    private Collection<E> children;
    private Object extra;

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Collection<E> getChildren() {
        return children;
    }

    public void setChildren(Collection<E> children) {
        this.children = children;
    }

    public void addChildren(E child) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    public Object getExtra() {
        return extra;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }
}
