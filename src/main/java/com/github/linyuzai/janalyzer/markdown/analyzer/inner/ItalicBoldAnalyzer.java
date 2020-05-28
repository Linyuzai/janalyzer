package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.CombinationAnalyzer;
import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.ItalicBoldElement;

public class ItalicBoldAnalyzer extends InnerPartAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<ItalicBoldAnalyzer> {

        private static final Proxy instance = new Proxy(new ItalicBoldAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(ItalicBoldAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Proxy.getInstance());
    }

    @Override
    public ItalicBoldElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String mask = "***";
        int italicBoldIndex = source.indexOf(mask);
        if (italicBoldIndex != -1) {
            int italicBoldEndIndex = source.indexOf(mask, italicBoldIndex + mask.length());
            if (italicBoldEndIndex != -1) {
                int firstBeginIndex = getFirstBeginIndex(context.getParent(), source);
                if (firstBeginIndex != -1 && firstBeginIndex < italicBoldIndex) {
                    return null;
                }
                int beginIndex = italicBoldIndex;
                int endIndex = italicBoldEndIndex + mask.length();
                String content = source.substring(beginIndex, endIndex);
                String inner = content.substring(mask.length(), content.length() - mask.length());
                ItalicBoldElement italicBoldElement = (ItalicBoldElement) super.analyze(newContext(inner, loop));
                italicBoldElement.setContent(content);
                italicBoldElement.setBeginIndex(beginIndex);
                italicBoldElement.setEndIndex(endIndex);
                return italicBoldElement;
            }
        }
        return null;
    }

    @Override
    public ItalicBoldElement newElement() {
        return new ItalicBoldElement();
    }
}
