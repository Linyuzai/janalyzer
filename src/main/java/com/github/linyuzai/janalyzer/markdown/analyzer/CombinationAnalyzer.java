package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.inner.*;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.CombinationElement;

public class CombinationAnalyzer extends MarkdownAnalyzer {

    private static final CombinationAnalyzer instance = new CombinationAnalyzer();

    public static CombinationAnalyzer getInstance() {
        return instance;
    }

    CombinationAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(InlineCodeAnalyzer.getInstance());
        registerAnalyzer(DeleteLineAnalyzer.getInstance());
        registerAnalyzer(ItalicBoldAnalyzer.getInstance());
        registerAnalyzer(BoldAnalyzer.getInstance());
        registerAnalyzer(ItalicAnalyzer.getInstance());
        registerAnalyzer(PicturePartAnalyzer.getInstance());
        registerAnalyzer(HyperlinkAnalyzer.getInstance());
        registerAnalyzer(TextAnalyzer.getInstance());
    }

    @Override
    public CombinationElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        int beginIndex = 0;
        int endIndex = getEndIndex(source, "\n");
        String content = source.substring(beginIndex, endIndex);
        CombinationElement combinationElement = (CombinationElement) super.analyze(newContext(content, loop));
        combinationElement.setContent(content);
        combinationElement.setBeginIndex(beginIndex);
        combinationElement.setEndIndex(endIndex);
        return combinationElement;
    }

    @Override
    public CombinationElement newElement() {
        return new CombinationElement();
    }
}
