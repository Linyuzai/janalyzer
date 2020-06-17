package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.UnorderedItemElement;

public class UnorderedItemAnalyzer extends MarkdownAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<UnorderedItemAnalyzer> {

        private static final UnorderedItemAnalyzer.Holder instance = new UnorderedItemAnalyzer.Holder();

        public static UnorderedItemAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public UnorderedItemAnalyzer newAnalyzer() {
            return new UnorderedItemAnalyzer();
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Holder.getInstance());
    }

    @Override
    public UnorderedItemElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        if (s.startsWith("- ") || s.startsWith("+ ") || s.startsWith("* ")) {
            int beginIndex = 0;
            int endIndex = getEndIndex(s, "\n");
            String content = s.substring(beginIndex, endIndex);
            String inner = content.substring(2);
            UnorderedItemElement unorderedItemElement = (UnorderedItemElement) super.analyze(newContext(inner, loop));
            baseElement(unorderedItemElement, beginIndex, endIndex + blankLength, content);
            unorderedItemElement.setListLevel(blankLength);
            return unorderedItemElement;
        }
        return null;
    }

    @Override
    public UnorderedItemElement newElement() {
        return new UnorderedItemElement();
    }
}
