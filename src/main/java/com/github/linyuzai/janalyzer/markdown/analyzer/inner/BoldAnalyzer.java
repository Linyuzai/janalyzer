package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.CombinationAnalyzer;
import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.BoldElement;

public class BoldAnalyzer extends InnerPartAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<BoldAnalyzer> {

        private static final BoldAnalyzer.Holder instance = new BoldAnalyzer.Holder();

        private Holder() {
        }

        public static BoldAnalyzer.Holder getInstance() {
            return instance;
        }

        @Override
        public BoldAnalyzer newAnalyzer() {
            return new BoldAnalyzer();
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Holder.getInstance());
    }

    @Override
    public BoldElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String mask = "**";
        int boldIndex = source.indexOf(mask);
        if (boldIndex != -1) {
            int boldEndIndex = source.indexOf(mask, boldIndex + mask.length());
            if (boldEndIndex != -1) {
                int firstBeginIndex = getFirstBeginIndex(context.getParent(), source);
                if (firstBeginIndex != -1 && firstBeginIndex < boldIndex) {
                    return null;
                }
                int beginIndex = boldIndex;
                int endIndex = boldEndIndex + mask.length();
                String content = source.substring(beginIndex, endIndex);
                String inner = content.substring(mask.length(), content.length() - mask.length());
                BoldElement boldElement = (BoldElement) super.analyze(newContext(inner, loop));
                baseElement(boldElement, beginIndex, endIndex, content);

                return boldElement;
            }
        }
        return null;
    }

    @Override
    public BoldElement newElement() {
        return new BoldElement();
    }

}
