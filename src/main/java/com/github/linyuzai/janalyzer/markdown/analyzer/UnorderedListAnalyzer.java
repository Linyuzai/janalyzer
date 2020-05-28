package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.UnorderedListElement;

public class UnorderedListAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<UnorderedListAnalyzer> {

        private static final Proxy instance = new Proxy(new UnorderedListAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(UnorderedListAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(UnorderedItemAnalyzer.Proxy.getInstance());
    }

    @Override
    public UnorderedListElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = source;
        int nextEndIndex = getNextUnorderedItemEndIndex(s);
        int endIndex = nextEndIndex;
        while (nextEndIndex != -1) {
            s = s.substring(nextEndIndex);
            nextEndIndex = getNextUnorderedItemEndIndex(s);
            if (nextEndIndex != -1) {
                endIndex += nextEndIndex;
            }
        }
        if (endIndex == -1) {
            return null;
        }
        int beginIndex = 0;
        String content = source.substring(beginIndex, endIndex);
        UnorderedListElement unorderedListElement = (UnorderedListElement) super.analyze(newContext(content, loop));
        baseElement(unorderedListElement, beginIndex, endIndex, content);
        return unorderedListElement;
    }

    private int getNextUnorderedItemEndIndex(String source) {
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        if (s.startsWith("- ") || s.startsWith("+ ") || s.startsWith("* ")) {
            int endIndex = getEndIndex(s, "\n");
            return endIndex + blankLength;
        }
        return -1;
    }

    @Override
    public UnorderedListElement newElement() {
        return new UnorderedListElement();
    }
}
