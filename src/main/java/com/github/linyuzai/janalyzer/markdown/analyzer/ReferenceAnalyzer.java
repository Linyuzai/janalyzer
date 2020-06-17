package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.MarkdownElement;
import com.github.linyuzai.janalyzer.markdown.element.ReferenceElement;

import java.util.Collection;

public class ReferenceAnalyzer extends MarkdownAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<ReferenceAnalyzer> {

        private static final Holder instance = new Holder(new ReferenceAnalyzer());

        public static Holder getInstance() {
            return instance;
        }

        public Holder(ReferenceAnalyzer analyzer) {
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
            baseElement(referenceElement, beginIndex, endIndex + blankLength, content);
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
