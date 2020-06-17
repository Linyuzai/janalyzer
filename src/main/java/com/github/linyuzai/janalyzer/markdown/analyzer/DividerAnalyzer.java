package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.DividerElement;

public class DividerAnalyzer extends MarkdownAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<DividerAnalyzer> {

        private static final DividerAnalyzer.Holder instance = new DividerAnalyzer.Holder();

        public static DividerAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public DividerAnalyzer newAnalyzer() {
            return new DividerAnalyzer();
        }
    }

    @Override
    public void registerSelfAnalyzers() {
    }

    @Override
    public DividerElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        if (s.startsWith("-") || s.startsWith("*")) {
            int beginIndex = 0;
            int endIndex = getEndIndex(s, "\n");
            String content = s.substring(beginIndex, endIndex);
            if (content.length() >= 3) {
                String inner = trimStart(content, '-', '*', '\n');
                if (inner.isEmpty()) {
                    DividerElement dividerElement = (DividerElement) super.analyze(newContext(content, loop));
                    baseElement(dividerElement, beginIndex, endIndex + blankLength, content);
                    return dividerElement;
                }
            }
        }
        return null;
    }

    @Override
    public DividerElement newElement() {
        return new DividerElement();
    }
}
