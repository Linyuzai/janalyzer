package com.github.linyuzai.janalyzer.ast;

public class StringTemplateContext implements TemplateContext {

    private String source;

    private boolean loop;

    public StringTemplateContext(String source, boolean loop) {
        this.source = source;
        this.loop = loop;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isLoop() {
        return loop;
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}
