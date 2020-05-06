package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.CombinationAnalyzer;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.HyperlinkElement;

public class HyperlinkAnalyzer extends InnerPartAnalyzer {

    private static final HyperlinkAnalyzer instance = new HyperlinkAnalyzer();

    public static HyperlinkAnalyzer getInstance() {
        return instance;
    }

    HyperlinkAnalyzer() {
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.getInstance());
    }

    @Override
    public HyperlinkElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String linkFirst = "[";
        String linkSecond = "](";
        String linkThird = ")";
        int linkFirstIndex = source.indexOf(linkFirst);
        if (linkFirstIndex != -1) {
            int linkSecondIndex = source.indexOf(linkSecond, linkFirstIndex + linkFirst.length());
            if (linkSecondIndex != -1) {
                int linkThirdIndex = source.indexOf(linkThird, linkSecondIndex + linkSecond.length());
                if (linkThirdIndex != -1) {
                    int firstBeginIndex = getFirstBeginIndex(context.getParent(), source);
                    if (firstBeginIndex != -1 && firstBeginIndex < linkFirstIndex) {
                        return null;
                    }
                    int beginIndex = linkFirstIndex;
                    int endIndex = linkThirdIndex + linkThird.length();
                    String content = source.substring(beginIndex, endIndex);
                    String inner = source.substring(linkFirstIndex + linkFirst.length(), linkSecondIndex);
                    String url = source.substring(linkSecondIndex + linkSecond.length(), linkThirdIndex);
                    HyperlinkElement hyperlinkElement = (HyperlinkElement) super.analyze(newContext(inner, loop));
                    hyperlinkElement.setContent(content);
                    hyperlinkElement.setBeginIndex(beginIndex);
                    hyperlinkElement.setEndIndex(endIndex);
                    hyperlinkElement.setUrl(url);
                    return hyperlinkElement;
                }
            }
        }
        return null;
    }

    @Override
    public HyperlinkElement newElement() {
        return new HyperlinkElement();
    }
}
