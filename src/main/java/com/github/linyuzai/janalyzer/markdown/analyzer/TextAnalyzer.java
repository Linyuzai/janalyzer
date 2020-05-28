package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TextElement;

public class TextAnalyzer extends MarkdownAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<TextAnalyzer> {

        private static final Proxy instance = new Proxy(new TextAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(TextAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {

    }

    @Override
    public TextElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        int beginIndex = 0;
        int endIndex = getEndIndex(source, "\n");
        String content = source.substring(beginIndex, endIndex);
        TextElement textElement = (TextElement) super.analyze(newContext(content, loop));
        baseElement(textElement, beginIndex, endIndex, content);
        textElement.setText(trimBoth(content, '\n', ' '));
        return textElement;
    }

    @Override
    public TextElement newElement() {
        return new TextElement();
    }
}
