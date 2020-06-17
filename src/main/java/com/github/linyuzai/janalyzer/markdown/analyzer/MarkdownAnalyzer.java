package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.ast.StringTemplateAnalyzer;
import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.MarkdownElement;

import java.util.Collection;

public class MarkdownAnalyzer extends StringTemplateAnalyzer<MarkdownContext, MarkdownAnalyzer, MarkdownElement> {

    public static class Holder extends AbstractAnalyzerHolder<MarkdownAnalyzer> {

        private static final Holder instance = new Holder(new MarkdownAnalyzer());

        public static Holder getInstance() {
            return instance;
        }

        public Holder(MarkdownAnalyzer analyzer) {
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
        registerAnalyzer(BlankLineAnalyzer.Holder.getInstance());
        registerAnalyzer(HeaderAnalyzer.Holder.getInstance());
        registerAnalyzer(ReferenceAnalyzer.Holder.getInstance());
        registerAnalyzer(UnorderedListAnalyzer.Holder.getInstance());
        registerAnalyzer(OrderedListAnalyzer.Holder.getInstance());
        registerAnalyzer(BlockCodeAnalyzer.Holder.getInstance());
        registerAnalyzer(DividerAnalyzer.Holder.getInstance());
        registerAnalyzer(TableAnalyzer.Holder.getInstance());
        registerAnalyzer(CombinationAnalyzer.Holder.getInstance());
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

    public void baseElement(MarkdownElement element, int beginIndex, int endIndex, String content) {
        element.setBeginIndex(beginIndex);
        element.setEndIndex(endIndex);
        element.setContent(content);
    }
}
