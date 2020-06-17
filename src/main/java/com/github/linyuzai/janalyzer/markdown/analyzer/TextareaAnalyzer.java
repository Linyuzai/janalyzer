package com.github.linyuzai.janalyzer.markdown.analyzer;

import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.TextareaElement;

public class TextareaAnalyzer extends MarkdownAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<TextareaAnalyzer> {

        private static final Holder instance = new Holder(new TextareaAnalyzer());

        public static Holder getInstance() {
            return instance;
        }

        public Holder(TextareaAnalyzer analyzer) {
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
        baseElement(textareaElement, beginIndex, endIndex, source);
        textareaElement.setText(source);
        return textareaElement;
    }

    @Override
    public TextareaElement newElement() {
        return new TextareaElement();
    }
}
