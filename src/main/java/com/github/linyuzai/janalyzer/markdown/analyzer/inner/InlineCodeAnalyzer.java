package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.TextAnalyzer;
import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.InlineCodeElement;

public class InlineCodeAnalyzer extends InnerPartAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<InlineCodeAnalyzer> {

        private static final InlineCodeAnalyzer.Holder instance = new InlineCodeAnalyzer.Holder();

        public static InlineCodeAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public InlineCodeAnalyzer newAnalyzer() {
            return new InlineCodeAnalyzer();
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(TextAnalyzer.Holder.getInstance());
    }

    @Override
    public InlineCodeElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String mask = "`";
        int inlineCodeIndex = source.indexOf(mask);
        if (inlineCodeIndex != -1) {
            int inlineCodeEndIndex = source.indexOf(mask, inlineCodeIndex + mask.length());
            if (inlineCodeEndIndex != -1) {
                int firstBeginIndex = getFirstBeginIndex(context.getParent(), source);
                if (firstBeginIndex != -1 && firstBeginIndex < inlineCodeIndex) {
                    return null;
                }
                int beginIndex = inlineCodeIndex;
                int endIndex = inlineCodeEndIndex + mask.length();
                String content = source.substring(beginIndex, endIndex);
                String inner = content.substring(mask.length(), content.length() - mask.length());
                InlineCodeElement inlineCodeElement = (InlineCodeElement) super.analyze(newContext(inner, loop));
                baseElement(inlineCodeElement, beginIndex, endIndex, content);
                return inlineCodeElement;
            }
        }
        return null;
    }

    @Override
    public InlineCodeElement newElement() {
        return new InlineCodeElement();
    }
}
