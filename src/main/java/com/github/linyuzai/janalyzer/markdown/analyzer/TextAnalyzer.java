package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TextElement;

public class TextAnalyzer extends MarkdownAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<TextAnalyzer> {

        private static final TextAnalyzer.Holder instance = new TextAnalyzer.Holder();

        public static TextAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public TextAnalyzer newAnalyzer() {
            return new TextAnalyzer();
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
