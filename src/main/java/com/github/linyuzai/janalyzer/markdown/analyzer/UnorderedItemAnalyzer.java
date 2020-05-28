package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.UnorderedItemElement;

public class UnorderedItemAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<UnorderedItemAnalyzer> {

        private static final Proxy instance = new Proxy(new UnorderedItemAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(UnorderedItemAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Proxy.getInstance());
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
