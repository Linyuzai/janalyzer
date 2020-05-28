package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.inner.*;
import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.CombinationElement;

public class CombinationAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<CombinationAnalyzer> {

        private static final Proxy instance = new Proxy(new CombinationAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(CombinationAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(InlineCodeAnalyzer.Proxy.getInstance());
        registerAnalyzer(DeleteLineAnalyzer.Proxy.getInstance());
        registerAnalyzer(ItalicBoldAnalyzer.Proxy.getInstance());
        registerAnalyzer(BoldAnalyzer.Proxy.getInstance());
        registerAnalyzer(ItalicAnalyzer.Proxy.getInstance());
        registerAnalyzer(PicturePartAnalyzer.Proxy.getInstance());
        registerAnalyzer(HyperlinkAnalyzer.Proxy.getInstance());
        registerAnalyzer(TextAnalyzer.Proxy.getInstance());
    }

    @Override
    public CombinationElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        int beginIndex = 0;
        int endIndex = getEndIndex(source, "\n");
        String content = source.substring(beginIndex, endIndex);
        CombinationElement combinationElement = (CombinationElement) super.analyze(newContext(content, loop));
        baseElement(combinationElement, beginIndex, endIndex, content);
        return combinationElement;
    }

    @Override
    public CombinationElement newElement() {
        return new CombinationElement();
    }
}
