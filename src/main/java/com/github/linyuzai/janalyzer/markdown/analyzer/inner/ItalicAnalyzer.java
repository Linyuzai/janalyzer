package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.CombinationAnalyzer;
import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.ItalicElement;

public class ItalicAnalyzer extends InnerPartAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<ItalicAnalyzer> {

        private static final ItalicAnalyzer.Holder instance = new ItalicAnalyzer.Holder();

        public static ItalicAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public ItalicAnalyzer newAnalyzer() {
            return new ItalicAnalyzer();
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Holder.getInstance());
    }

    @Override
    public ItalicElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String mask = "*";
        int italicIndex = source.indexOf(mask);
        if (italicIndex != -1) {
            int italicEndIndex = source.indexOf(mask, italicIndex + mask.length());
            if (italicEndIndex != -1) {
                int firstBeginIndex = getFirstBeginIndex(context.getParent(), source);
                if (firstBeginIndex != -1 && firstBeginIndex < italicIndex) {
                    return null;
                }
                int beginIndex = italicIndex;
                int endIndex = italicEndIndex + mask.length();
                String content = source.substring(beginIndex, endIndex);
                String inner = content.substring(mask.length(), content.length() - mask.length());
                ItalicElement italicElement = (ItalicElement) super.analyze(newContext(inner, loop));
                baseElement(italicElement, beginIndex, endIndex, content);
                return italicElement;
            }
        }
        return null;
    }

    @Override
    public ItalicElement newElement() {
        return new ItalicElement();
    }
}
