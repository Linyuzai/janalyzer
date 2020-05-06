package com.github.linyuzai.janalyzer.ast;

public interface TemplateAnalyzer<C extends TemplateContext, E extends TemplateElement> {

    E analyze(C context);

    int order();
}
