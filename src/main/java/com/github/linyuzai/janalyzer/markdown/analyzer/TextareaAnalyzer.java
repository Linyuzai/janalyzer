package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TextareaElement;

public class TextareaAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<TextareaAnalyzer> {

        private static final Proxy instance = new Proxy(new TextareaAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(TextareaAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {

    }

    @Override
    public TextareaElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        int beginIndex = 0;
        int endIndex = source.length();
        TextareaElement textareaElement = (TextareaElement) super.analyze(newContext(source, loop));
        textareaElement.setContent(source);
        textareaElement.setBeginIndex(beginIndex);
        textareaElement.setEndIndex(endIndex);
        textareaElement.setText(source);
        return textareaElement;
    }

    @Override
    public TextareaElement newElement() {
        return new TextareaElement();
    }
}
