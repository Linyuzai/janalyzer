package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.MarkdownElement;
import com.github.linyuzai.janalyzer.markdown.element.ReferenceElement;

import java.util.Collection;

public class ReferenceAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<ReferenceAnalyzer> {

        private static final Proxy instance = new Proxy(new ReferenceAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(ReferenceAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        super.registerSelfAnalyzers();
    }

    @Override
    public ReferenceElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String s = trimStart(source, ' ');
        int blankLength = source.length() - s.length();
        if (s.startsWith(">")) {
            String ss = trimStart(s, '>');
            int maskLength = s.length() - ss.length();
            int endIndex = s.length();
            Collection<MarkdownAnalyzer> analyzers = getChildren();
            if (analyzers != null) {
                for (MarkdownAnalyzer analyzer : analyzers) {
                    MarkdownElement me = analyzer.analyze(newContext(ss, false));
                    if (me != null) {
                        endIndex = me.getEndIndex() + maskLength;
                        break;
                    }
                }
            }
            int beginIndex = 0;
            String content = s.substring(beginIndex, endIndex);
            String inner = trimStart(content, '>');
            ReferenceElement referenceElement = (ReferenceElement) super.analyze(newContext(inner, loop));
            referenceElement.setContent(content);
            referenceElement.setBeginIndex(beginIndex);
            referenceElement.setEndIndex(endIndex + blankLength);
            referenceElement.setReferenceLevel(content.length() - inner.length());
            return referenceElement;
        }
        return null;
    }

    @Override
    public ReferenceElement newElement() {
        return new ReferenceElement();
    }
}
