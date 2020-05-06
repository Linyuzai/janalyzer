package com.github.linyuzai.janalyzer.markdown.context;

import com.github.linyuzai.janalyzer.ast.StringTemplateContext;
import com.github.linyuzai.janalyzer.markdown.analyzer.MarkdownAnalyzer;

public class MarkdownContext extends StringTemplateContext {

    private MarkdownAnalyzer parent;

    public MarkdownContext(String source, boolean loop, MarkdownAnalyzer analyzer) {
        super(source, loop);
        parent = analyzer;
    }

    public MarkdownAnalyzer getParent() {
        return parent;
    }

    public void setParent(MarkdownAnalyzer parent) {
        this.parent = parent;
    }
}
