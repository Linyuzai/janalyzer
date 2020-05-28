package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.BlankLineElement;

public class BlankLineAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<BlankLineAnalyzer> {

        private static final Proxy instance = new Proxy(new BlankLineAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(BlankLineAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {

    }

    @Override
    public BlankLineElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        int beginIndex = 0;
        int endIndex = getEndIndex(source, "\n");
        String content = source.substring(beginIndex, endIndex);
        String s = content.trim();
        if (s.isEmpty()) {
            BlankLineElement blankLineElement = (BlankLineElement) super.analyze(newContext(content, loop));
            baseElement(blankLineElement, beginIndex, endIndex, content);
            return blankLineElement;
        }
        return null;
    }

    @Override
    public BlankLineElement newElement() {
        return new BlankLineElement();
    }

}
