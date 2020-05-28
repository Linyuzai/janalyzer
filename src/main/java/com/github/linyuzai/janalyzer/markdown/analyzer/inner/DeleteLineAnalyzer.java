package com.github.linyuzai.janalyzer.markdown.analyzer.inner;

import com.github.linyuzai.janalyzer.markdown.analyzer.CombinationAnalyzer;
import com.github.linyuzai.janalyzer.markdown.analyzer.proxy.AbstractAnalyzerProxy;
import com.github.linyuzai.janalyzer.markdown.context.MarkdownContext;
import com.github.linyuzai.janalyzer.markdown.element.DeleteLineElement;

public class DeleteLineAnalyzer extends InnerPartAnalyzer {

    public static class Proxy extends AbstractAnalyzerProxy<DeleteLineAnalyzer> {

        private static final Proxy instance = new Proxy(new DeleteLineAnalyzer());

        public static Proxy getInstance() {
            return instance;
        }

        public Proxy(DeleteLineAnalyzer analyzer) {
            super(analyzer);
        }
    }

    @Override
    public void registerSelfAnalyzers() {
        registerAnalyzer(CombinationAnalyzer.Proxy.getInstance());
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
                deleteLineElement.setContent(content);
                deleteLineElement.setBeginIndex(beginIndex);
                deleteLineElement.setEndIndex(endIndex);
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
