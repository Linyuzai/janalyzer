package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.inner.*;
import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.CombinationElement;

public class CombinationAnalyzer extends MarkdownAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<CombinationAnalyzer> {

        private static final CombinationAnalyzer.Holder instance = new CombinationAnalyzer.Holder();

        public static CombinationAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public CombinationAnalyzer newAnalyzer() {
            return new CombinationAnalyzer();
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(InlineCodeAnalyzer.Holder.getInstance());
        registerAnalyzer(DeleteLineAnalyzer.Holder.getInstance());
        registerAnalyzer(ItalicBoldAnalyzer.Holder.getInstance());
        registerAnalyzer(BoldAnalyzer.Holder.getInstance());
        registerAnalyzer(ItalicAnalyzer.Holder.getInstance());
        registerAnalyzer(PicturePartAnalyzer.Holder.getInstance());
        registerAnalyzer(HyperlinkAnalyzer.Holder.getInstance());
        registerAnalyzer(TextAnalyzer.Holder.getInstance());
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
