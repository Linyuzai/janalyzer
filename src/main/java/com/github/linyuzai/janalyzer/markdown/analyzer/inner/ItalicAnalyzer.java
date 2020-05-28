package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.CombinationAnalyzer;
import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.ItalicElement;

public class ItalicAnalyzer extends InnerPartAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<ItalicAnalyzer> {

        private static final Proxy instance = new Proxy(new ItalicAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(ItalicAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Proxy.getInstance());
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
                italicElement.setContent(content);
                italicElement.setBeginIndex(beginIndex);
                italicElement.setEndIndex(endIndex);
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
