package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.ast.StringTemplateAnalyzer;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.MarkdownElement;

import java.util.Collection;

public class MarkdownAnalyzer extends StringTemplateAnalyzer<MarkdownContext, MarkdownAnalyzer, MarkdownElement> {

    private static final MarkdownAnalyzer instance = new MarkdownAnalyzer();

    private boolean analyzersRegister;

    public static MarkdownAnalyzer getInstance() {
        return instance;
    }

    public void registerAnalyzers() {
        if (analyzersRegister) {
            return;
        }
        analyzersRegister = true;
        registerSelfAnalyzers();
        registerChildrenAnalyzers();
    }

    public void registerSelfAnalyzers() {
        registerAnalyzer(BlankLineAnalyzer.getInstance());
        registerAnalyzer(HeaderAnalyzer.getInstance());
        registerAnalyzer(ReferenceAnalyzer.getInstance());
        registerAnalyzer(UnorderedListAnalyzer.getInstance());
        registerAnalyzer(OrderedListAnalyzer.getInstance());
        registerAnalyzer(BlockCodeAnalyzer.getInstance());
        registerAnalyzer(DividerAnalyzer.getInstance());
        registerAnalyzer(TableAnalyzer.getInstance());
        registerAnalyzer(CombinationAnalyzer.getInstance());
    }

    public void registerChildrenAnalyzers() {
        Collection<MarkdownAnalyzer> children = getChildren();
        if (children == null) {
            return;
        }
        children.forEach(MarkdownAnalyzer::registerAnalyzers);
    }

    @Override
    public MarkdownElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        return super.analyze(newContext(source, loop));
    }

    @SuppressWarnings("unchecked")
    @Override
    public MarkdownElement newElement() {
        return new MarkdownElement();
    }

    @Override
    public MarkdownContext newContext(String source, boolean loop) {
        return new MarkdownContext(source, loop, this);
    }
}
