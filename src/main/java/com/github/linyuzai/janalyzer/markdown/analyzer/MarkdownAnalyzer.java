package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.ast.StringTemplateAnalyzer;
import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.MarkdownElement;

import java.util.Collection;

public class MarkdownAnalyzer extends StringTemplateAnalyzer<MarkdownContext, MarkdownAnalyzer, MarkdownElement> {

    public static class Proxy extends AbstractAnalyzerProxy<MarkdownAnalyzer> {

        private static final Proxy instance = new Proxy(new MarkdownAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(MarkdownAnalyzer analyzer) {
            super(analyzer);
        }
    }

    private boolean analyzersRegister;

    public void registerAnalyzers() {
        if (analyzersRegister) {
            return;
        }
        analyzersRegister = true;
        registerSelfAnalyzers();
        registerChildrenAnalyzers();
    }

    public void registerSelfAnalyzers() {
        registerAnalyzer(BlankLineAnalyzer.Proxy.getInstance());
        registerAnalyzer(HeaderAnalyzer.Proxy.getInstance());
        registerAnalyzer(ReferenceAnalyzer.Proxy.getInstance());
        registerAnalyzer(UnorderedListAnalyzer.Proxy.getInstance());
        registerAnalyzer(OrderedListAnalyzer.Proxy.getInstance());
        registerAnalyzer(BlockCodeAnalyzer.Proxy.getInstance());
        registerAnalyzer(DividerAnalyzer.Proxy.getInstance());
        registerAnalyzer(TableAnalyzer.Proxy.getInstance());
        registerAnalyzer(CombinationAnalyzer.Proxy.getInstance());
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
