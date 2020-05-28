package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.HeaderElement;

public class HeaderAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<DividerAnalyzer> {

        private static final Proxy instance = new Proxy(new DividerAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(DividerAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Proxy.getInstance());
    }

    @Override
    public HeaderElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        int headerLevel = 0;
        if (s.startsWith("######")) {
            headerLevel = 6;
        } else if (s.startsWith("#####")) {
            headerLevel = 5;
        } else if (s.startsWith("####")) {
            headerLevel = 4;
        } else if (s.startsWith("###")) {
            headerLevel = 3;
        } else if (s.startsWith("##")) {
            headerLevel = 2;
        } else if (s.startsWith("#")) {
            headerLevel = 1;
        }
        if (headerLevel > 0) {
            int beginIndex = 0;
            int endIndex = getEndIndex(s, "\n");
            String content = s.substring(0, endIndex);
            String inner = content.substring(headerLevel);
            HeaderElement headerElement = (HeaderElement) super.analyze(newContext(inner, loop));
            headerElement.setContent(content);
            headerElement.setBeginIndex(beginIndex);
            headerElement.setEndIndex(endIndex + blankLength);
            headerElement.setHeaderLevel(headerLevel);
            return headerElement;
        } else {
            return null;
        }
    }

    @Override
    public HeaderElement newElement() {
        return new HeaderElement();
    }
}
