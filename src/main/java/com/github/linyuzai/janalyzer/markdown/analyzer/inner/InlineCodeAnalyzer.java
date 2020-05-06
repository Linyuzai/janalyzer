package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.TextAnalyzer;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.InlineCodeElement;

public class InlineCodeAnalyzer extends InnerPartAnalyzer {

    private static final InlineCodeAnalyzer instance = new InlineCodeAnalyzer();

    public static InlineCodeAnalyzer getInstance() {
        return instance;
    }

    InlineCodeAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(TextAnalyzer.getInstance());
    }

    @Override
    public InlineCodeElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String mask = "`";
        int inlineCodeIndex = source.indexOf(mask);
        if (inlineCodeIndex != -1) {
            int inlineCodeEndIndex = source.indexOf(mask, inlineCodeIndex + mask.length());
            if (inlineCodeEndIndex != -1) {
                int firstBeginIndex = getFirstBeginIndex(context.getParent(), source);
                if (firstBeginIndex != -1 && firstBeginIndex < inlineCodeIndex) {
                    return null;
                }
                int beginIndex = inlineCodeIndex;
                int endIndex = inlineCodeEndIndex + mask.length();
                String content = source.substring(beginIndex, endIndex);
                String inner = content.substring(mask.length(), content.length() - mask.length());
                InlineCodeElement inlineCodeElement = (InlineCodeElement) super.analyze(newContext(inner, loop));
                inlineCodeElement.setContent(content);
                inlineCodeElement.setBeginIndex(beginIndex);
                inlineCodeElement.setEndIndex(endIndex);
                return inlineCodeElement;
            }
        }
        return null;
    }

    @Override
    public InlineCodeElement newElement() {
        return new InlineCodeElement();
    }
}