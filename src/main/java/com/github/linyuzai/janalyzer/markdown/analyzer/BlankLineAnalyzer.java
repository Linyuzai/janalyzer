package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.BlankLineElement;

public class BlankLineAnalyzer extends MarkdownAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<BlankLineAnalyzer> {

        private static final BlankLineAnalyzer.Holder instance = new BlankLineAnalyzer.Holder();

        public static BlankLineAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public BlankLineAnalyzer newAnalyzer() {
            return new BlankLineAnalyzer();
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
