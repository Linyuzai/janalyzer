package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.CombinationAnalyzer;
import com.github.linyuzai.janalyzer.markdown.analyzer.holder.AbstractAnalyzerHolder;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.DeleteLineElement;

public class DeleteLineAnalyzer extends InnerPartAnalyzer {

    public static class Holder extends AbstractAnalyzerHolder<DeleteLineAnalyzer> {

        private static final DeleteLineAnalyzer.Holder instance = new DeleteLineAnalyzer.Holder();

        public static DeleteLineAnalyzer.Holder getInstance() {
            return instance;
        }

        private Holder() {
        }

        @Override
        public DeleteLineAnalyzer newAnalyzer() {
            return new DeleteLineAnalyzer();
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Holder.getInstance());
    }

    @Override
    public DeleteLineElement analyze(MarkdownContext context) {
        String source = context.getSource();
        boolean loop = context.isLoop();
        String mask = "~~";
        int deleteLineIndex = source.indexOf(mask);
        if (deleteLineIndex != -1) {
            int deleteLineEndIndex = source.indexOf(mask, deleteLineIndex + mask.length());
            if (deleteLineEndIndex != -1) {
                int firstBeginIndex = getFirstBeginIndex(context.getParent(), source);
                if (firstBeginIndex != -1 && firstBeginIndex < deleteLineIndex) {
                    return null;
                }
                int beginIndex = deleteLineIndex;
                int endIndex = deleteLineEndIndex + mask.length();
                String content = source.substring(beginIndex, endIndex);
                String inner = content.substring(mask.length(), content.length() - mask.length());
                DeleteLineElement deleteLineElement = (DeleteLineElement) super.analyze(newContext(inner, loop));
                baseElement(deleteLineElement, beginIndex, endIndex, content);
                return deleteLineElement;
            }
        }
        return null;
    }

    @Override
    public DeleteLineElement newElement() {
        return new DeleteLineElement();
    }
}
